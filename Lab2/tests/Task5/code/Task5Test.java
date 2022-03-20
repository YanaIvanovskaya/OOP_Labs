package Task5.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

class Task5Test {

    @Test
    void case_string_is_not_a_url() {
        Assertions.assertThrows(MalformedURLException.class, () -> UrlMatcher.getUrlInfo("qwerty"));
    }

    @Test
    void case_url_without_port() {
        String url = "http://qwerty.ru/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_url_with_port() {
        String url = "http://qwerty.ru:345/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_url_with_unknown_protocol() {
        String url = "ws://qwerty.ru:345/document/12/23";
        Assertions.assertFalse(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_url_port_is_out_of_range_1_65535() {
        String url = "http://qwerty.ru:65888/document/12/23";
        Assertions.assertFalse(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_url_with_protocol_in_upper_case() {
        String url = "HTTPS://qwerty.ru/document/12/23";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_url_without_document() {
        String url = "https://qwerty.ru";
        Assertions.assertTrue(UrlMatcher.canParseUrl(url));
    }

    @Test
    void case_these_urls_correct() {
        String[] correctUrls = {
                "http://qwerty-123.dev.com/user/123?page=4311",
                "http://q.com",
                "https://qwerty.dev:1/123",
                "https://qwerty.dev:65535",
                "ftp://qwerty.qwerty.qwerty.ru/12345/54545?page=none&offline=0",
                "ftp://12-34-343.ru",
        };
        for (String url : correctUrls) {
            Assertions.assertTrue(UrlMatcher.canParseUrl(url));
        }
    }

    @Test
    void check_info_for_url_without_port() throws MalformedURLException {
        String url = "http://qwerty-123.dev.com/user/123?page=4311";
        String expectedInfo = """
                http://qwerty-123.dev.com/user/123?page=4311
                PROTOCOL: http
                HOST: qwerty-123.dev.com
                PORT: 80
                DOC: user/123?page=4311
                """;
        Assertions.assertEquals(UrlMatcher.getUrlInfo(url), expectedInfo);
    }

    @Test
    void check_info_for_url_without_document() throws MalformedURLException {
        String url = "http://qwerty-123.dev.com";
        String expectedInfo = """
                http://qwerty-123.dev.com
                PROTOCOL: http
                HOST: qwerty-123.dev.com
                PORT: 80
                """;
        Assertions.assertEquals(UrlMatcher.getUrlInfo(url), expectedInfo);
    }

}