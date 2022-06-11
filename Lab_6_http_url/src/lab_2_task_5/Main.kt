package lab_2_task_5

import lab_6.Url
import java.net.MalformedURLException
import java.util.*

// Task 5 - Вариант 1 – парсер URL-ов – 80 баллов
fun main() {
    println("Please, enter url to validate or empty line to cancel:")
    var isInterrupted = false
    Scanner(System.`in`).use { userInput ->
        while (!isInterrupted) {
            val url = userInput.nextLine()

            if (url.isBlank()) isInterrupted = true
            else try {
                printUrlInfo(url, UrlMatcher.getUrl(url))
            } catch (ex: MalformedURLException) {
                println(ex.message)
            }
        }
    }
}

private fun printUrlInfo(url: String, info: Url) {
    println(url)
    println("PROTOCOL: " + info.protocol)
    println("HOST: " + info.domain)
    println("PORT: " + info.port)
    println("DOC: " + info.document)
}
