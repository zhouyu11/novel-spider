package com.yu

import com.yu.task.BackendTaskConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.yu.controller"))
@EnableAutoConfiguration
@Import(value = BackendTaskConfig::class)
open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            org.springframework.boot.SpringApplication.run(Application::class.java, *args)
        }
    }
}