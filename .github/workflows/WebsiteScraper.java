///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.jsoup:jsoup:1.15.4
//DEPS com.fasterxml.jackson.core:jackson-databind:2.14.2
//DEPS commons-io:commons-io:2.11.0
//DEPS com.vladsch.flexmark:flexmark-all:0.64.0

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.FileUtils;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class WebsiteScraper {
    private static final String WEBSITE_URL = "https://bazlur.ca";
    private static final String OUTPUT_DIR = "website_content";
    private static final String RECORD_FILE = "record.json";
    private static final String PROGRESS_FILE = "scraper_progress.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final FlexmarkHtmlConverter htmlToMarkdownConverter = FlexmarkHtmlConverter.builder().build();
    private static final int REQUEST_DELAY_MS = 1000; // Delay between requests
    private static final int CONNECTION_TIMEOUT_MS = 60000; // 1 minute timeout

    private static class Progress {
        @com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS)
        public Set<String> processedUrls = new HashSet<>();

        @com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS)
        public Queue<String> pagesToProcess = new LinkedList<>();

        @com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS)
        public Map<String, String> existingArticles = new HashMap<>();

        public int totalPages;
        public int currentPage;

        public void save() throws IOException {
            // Save progress atomically using temporary file
            File tempProgress = new File(PROGRESS_FILE + ".tmp");
            objectMapper.writeValue(tempProgress, this);
            Files.move(tempProgress.toPath(), 
                      new File(PROGRESS_FILE).toPath(), 
                      StandardCopyOption.ATOMIC_MOVE,
                      StandardCopyOption.REPLACE_EXISTING);

            // Save record file atomically
            File tempRecord = new File(RECORD_FILE + ".tmp");
            objectMapper.writeValue(tempRecord, existingArticles);
            Files.move(tempRecord.toPath(), 
                      new File(RECORD_FILE).toPath(), 
                      StandardCopyOption.ATOMIC_MOVE,
                      StandardCopyOption.REPLACE_EXISTING);
        }

        @SuppressWarnings("unchecked")
        public static Progress load() throws IOException {
            Progress progress = new Progress();

            // Try to load existing progress
            File progressFile = new File(PROGRESS_FILE);
            if (progressFile.exists()) {
                progress = objectMapper.readValue(progressFile, Progress.class);

                // If progress is corrupted, start fresh
                if (progress.processedUrls == null) {
                    log("Progress file corrupted, starting fresh session");
                    progress = new Progress();
                } else {
                    // If we have processed URLs but no pages to process, we either completed or crashed
                    if (progress.pagesToProcess.isEmpty() && !progress.processedUrls.isEmpty()) {
                        // Check if we processed all pages (no next page found)
                        String lastProcessedUrl = progress.processedUrls.stream()
                            .filter(url -> url.contains("/page/"))
                            .max(Comparator.comparingInt(url -> {
                                try {
                                    return Integer.parseInt(url.replaceAll(".*/page/(\\d+)/.*", "$1"));
                                } catch (Exception e) {
                                    return 0;
                                }
                            }))
                            .orElse("");

                        if (lastProcessedUrl.isEmpty()) {
                            log("No page progress found, starting fresh session");
                            progress = new Progress();
                        } else {
                            try {
                                int lastPage = Integer.parseInt(lastProcessedUrl.replaceAll(".*/page/(\\d+)/.*", "$1"));
                                log("Resuming from page " + lastPage);

                                // Add the next page to process
                                String nextPageUrl = WEBSITE_URL + "/page/" + (lastPage + 1) + "/";
                                progress.pagesToProcess.add(nextPageUrl);
                                progress.totalPages = lastPage + 1;
                                progress.currentPage = lastPage;

                                // Clear processed URLs for pages after the last successful one
                                progress.processedUrls.removeIf(url -> {
                                    try {
                                        String pageNum = url.replaceAll(".*/page/(\\d+)/.*", "$1");
                                        return Integer.parseInt(pageNum) > lastPage;
                                    } catch (Exception e) {
                                        return false;
                                    }
                                });
                            } catch (Exception e) {
                                log("Error parsing last page number, starting fresh session");
                                progress = new Progress();
                            }
                        }
                    } else {
                        log("Resuming from previous session with " + progress.pagesToProcess.size() + " pages remaining");
                    }
                }
            }

            // Try to load existing record
            File recordFile = new File(RECORD_FILE);
            if (recordFile.exists()) {
                try {
                    progress.existingArticles = objectMapper.readValue(recordFile, Map.class);
                    log("Loaded existing record with " + progress.existingArticles.size() + " articles");
                } catch (IOException e) {
                    log("Warning: Could not load record file: " + e.getMessage());
                    progress.existingArticles = new HashMap<>();
                }
            }

            // Initialize if new session
            if (progress.pagesToProcess.isEmpty()) {
                progress.pagesToProcess.add(WEBSITE_URL);
                progress.totalPages = 1;
                progress.currentPage = 0;
            }

            return progress;
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void log(String message) {
        System.out.println("[" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] " + message);
    }

    public static void main(String... args) {
        try {
            log("Starting website content backup process");

            // Create output directory if it doesn't exist
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            log("Created output directory: " + OUTPUT_DIR);

            Progress progress = Progress.load();
            log("Loaded progress: " + progress.currentPage + " pages processed, " + 
                progress.pagesToProcess.size() + " pages remaining");

            int newArticles = 0;
            int processedImages = 0;

            while (!progress.pagesToProcess.isEmpty()) {
                String currentUrl = progress.pagesToProcess.poll();
                if (progress.processedUrls.contains(currentUrl)) {
                    continue;
                }

                progress.currentPage++;
                log(String.format("Processing page %d/%d: %s", progress.currentPage, progress.totalPages, currentUrl));
                sleep(REQUEST_DELAY_MS); // Rate limiting
                Document doc = Jsoup.connect(currentUrl)
                                  .userAgent("Mozilla/5.0")
                                  .timeout(CONNECTION_TIMEOUT_MS)
                                  .get();
                progress.processedUrls.add(currentUrl);

                // Save progress periodically
                if (progress.currentPage % 5 == 0) { // Save every 5 pages
                    progress.save();
                    log("Progress saved");
                }

                // Find next page link
                Elements nextPageLinks = doc.select("a.next.page-numbers");
                for (Element nextLink : nextPageLinks) {
                    String nextUrl = nextLink.attr("abs:href");
                    if (!progress.processedUrls.contains(nextUrl)) {
                        progress.pagesToProcess.add(nextUrl);
                        progress.totalPages++;
                        log(String.format("Found next page (%d total): %s", progress.totalPages, nextUrl));
                    }
                }

                Elements articles = doc.select("article");
                log("Found " + articles.size() + " articles on " + currentUrl);

                for (Element article : articles) {
                    try {
                        Element titleElement = article.select("h1, h2").first();
                        Element linkElement = article.select("a").first();

                        if (titleElement == null || linkElement == null) {
                            log("Warning: Skipping article due to missing title or link");
                            continue;
                        }

                        String title = titleElement.text();
                        String url = linkElement.attr("abs:href");

                        if (progress.existingArticles.containsKey(url)) {
                            continue;
                        }

                        log("Processing new article: " + title);

                        // Fetch full article content
                        sleep(REQUEST_DELAY_MS); // Rate limiting
                        Document articleDoc = Jsoup.connect(url)
                                                .userAgent("Mozilla/5.0")
                                                .timeout(CONNECTION_TIMEOUT_MS)
                                                .get();

                        Element content = articleDoc.select("article").first();
                        if (content == null) {
                            log("Warning: Could not find article content for: " + title);
                            continue;
                        }

                        // Remove sharing and related content
                        content.select(".sharedaddy, .jp-relatedposts, .entry-meta, .entry-footer").remove();

                        // Download images
                        Elements images = content.select("img");
                        for (Element img : images) {
                            try {
                                String imgUrl = img.attr("abs:src");
                                String imgName = new File(imgUrl).getName()
                                    .replaceAll("\\?.*$", "") // Remove URL parameters
                                    .replaceAll("%[0-9A-F]{2}", "-") // Replace URL encoded characters with dash
                                    .replaceAll("[^a-zA-Z0-9.-]", "-") // Replace special characters with dash
                                    .replaceAll("-+", "-") // Replace multiple dashes with single dash
                                    .toLowerCase();
                                String imgPath = OUTPUT_DIR + "/images/" + imgName;

                                Files.createDirectories(Paths.get(OUTPUT_DIR, "images"));

                                log("Downloading image: " + imgUrl);
                                sleep(REQUEST_DELAY_MS / 2); // Shorter delay for images
                                FileUtils.copyURLToFile(new URL(imgUrl), new File(imgPath), CONNECTION_TIMEOUT_MS, CONNECTION_TIMEOUT_MS);

                                img.attr("src", "images/" + imgName);
                                processedImages++;
                            } catch (Exception e) {
                                log("Warning: Failed to download image: " + e.getMessage());
                            }
                        }

                        // Convert to markdown
                        String markdown = htmlToMarkdownConverter.convert(content.html());

                        // Prepare metadata
                        StringBuilder fullContent = new StringBuilder();
                        fullContent.append("---\n");
                        fullContent.append("title: ").append(title).append("\n");
                        fullContent.append("original_url: ").append(url).append("\n");
                        fullContent.append("date_scraped: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n");
                        fullContent.append("---\n\n");
                        fullContent.append(markdown);

                        // Save markdown file
                        String fileName = title.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "-").toLowerCase() + ".md";
                        Files.write(Paths.get(OUTPUT_DIR, fileName), fullContent.toString().getBytes());

                        // Update record
                        progress.existingArticles.put(url, fileName);
                        newArticles++;

                        log("Successfully processed article: " + title);
                    } catch (Exception e) {
                        log("Error processing article: " + e.getMessage());
                    }
                }
            }

            // Save final progress and record
            progress.save();

            log("Backup completed successfully:");
            log("- Pages processed: " + progress.currentPage);
            log("- New articles processed: " + newArticles);
            log("- Images downloaded: " + processedImages);
            log("- Total articles in record: " + progress.existingArticles.size());

        } catch (Exception e) {
            log("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

}
