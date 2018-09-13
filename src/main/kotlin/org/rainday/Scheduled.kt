package org.rainday

import io.vertx.core.Vertx
import org.quartz.CronExpression
import java.util.*


/**
 * Created by wyd on 2018/9/12 14:36:59.
 */

fun Vertx.scheduled(cron: String, block: (timerId: Long) -> Unit):  Unit{
    //定时任务不考虑集群，只单机运行
    val expression = CronExpression(cron)
    this.assignTask(expression, block)
}


private fun Vertx.assignTask(expression: CronExpression, block: (timerId: Long) -> Unit) {
   this.timerStream(expression.getNextValidTimeAfter(Date()).toInstant().toEpochMilli() - Date().toInstant().toEpochMilli())
            .handler(block)
            .endHandler {
                this.assignTask(expression, block)
            }
            .exceptionHandler {
                println("vertx scheduled exception ${it.message}")
            }
}

