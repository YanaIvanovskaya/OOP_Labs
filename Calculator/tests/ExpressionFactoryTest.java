import exceptions.SyntaxError;
import exceptions.SyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ExpressionFactoryTest {

    private static final List<Token> tokens = new ArrayList<>();

    @BeforeEach
    void clearTestData() {
        tokens.clear();
    }

    @Test
    @DisplayName("Если список токенов содержит неопознанный токен, выбрасывается исключение")
    void case_1() {
        tokens.add(Token.Keyword.VarDeclaration);
        tokens.add(new Token.Unexpected(")"));

        try {
            ExpressionFactory.createFromTokens(tokens);
            throw new AssertionError();
        } catch (SyntaxException ex) {
            Assertions.assertEquals(SyntaxError.UNEXPECTED_TOKEN, ex.cause);
        }
    }

    @Test
    @DisplayName("Если список токенов нельзя преобразовать в выражение, выбрасывается исключение")
    void case_2() {
        tokens.add(Token.Keyword.VarDeclaration);
        tokens.add(new Token.Identifier("qwerty"));
        tokens.add(new Token.Number("10"));

        try {
            ExpressionFactory.createFromTokens(tokens);
            throw new AssertionError();
        } catch (SyntaxException ex) {
            Assertions.assertEquals(SyntaxError.NOT_A_STATEMENT, ex.cause);
        }
    }

    @Test
    @DisplayName("Если первое слово в выражении не является ключевым, выбрасывается исключение")
    void case_3() {
        tokens.add(new Token.Identifier("qwerty"));
        tokens.add(new Token.Number("10"));

        try {
            ExpressionFactory.createFromTokens(tokens);
            throw new AssertionError();
        } catch (SyntaxException ex) {
            Assertions.assertEquals(SyntaxError.NOT_A_STATEMENT, ex.cause);
        }
    }

}