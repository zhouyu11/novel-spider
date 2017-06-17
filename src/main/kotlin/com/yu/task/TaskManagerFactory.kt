package com.yu.task


import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.Getter
import lombok.Setter
import lombok.extern.slf4j.Slf4j
import org.springframework.context.SmartLifecycle
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by zhouyu on 16/06/2017.
 */
@Getter
@Setter
@Slf4j
open class TaskManagerFactory(val task: Runnable, val initialDelay: Long, val delay: Long, val timeUnit: TimeUnit) : SmartLifecycle {
    private val autoStartup = true

    private var threadPoolExecutor: ScheduledThreadPoolExecutor? = null
    private var future: ScheduledFuture<*>? = null

    init {
        threadPoolExecutor = ScheduledThreadPoolExecutor(1, NamedTaskFactory(false, "FixedDelayTask_"))
    }


    override fun isRunning(): Boolean {
        if (future != null) {
            return !future!!.isDone
        }
        return false
    }

    override fun start() {
        future = threadPoolExecutor!!.scheduleAtFixedRate(task, initialDelay, delay, timeUnit)
    }

    override fun isAutoStartup(): Boolean {
        return autoStartup;
    }

    override fun stop(callback: Runnable) {
        if (threadPoolExecutor != null) {
            threadPoolExecutor!!.shutdown()
        }
        callback.run()
    }

    override fun stop() {

    }

    override fun getPhase(): Int {
        return 0
    }

}