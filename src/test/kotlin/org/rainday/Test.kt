package org.rainday

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * Created by wyd on 2018/9/13 13:45:45.
 */
fun main(vararg args: String) {
    val vertx = Vertx.vertx()
    vertx.eventBus().consumer<String>("vertx.kotlin.schedule"){
        launch(vertx.dispatcher()) {
            println("consumer receive: ${it.body()}")
            delay(10000)
            it.reply(it.body())
        }

    }
    vertx.scheduled("0/2 * * * * ?","vertx.kotlin.schedule")
    Thread.sleep(6000)

    println("5      " + Date().toInstant().toEpochMilli() + "${Thread.currentThread().id}  ${Thread.currentThread().name}")
}