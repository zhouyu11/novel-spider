package com.yu.aop

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

/**
 * Created by zhouyu on 17/06/2017.
 */
@Aspect
@Component
open class SpiderMonitor {
    @Around("execution(* com.yu.task.NovelSpiderTask.run())")
    @Throws(Throwable::class)
    fun logMethod(pjp: ProceedingJoinPoint): Any {
        val proceed = pjp.proceed()
        return proceed
    }
}
