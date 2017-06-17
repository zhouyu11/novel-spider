package com.yu.spiders

import com.yu.domain.model.Chapter
import com.yu.domain.model.Novel
import com.yu.domain.model.NovelStatus
import com.yu.repository.ChapterDao
import com.yu.repository.NovelDao
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by zhouyu on 17/06/2017.
 */

@Component
open class NovelSpiderHandler {
    @Autowired private lateinit var spiderNet: SpiderNet
    @Autowired private lateinit var chapterDao: ChapterDao
    @Autowired lateinit var noveldao: NovelDao

    fun crawlChapters(novel: Novel) {
        if (novel.status == NovelStatus.DONE) {
            return
        }

        val links = spiderNet.crawlChapters(novel.host + novel.link)

        log.println(links.size)
        val unSavedChapters = filterUnSavedChapter(links, novel.latestChapterName)
        log.println(unSavedChapters.size)

        unSavedChapters.forEach { item ->
            val chapter = Chapter()
            chapter.link = item.attr("href")
            chapter.name = item.text()
            chapter.novel = novel

            if (chapter.link?.commonPrefixWith(novel.link!!, true).equals(novel.link)) {
                log.println(chapter.name + "   --- " + chapter.link)
                spiderNet.getChapterContent(novel.host + chapter.link)
                chapterDao.save(chapter)
                novel.latestChapterName = chapter.name
                noveldao.save(novel)
            } else {
                log.println("skip" + chapter.name + " --- " + chapter.link)
            }
        }
    }

    private fun filterUnSavedChapter(links: List<Element>, latestChapterName: String?): List<Element> {
        val chapters = ArrayList(links)

        if (chapters.size == 0 || latestChapterName == null) return chapters

        var chapter = chapters[0]

        while (chapter.text() != latestChapterName) {
            log.println(chapter.text())
            chapters.remove(chapter)
            chapter = chapters[0]
        }
        chapters.remove(chapter)
        return chapters
    }
}

