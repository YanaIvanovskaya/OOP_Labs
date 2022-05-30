import lab_2_task_5.Protocol
import lab_2_task_5.UrlInfo
import lab_2_task_5.UrlMatcher
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
                "https://ere.c/",
                "https://ere$.c",
                "https://eredw//",
                "https://",
                "https:dwdw"
        )
        incorrectUrls.forEach {
            assertThrows(MalformedURLException::class.java) {
                UrlMatcher.getUrlInfo(it)
            }
        }
    }

    @Test
    @DisplayName("Валидный урл может не содержать порт")
    fun case_2() {
        val url = "http://qwerty.ru/document/12/23"
        val expected = UrlInfo(protocol = Protocol.HTTP, host = "qwerty.ru", port = 80, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrlInfo(url))
    }

    @Test
    @DisplayName("Валидный урл может содержать порт")
    fun case_3() {
        val url = "http://qwerty.ru:345/document/12/23"
        val expected = UrlInfo(protocol = Protocol.HTTP, host = "qwerty.ru", port = 345, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrlInfo(url))
    }

    @Test
    @DisplayName("Если порт не находится в диапазоне [1;65535], то урл некорректен")
    fun case_4() {
        val url = "http://qwerty.ru:65888/document/12/23"
        assertThrows(MalformedURLException::class.java) {
            UrlMatcher.getUrlInfo(url)
        }
    }

    @Test
    @DisplayName("Если у урла протокол не HTTP/HTTPS/FTP, то урл некорректен")
    fun case_5() {
        val url = "ws://qwerty.ru:345/document/12/23"
        assertThrows(MalformedURLException::class.java) {
            UrlMatcher.getUrlInfo(url)
        }
    }

    @Test
    @DisplayName("Название протокола может быть в любом регистре")
    fun case_6() {
        val url = "HTtPs://qwerty.ru/document/12/23"
        val expected = UrlInfo(protocol = Protocol.HTTPS, host = "qwerty.ru", port = 80, document = "/document/12/23")
        assertEquals(expected, UrlMatcher.getUrlInfo(url))
    }

    @Test
    @DisplayName("Валидный протокол может не содержать документ")
    fun case_7() {
        val url = "https://qwerty.ru"
        val expected = UrlInfo(protocol = Protocol.HTTPS, host = "qwerty.ru", port = 443, document = "")
        assertEquals(expected, UrlMatcher.getUrlInfo(url))
    }


    @Test
    @DisplayName("Валидный урл может иметь порт, равный 1 или 65535")
    fun case_8() {
        val url1 = "https://qwerty.dev:1"
        val expected1 = UrlInfo(protocol = Protocol.HTTPS, host = "qwerty.dev", port = 1, document = "")
        assertEquals(expected1, UrlMatcher.getUrlInfo(url1))

        val url2 = "https://qwerty.dev:65535"
        val expected2 = UrlInfo(protocol = Protocol.HTTPS, host = "qwerty.dev", port = 65535, document = "")
        assertEquals(expected2, UrlMatcher.getUrlInfo(url2))
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
                UrlMatcher.getUrlInfo(url)
            }
        }
    }

    @Test
    @DisplayName("Метод по получению информации об урле должен корректно парсить исходную строку")
    @Throws(MalformedURLException::class)
    fun getUrlInfo() {
        val testMap = mutableMapOf<String, UrlInfo>()
        // host
        testMap["https://qwerty.ru"] = UrlInfo(Protocol.HTTPS, "qwerty.ru", 443, "")
        testMap["https://q"] = UrlInfo(Protocol.HTTPS, "q", 443, "")
        testMap["https://."] = UrlInfo(Protocol.HTTPS, ".", 443, "")
        testMap["http://qwerty-23.ru:56"] = UrlInfo(Protocol.HTTP, "qwerty-23.ru", 56, "")

        //document
        testMap["ftp://q.ru/343/565"] = UrlInfo(Protocol.FTP, "q.ru", 21, "/343/565")
        testMap["https://q:67/f/d"] = UrlInfo(Protocol.HTTPS, "q", 67, "/f/d")
        testMap["http://qwerty-23.ru:569/we"] = UrlInfo(Protocol.HTTP, "qwerty-23.ru", 569, "/we")

        // port
        testMap["HTTPS://q.u:1/343/565"] = UrlInfo(Protocol.HTTPS, "q.u", 1, "/343/565")
        testMap["https://q:6"] = UrlInfo(Protocol.HTTPS, "q", 6, "")
        testMap.forEach { (key, value) ->
            assertEquals(value, UrlMatcher.getUrlInfo(key))
        }
    }

}