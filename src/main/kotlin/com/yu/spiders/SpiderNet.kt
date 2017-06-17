package com.yu.spiders

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

/**
 * Created by zhouyu on 17/06/2017.
 */
@Component
@Slf4j
open class SpiderNet {
    open fun crawlChapters(link: String?): List<Element> {
        var document: Document? = null
        while (document == null) {
            document = crawlPage(link)
        }

        val collect = document.select("a").stream()
                .filter { item ->
                    item.attr("href").contains(".html") && item.text().contains("章") && item.text().contains("第")
                }.collect(Collectors.toList())
        Collections.reverse(collect)
        return collect
    }

    private fun crawlPage(link: String?): Document? {
        var document: Document? = null
        try {
            document = Jsoup.connect(link).get()
        } catch (exception: Exception) {
            log.println("get $link failed.")
        }
        return document
    }

    open fun getChapterContent(link: String?): String {
        var content: String? = null
        while (content == null) {
            try {
                content =  Jsoup.connect(link)
                        .get()
                        .getElementById("nr1")
                        .text()

            } catch (e: Exception) {
                log.println("get $link failed.")
            }
        }
        return content
    }

}
