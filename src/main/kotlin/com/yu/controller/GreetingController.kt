package com.yu.controller

import com.yu.domain.Greeting
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
open class GreetingController {
    @RequestMapping("/greeting")
    public fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting(1L, "Hello, $name")
    }
}