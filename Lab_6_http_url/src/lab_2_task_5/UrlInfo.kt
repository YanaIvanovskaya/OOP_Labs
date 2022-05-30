package lab_2_task_5

import lab_2_task_5.Protocol

data class UrlInfo(
        val protocol: Protocol,
        val host: String,
        val port: Int,
        val document: String
)