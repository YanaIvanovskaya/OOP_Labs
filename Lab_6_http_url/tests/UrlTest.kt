import lab_2_task_5.Protocol
import lab_6.Url
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.MalformedURLException
import kotlin.test.assertEquals

internal class UrlTest {

    @Test
    @DisplayName("Конструктор с параметром-строкой выполняет парсинг строкового представления URL-а," +
            " в случае ошибки парсинга выбрасывает исключение UrlParsingError")
    fun case_0() {
        val url1 = "https:/qwerty-123.ru:89/doc/123"
        assertThrows<MalformedURLException> {
            Url(url1)
        }
        val url2 = "https://qwerty-123.ru:89/doc/123"
        val actual = Url(url2)
        assertEquals("qwerty-123.ru", actual.domain)
        assertEquals(89, actual.port)
        assertEquals("/doc/123", actual.document)
        assertEquals(Protocol.HTTPS, actual.protocol)
    }

    @Test
    @DisplayName("Конструктор с параметрами domain, document, protocol, port инициализирует URL на основе переданных параметров")
    fun case_1() {
        val url = Url(
                domain = "qwerty-123.ru",
                document = "/doc/123",
                port = 89,
                protocol = Protocol.HTTP
        )
        assertEquals("qwerty-123.ru", url.domain)
        assertEquals(89, url.port)
        assertEquals("/doc/123", url.document)
        assertEquals(Protocol.HTTP, url.protocol)
    }

    @Test
    @DisplayName("Если указан некорректный домен,выбрасывается исключение")
    fun case_2() {
        assertThrows<java.lang.IllegalArgumentException> {
            Url(domain = "", document = "/doc", port = 120, protocol = Protocol.HTTP)
        }
    }

    @Test
    @DisplayName("Если указан некорректный документ,выбрасывается исключение")
    fun case_3() {
        assertThrows<java.lang.IllegalArgumentException> {
            Url(domain = "qwerty.ru", document = "&###", port = 120, protocol = Protocol.HTTP)
        }
    }

    @Test
    @DisplayName("Если указан некорректный порт,выбрасывается исключение")
    fun case_4() {
        assertThrows<java.lang.IllegalArgumentException> {
            Url(domain = "qwerty.ru", document = "/doc", port = 0, protocol = Protocol.HTTP)
        }
    }

    @Test
    @DisplayName("Конструктор с параметрами domain, document инициализирует URL c протоколом HTTP и портом 80")
    fun case_5() {
        val url = Url(
                domain = "qwerty-123.ru",
                document = "/doc/123"
        )
        assertEquals("qwerty-123.ru", url.domain)
        assertEquals(80, url.port)
        assertEquals("/doc/123", url.document)
        assertEquals(Protocol.HTTP, url.protocol)
    }


    @Test
    @DisplayName("Если имя документа не начинается с символа /, то добавляет / к имени документа")
    fun case_6() {
        val url = Url(
                domain = "qwerty-123.ru",
                document = "doc/123"
        )
        assertEquals("qwerty-123.ru", url.domain)
        assertEquals(80, url.port)
        assertEquals("/doc/123", url.document)
        assertEquals(Protocol.HTTP, url.protocol)
    }

    @Test
    @DisplayName("Свойство url должна возвращать строковое представление HttpUrl")
    fun case_7() {
        val url = Url(
                domain = "qwerty-123.ru",
                document = "doc/123"
        )
        val expected = "http://qwerty-123.ru/doc/123"
        assertEquals(url.url, expected)
    }

    @Test
    @DisplayName("Стандартный порт не должен включаться в строковое представление HttpUrl")
    fun case_8() {
        val url = Url(
                domain = "qwerty-123.ru",
                document = "doc/123",
                port = 443,
                protocol = Protocol.HTTPS
        )
        val expected = "https://qwerty-123.ru/doc/123"
        assertEquals(url.url, expected)
    }

}