package com.yu.task

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by zhouyu on 16/06/2017.
 */
class NamedTaskFactory(private val daemon: Boolean, private val threadNamePrefix: String) : ThreadFactory {
    private val backingThreadFactory = Executors.defaultThreadFactory()

    private val increment = AtomicLong()

    override fun newThread(r: Runnable?): Thread {
        val thread = backingThreadFactory.newThread(r)
        thread.name = threadNamePrefix + increment.incrementAndGet()
        thread.isDaemon = daemon
        return thread
    }
}
