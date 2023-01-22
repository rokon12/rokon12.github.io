#!/usr/bin/env kotlin

@file:DependsOn("org.freemarker:freemarker:2.3.31")
@file:DependsOn("com.squareup.okhttp3:okhttp:4.10.0")
@file:DependsOn("no.api.freemarker:freemarker-java8:2.1.0")
@file:DependsOn("org.yaml:snakeyaml:1.33")
@file:DependsOn("org.apache.commons:commons-text:1.10.0")
@file:DependsOn("org.json:json:20220924")
@file:DependsOn("org.jsoup:jsoup:1.15.3")
@file:DependsOn("com.rometools:rome:1.12.0")
@file:DependsOn("com.squareup.retrofit2:retrofit:2.9.0")
@file:DependsOn("com.squareup.retrofit2:converter-gson:2.0.0-beta3")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

import com.google.gson.Gson
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import freemarker.template.*
import no.api.freemarker.java8.Java8ObjectWrapper
import okhttp3.*
import org.apache.commons.text.StringEscapeUtils
import org.jsoup.Jsoup
import java.io.*
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val template: Template = Configuration(Configuration.VERSION_2_3_29)
        .apply {
            setDirectoryForTemplateLoading(File("."))
            defaultEncoding = "UTF-8"
            templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
            logTemplateExceptions = false
            wrapUncheckedExceptions = true
            fallbackOnNullLoopVariable = false
            objectWrapper = Java8ObjectWrapper(this.incompatibleImprovements)
        }.getTemplate("template.md")

val posts: List<Post> by lazy {
    val url = URL("https://foojay.io/today/author/bazlur-rahman/feed")
    val input = XmlReader(url)
    val feed = SyndFeedInput().build(input)
    feed.entries.map {
        val published = Instant.ofEpochMilli(it.publishedDate.time)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        var content = it.description.value
        content = StringEscapeUtils.unescapeHtml4(content)
        content = content.replace(Regex("<(div|p)[^>]*>"), "").replace(Regex("</(div|p)>"), "")
        Post(published, it.title, it.link, content)
    }
}

data class Post(val published: LocalDate, val title: String, val link: String, val excerpt: String)

val client = OkHttpClient()
val gson = Gson()
var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
var formatter2: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm")
val baseUrl = "https://www.infoq.com/"
val infoqUrl = "${baseUrl}userProfile!getMore.action?single=true&token=ElCokalLPL2msy8g83iejKIMwEQ7Cnmn&fu=0&f=0&ft=0&lc=0&cml=0&cmo=0&news=0&articles=0&presentations=0&minibooks=0&podcasts=0&interviews=0&research=0&reviewed=0"
val request = Request.Builder()
        .url(infoqUrl)
        .build()
data class Data(val id: String, val type: String, val title: String, val date: String, val meta: String, val body: String, val hidden: String, val hasMore: Boolean, val followedByCurrentUser: Boolean, val count: String, val link: String, val msg: String)

val infoqPosts: List<Post> by lazy {
    val response = client.newCall(request).execute()
    val responseData = response.body?.string()

    gson.fromJson(responseData, Array<Data>::class.java)
            .filter { it.type == "news" }
            .map { Post(localDate(it), it.title, (baseUrl + it.link), it.body) }
}

val talks: List<String> by lazy {
    val document = Jsoup.connect("https://bazlur.ca/conference-talks/")
            .userAgent("Mozilla").get()
    val container = document.getElementsByClass("container")
    val ul = container.select("div.entry-content > ul")
    val li = ul.select("li")
    li.map { it.html() }
}

val root = mapOf(
        "foojayPosts" to posts,
        "infoqPosts" to infoqPosts,
        "talks" to talks
)

template.process(root, FileWriter("README.md"))

fun localDate(it: Data): LocalDate {
    val date = try {
        LocalDate.parse(it.date, formatter)
    } catch (e: Exception) {
        try {
            LocalDate.parse(it.date, formatter2)
        } catch (e: Exception) {
            LocalDate.now()
        }
    }
    return date
}
