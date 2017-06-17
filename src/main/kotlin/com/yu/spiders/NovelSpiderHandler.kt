package com.yu.spiders

import com.yu.domain.model.Chapter
import com.yu.domain.model.Novel
import com.yu.domain.model.NovelStatus
import com.yu.repository.ChapterDao
import com.yu.repository.NovelDao
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.*

/**
 * Created by zhouyu on 17/06/2017.
 */

@Component
open class NovelSpiderHandler {
    @Autowired private lateinit var spiderNet: SpiderNet
    @Autowired private lateinit var chapterDao: ChapterDao
    @Autowired lateinit var noveldao: NovelDao
    private val log = LoggerFactory.getLogger(NovelSpiderHandler::class.java)

    fun crawlChapters(novel: Novel) {
        if (novel.status == NovelStatus.DONE) {
            return
        }

        val links = spiderNet.crawlChapters(novel.host + novel.link)

        log.info("found link count {}", links.size)
        val unSavedChapters = filterUnSavedChapter(links, novel.latestChapterName)
        log.info("unCrawled link count {}", unSavedChapters.size)

        unSavedChapters.forEach { item ->
            val stopWatch = StopWatch()
            stopWatch.start()
            val chapter = Chapter()
            chapter.link = item.attr("href")
            chapter.name = item.text()
            chapter.novel = novel

            if (chapter.link?.commonPrefixWith(novel.link!!, true).equals(novel.link)) {
                chapter.content = spiderNet.getChapterContent(novel.host + chapter.link)
                chapterDao.save(chapter)
                novel.latestChapterName = chapter.name
                noveldao.save(novel)
            } else {
                log.info("skip" + chapter.name + " --- " + chapter.link)
            }

            stopWatch.stop()
            log.info("chapter {} , cost time {}", chapter.name, stopWatch.totalTimeMillis)
        }
    }

    private fun filterUnSavedChapter(links: List<Element>, latestChapterName: String?): List<Element> {
        val chapters = ArrayList(links)

        if (chapters.size == 0 || latestChapterName == null) return chapters

        var chapter = chapters[0]

        while (chapter.text() != latestChapterName && chapters.size > 1) {
            log.info("already saved chapter {}.", chapter.text())
            chapters.remove(chapter)
            chapter = chapters[0]
        }
        chapters.remove(chapter)
        return chapters
    }
}

