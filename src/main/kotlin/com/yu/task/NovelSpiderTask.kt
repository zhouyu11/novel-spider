package com.yu.task

import com.yu.repository.NovelDao
import com.yu.spiders.NovelSpiderHandler
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.Getter
import lombok.Setter
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Getter
@Setter
@Slf4j
@Component
open class NovelSpiderTask : Runnable {
    @Autowired lateinit var novelSpiderHandler: NovelSpiderHandler
    @Autowired lateinit var noveldao: NovelDao
    private val log = LoggerFactory.getLogger(NovelSpiderTask::class.java)

    override fun run() {
        log.info("NovelSpiderTask start.")
        val novels = noveldao.list()
        novels.forEach { item ->
            log.info("crawl novel: " + item.name)
            novelSpiderHandler.crawlChapters(item)
        }
        log.info("NovelSpiderTask end.")
    }
}