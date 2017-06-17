package com.yu.spiders

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.*
import java.util.stream.Collectors

/**
 * Created by zhouyu on 17/06/2017.
 */
@Component
@Slf4j
open class SpiderNet {
    private val log = LoggerFactory.getLogger(SpiderNet::class.java)
    open fun crawlChapters(link: String?): List<Element> {
        val stopWatch = StopWatch()
        stopWatch.start()
        var document: Document? = null
        while (document == null) {
            document = crawlPage(link)
        }

        val elements = document.select("a")
        val collect = elements.stream()
                .filter { item ->
                    item.attr("href").contains(".html") && item.text().contains("章") && item.text().contains("第")
                }.collect(Collectors.toList())
        Collections.reverse(collect)
        stopWatch.stop()
        log.info("link {} , cost time {}", link, stopWatch.totalTimeMillis)
        return collect
    }

    private fun crawlPage(link: String?): Document? {
        var document: Document? = null
        try {
            document = Jsoup.connect(link).get()
        } catch (exception: Exception) {
            log.info("get $link failed.")
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
                log.info("get $link failed.")
            }
        }
        return content
    }

}
