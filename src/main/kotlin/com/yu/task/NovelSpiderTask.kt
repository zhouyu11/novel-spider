package com.yu.task

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.Getter
import lombok.Setter
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Getter
@Setter
@Slf4j
@Component
open class NovelSpiderTask : Runnable{
    override fun run() {
        log.println("hhhhhhh.")
    }
}