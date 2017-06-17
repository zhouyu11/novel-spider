package com.yu.task

import com.yu.repository.NovelDao
import com.yu.spiders.NovelSpiderHandler
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.Getter
import lombok.Setter
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Getter
@Setter
@Slf4j
@Component
open class NovelSpiderTask : Runnable {
    @Autowired lateinit var novelSpiderHandler: NovelSpiderHandler
    @Autowired lateinit var noveldao: NovelDao
    override fun run() {
        val novels = noveldao.list()
        novels.forEach { item ->
            log.println("crawl novel: "+ item.name)
            novelSpiderHandler.crawlChapters(item)
        }

    }
}