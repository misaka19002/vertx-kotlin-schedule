package org.rainday

import io.vertx.core.Vertx
import org.quartz.CronExpression
import java.time.LocalDateTime
import java.util.*


/**
 * Created by wyd on 2018/9/12 14:36:59.
 */

fun Vertx.scheduled(cron: String, ebusAddr: String):  Unit{
    //定时任务不考虑集群，只单机运行
    val expression = CronExpression(cron)
    this.assignTask(expression, ebusAddr)
}


private fun Vertx.assignTask(expression: CronExpression, ebusAddr: String) {
    this.setTimer(expression.getNextValidTimeAfter(Date()).toInstant().toEpochMilli() - Date().toInstant().toEpochMilli()){
        println("${LocalDateTime.now()} send ($it) through eventbuss")
        //send timerId
        this.eventBus().send<String>(ebusAddr, it.toString()){
            if (it.succeeded()) {
                println("${LocalDateTime.now()} schedule receive response successfully! ${it.result().body()}")
            } else {
                println("schedule receive response failed :${it.cause().message}")
            }
            this.assignTask(expression, ebusAddr)
        }
    }
}

