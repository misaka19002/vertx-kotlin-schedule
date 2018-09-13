package org.rainday

import io.vertx.core.Vertx
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by wyd on 2018/9/13 13:45:45.
 */

fun main(vararg args: String) {
    val vertx = Vertx.vertx()
    vertx.scheduled("0/2 * * * * ?") {
        //请不要在这里执行阻塞任务
        //请在这里自己处理异常
        if (it > 0) {
            throw RuntimeException("sdfdsf")
        }
        println("$it ${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) } ${Thread.currentThread().id}  ${Thread.currentThread().name}")
    }
    Thread.sleep(6000)

    println("5      " + Date().toInstant().toEpochMilli() + "${Thread.currentThread().id}  ${Thread.currentThread().name}")
}