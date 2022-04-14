import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParserTest {

    static Map<Integer, List<Token>> tokenMap = new HashMap<>();
    static ArrayList<Token> expected = new ArrayList<>();

    @BeforeEach
    void clearTestData() {
        expected.clear();
        tokenMap.clear();
    }

    @Test
    @DisplayName("Между словами в выражении может быть любое количество пробелов")
    void case_0() {
        String[] code = {
                "   var  qwerty   ",
                "   let qwerty =  90   "
        };
        tokenMap.put(0, List.of(
                Token.Keyword.VarDeclaration,
                new Token.Identifier("qwerty"))
        );
        tokenMap.put(1, List.of(
                Token.Keyword.VarInitialization,
                new Token.Identifier("qwerty"),
                Token.Symbol.Assignment,
                new Token.Number("90")
        ));
        Assertions.assertEquals(tokenMap, Parser.parse(code));
    }

    @Test
    @DisplayName("Передача пустой строки или строки только из пробелов возвращает пустую мапу")
    void case_1() {
        Assertions.assertEquals(tokenMap, Parser.parse(new String[]{"", "       "}));
    }

    @Test
    @DisplayName("Проверка корректного объявления переменной")
    void case_2() {
        String[] code = {
                "var qwerty"
        };
        expected.add(Token.Keyword.VarDeclaration);
        expected.add(new Token.Identifier("qwerty"));
        tokenMap.put(0, expected);
        Assertions.assertEquals(tokenMap, Parser.parse(code));
    }

    @Test
    @DisplayName("Проверка корректной инициализации переменной числом")
    void case_3() {
        String[] code = {
                "let qwerty = -123.98"
        };
        expected.add(Token.Keyword.VarInitialization);
        expected.add(new Token.Identifier("qwerty"));
        expected.add(Token.Symbol.Assignment);
        expected.add(new Token.Number("-123.98"));
        tokenMap.put(0, expected);
        Assertions.assertEquals(tokenMap, Parser.parse(code));
    }

    @Test
    @DisplayName("Проверка корректной инициализации переменной значением другой переменной")
    void case_4() {
        String[] code = {
                "let qwerty = foo"
        };
        expected.add(Token.Keyword.VarInitialization);
        expected.add(new Token.Identifier("qwerty"));
        expected.add(Token.Symbol.Assignment);
        expected.add(new Token.Identifier("foo"));
        tokenMap.put(0, expected);
        Assertions.assertEquals(tokenMap, Parser.parse(code));
    }

}