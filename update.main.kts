#!/usr/bin/env kotlin

@file:DependsOn("org.freemarker:freemarker:2.3.31")
@file:DependsOn("com.squareup.okhttp3:okhttp:4.10.0")
@file:DependsOn("no.api.freemarker:freemarker-java8:2.1.0")
@file:DependsOn("org.yaml:snakeyaml:1.33")
@file:DependsOn("org.apache.commons:commons-text:1.10.0")
@file:DependsOn("org.json:json:20220924")
@file:DependsOn("org.jsoup:jsoup:1.15.3")
@file:DependsOn("com.rometools:rome:1.12.0")

import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import freemarker.template.*
import no.api.freemarker.java8.Java8ObjectWrapper
import okhttp3.*
import org.apache.commons.text.StringEscapeUtils
import java.io.*
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


val client = OkHttpClient()

fun <T> execute(builder: Request.Builder, extractor: (String?) -> T): T {
    val body = client.newCall(builder.build())
            .execute()
            .body
            ?.string()
    return extractor(body)
}

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

val root = mapOf(
        "foojayPosts" to posts,
)

template.process(root, FileWriter("README.md"))
