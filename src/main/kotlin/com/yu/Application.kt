package com.yu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.yu.controller"))
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            org.springframework.boot.SpringApplication.run(Application::class.java, *args)
        }
    }
}