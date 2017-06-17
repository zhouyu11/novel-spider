package com.yu.task

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * Created by zhouyu on 17/06/2017.
 */

@Configuration
open class BackendTaskConfig {
    @Bean
    open fun novelSpiderTask(): NovelSpiderTask {
        return NovelSpiderTask()
    }

    @Bean
    open fun novelSpiderTaskManager(): TaskManagerFactory {
        val task = novelSpiderTask()

        val taskManager = TaskManagerFactory(task, 0 , 30, TimeUnit.SECONDS)

        return taskManager
    }
}

