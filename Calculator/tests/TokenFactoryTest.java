import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenFactoryTest {

    @Test
    @DisplayName("Слово var является ключевым словом объявления переменной")
    void case_1() {
        Assertions.assertEquals(Token.Keyword.VarDeclaration, TokenFactory.getFromString("var"));
    }

    @Test
    @DisplayName("Слово let является ключевым словом инициализации переменной")
    void case_2() {
        Assertions.assertEquals(Token.Keyword.VarInitialization, TokenFactory.getFromString("let"));
    }

    @Test
    @DisplayName("Знак = является символом присваивания")
    void case_3() {
        Assertions.assertEquals(Token.Symbol.Assignment, TokenFactory.getFromString("="));
    }

    @Test
    @DisplayName("Проверка идентификаторов")
    void case_4() {
        Assertions.assertEquals(new Token.Identifier("qwerty"), TokenFactory.getFromString("qwerty"));
    }

    @Test
    @DisplayName("Проверка чисел")
    void case_5() {
        Assertions.assertEquals(new Token.Number("-98.5"), TokenFactory.getFromString("-98.5"));
    }

}