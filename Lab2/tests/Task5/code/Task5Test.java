package Task5.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

class Task5Test {

    @Test
    @DisplayName("Попытка получить информацию у некорректного урла должна приводить к исключению")
    void case_string_is_not_a_url() {
        Assertions.assertThrows(MalformedURLException.class, () -> UrlMatcher.getUrlInfo("qwerty"));
    }

    @Test
    @DisplayName("Валидный урл может не содержать порт")
    void case_url_without_port() {
        String url = "http://qwerty.ru/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Валидный урл может содержать порт")
    void case_url_with_port() {
        String url = "http://qwerty.ru:345/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Если порт не находится в диапазоне [1;65535], то урл некорректен")
    void case_url_port_is_out_of_range_1_65535() {
        String url = "http://qwerty.ru:65888/document/12/23";
        Assertions.assertFalse(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Если у урла протокол не HTTP/HTTPS/FTP, то урл некорректен")
    void case_url_with_unknown_protocol() {
        String url = "ws://qwerty.ru:345/document/12/23";
        Assertions.assertFalse(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Название протокола может быть в upper case")
    void case_url_with_protocol_in_upper_case() {
        String url = "HTTPS://qwerty.ru/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Валидный протокол может не содержать документ")
    void case_url_without_document() {
        String url = "https://qwerty.ru";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    @DisplayName("Валидный урл может иметь порт, равный 1 или 65535")
    void case_port_min_max_value() {
        String[] correctUrls = {
                "https://qwerty.dev:1/123",
                "https://qwerty.dev:65535",
        };
        for (String url : correctUrls) {
            Assertions.assertTrue(UrlMatcher.canParseUrl(url));
        }
    }

    @Test
    @DisplayName("Валидный урл может содержать в хосте точки, дефисы, цифры")
    void case_these_urls_correct() {
        String[] correctUrls = {
                "http://qwerty-123.dev.com",
                "http://q.com",
                "ftp://qwerty.qwerty.qwerty.ru",
                "ftp://12-34-343.ru",
                "ftp://.-",
        };
        for (String url : correctUrls) {
            Assertions.assertTrue(UrlMatcher.canParseUrl(url));
        }
    }

    @Test
    @DisplayName("Метод по получению информации об урле должен корректно парсить исходную строку")
    void getUrlInfo() throws MalformedURLException {
        HashMap<String, UrlInfo> testMap = new HashMap<>();
        // host
        testMap.put("https://qwerty.ru", new UrlInfo(Protocol.HTTPS, "qwerty.ru", 443, ""));
        testMap.put("https://q", new UrlInfo(Protocol.HTTPS, "q", 443, ""));
        testMap.put("https://.", new UrlInfo(Protocol.HTTPS, ".", 443, ""));
        testMap.put("http://qwerty-23.ru:56", new UrlInfo(Protocol.HTTP, "qwerty-23.ru", 56, ""));

        //document
        testMap.put("ftp://q.ru/343/565", new UrlInfo(Protocol.FTP, "q.ru", 21, "/343/565"));
        testMap.put("https://q:67/f/d", new UrlInfo(Protocol.HTTPS, "q", 67, "/f/d"));
        testMap.put("http://qwerty-23.ru:569/we", new UrlInfo(Protocol.HTTP, "qwerty-23.ru", 569, "/we"));

        // port
        testMap.put("HTTPS://q.u:1/343/565", new UrlInfo(Protocol.HTTPS, "q.u", 1, "/343/565"));
        testMap.put("https://q:6", new UrlInfo(Protocol.HTTPS, "q", 6, ""));

        for (Map.Entry<String, UrlInfo> entry : testMap.entrySet()) {
            Assertions.assertEquals(entry.getValue(), UrlMatcher.getUrlInfo(entry.getKey()));
        }
    }

}