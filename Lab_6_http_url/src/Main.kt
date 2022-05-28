// Task 5 - Вариант 1 – парсер URL-ов – 80 баллов
fun main(args: Array<String>) {

    val list = listOf(
            "http://qwerty.ru:78/document23/ffd/dfd.x3r",
            "http://qwerty.ru:/document23/ffd/dfd.x3r",
//            "http://qwerty.ru:34document23/ffd/dfd.x3r",
//            "https://ere.c",
//            "https://ere.c/fekfe/efef",
//            "https://ere.c",
//            "https://eredw",
//            "https://",
//            "https:dwdw"
    )

    list.forEach {
        println(UrlMatcher.getUrlInfo(it))
    }

//    println("Please, enter url to validate or empty line to cancel:")
//    var isInterrupted = false
//    Scanner(System.`in`).use { userInput ->
//        while (!isInterrupted) {
//            val url = userInput.nextLine()
//            if (url.isBlank()) {
//                isInterrupted = true
//            } else try {
//              //  printUrlInfo(url, UrlMatcher.getUrlInfo(url))
//            } catch (ex: MalformedURLException) {
//                println("This is not url")
//            }
//        }
//    }
}

private fun printUrlInfo(url: String, info: UrlInfo) {
    println(url)
    println("PROTOCOL: " + info.protocol)
    println("HOST: " + info.host)
    println("PORT: " + info.port)
    println("DOC: " + info.document)
}
