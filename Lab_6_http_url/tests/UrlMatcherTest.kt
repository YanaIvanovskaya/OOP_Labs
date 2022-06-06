import lab_2_task_5.Protocol
import lab_2_task_5.UrlMatcher
import lab_6.Url
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.MalformedURLException

internal class UrlMatcherTest {
    @Test
    @DisplayName("Попытка получить информацию у некорректного урла должна приводить к исключению")
    fun case_1() {
        val incorrectUrls = listOf(
                "fttp://qwerty.ru:78/document23/ffd/dfd.x3r",
                "http://qwerty.ru:/document23/ffd/dfd.x3r",
                "http://qwerty.ru:34document23/ffd/dfd.x3r",
                "https:///ere.c",
                "https:/ere.c",
                "https:ere.c",
                "https://ere$.c",
                "https://eredw//",
                "https://",
                "https:dwdw"
        )
        incorrectUrls.forEach {
            assertThrows(MalformedURLException::class.java) {
                println(UrlMatcher.getUrl(it))
            }
        }
    }

    @Test
    @DisplayName("Валидный урл может не содержать порт")
    fun case_2() {
        val url = "http://qwerty.ru/document/12/23"
        val expected = Url(protocol = Protocol.HTTP, domain = "qwerty.ru", port = 80, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrl(url))
    }

    @Test
    @DisplayName("Валидный урл может содержать порт")
    fun case_3() {
        val url = "http://qwerty.ru:345/document/12/23"
        val expected = Url(protocol = Protocol.HTTP, domain = "qwerty.ru", port = 345, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrl(url))
    }

    @Test
    @DisplayName("Если порт не находится в диапазоне [1;65535], то урл некорректен")
    fun case_4() {
        val url = "http://qwerty.ru:65888/document/12/23"
        assertThrows(MalformedURLException::class.java) {
            UrlMatcher.getUrl(url)
        }
    }

    @Test
    @DisplayName("Если у урла протокол не HTTP/HTTPS/FTP, то урл некорректен")
    fun case_5() {
        val url = "ws://qwerty.ru:345/document/12/23"
        assertThrows(MalformedURLException::class.java) {
            UrlMatcher.getUrl(url)
        }
    }

    @Test
    @DisplayName("Название протокола может быть в любом регистре")
    fun case_6() {
        val url = "HTtPs://qwerty.ru/document/12/23"
        val expected = Url(protocol = Protocol.HTTPS, domain = "qwerty.ru", port = 443, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrl(url))
    }

    @Test
    @DisplayName("Валидный протокол может не содержать документ")
    fun case_7() {
        val url = "https://qwerty.ru"
        val expected = Url(protocol = Protocol.HTTPS, domain = "qwerty.ru", port = 443, document = "")
        assertEquals(expected, UrlMatcher.getUrl(url))
    }

    @Test
    @DisplayName("Валидный урл может иметь порт, равный 1 или 65535")
    fun case_8() {
        val url1 = "https://qwerty.dev:1"
        val expected1 = Url(protocol = Protocol.HTTPS, domain = "qwerty.dev", port = 1, document = "")
        assertEquals(expected1, UrlMatcher.getUrl(url1))

        val url2 = "https://qwerty.dev:65535"
        val expected2 = Url(protocol = Protocol.HTTPS, domain = "qwerty.dev", port = 65535, document = "")
        assertEquals(expected2, UrlMatcher.getUrl(url2))
    }

    @Test
    @DisplayName("Валидный урл может содержать в хосте точки, дефисы, цифры")
    fun case_9() {
        val correctUrls = arrayOf(
                "http://qwerty-123.dev.com",
                "http://q.com",
                "ftp://qwerty.qwerty.qwerty.ru",
                "ftp://12-34-343.ru",
                "ftp://.-"
        )
        for (url in correctUrls) {
            assertDoesNotThrow {
                UrlMatcher.getUrl(url)
            }
        }
    }

    @Test
    @DisplayName("Метод по получению информации об урле должен корректно парсить исходную строку")
    @Throws(MalformedURLException::class)
    fun getUrlInfo() {
        val testMap = mutableMapOf<String, Url>()
        // host
        testMap["https://qwerty.ru"] = Url(protocol = Protocol.HTTPS, domain = "qwerty.ru", port = 443, document = "")
        testMap["https://q"] = Url(protocol = Protocol.HTTPS, domain = "q", port = 443, document = "")
        testMap["https://."] = Url(protocol = Protocol.HTTPS, domain = ".", port = 443, document = "")
        testMap["http://qwerty-23.ru:56"] = Url(protocol = Protocol.HTTP, domain = "qwerty-23.ru", port = 56, document = "")

        //document
        testMap["ftp://q.ru/343/565"] = Url(protocol = Protocol.FTP, domain = "q.ru", port = 21, document = "/343/565")
        testMap["https://q:67/f/d"] = Url(protocol = Protocol.HTTPS, domain = "q", port = 67, document = "/f/d")
        testMap["http://qwerty-23.ru:569/we"] = Url(protocol = Protocol.HTTP, domain = "qwerty-23.ru", port = 569, document = "/we")

        // port
        testMap["HTTPS://q.u:1/343/565"] = Url(protocol = Protocol.HTTPS, domain = "q.u", port = 1, document = "/343/565")
        testMap["https://q:6"] = Url(protocol = Protocol.HTTPS, domain = "q", port = 6, document = "")
        testMap.forEach { (key, value) ->
            assertEquals(value, UrlMatcher.getUrl(key))
        }
    }

    @Test
    @DisplayName("Валидный урл может иметь пустой документ")
    fun case_10() {
        val url1 = "https://qwerty.dev/"
        val expected1 = Url(protocol = Protocol.HTTPS, domain = "qwerty.dev", port = 443, document = "/")
        assertEquals(expected1, UrlMatcher.getUrl(url1))

    }

}