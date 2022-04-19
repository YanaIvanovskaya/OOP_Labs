import exceptions.SemanticException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ExpressionEvaluatorTest {

    List<Expression> expressions = new ArrayList<>();

    @BeforeEach
    void clearTestData() {
        expressions.clear();
    }

    @Test
    @DisplayName("Если объявляется переменная с существующим именем, выбрасывается исключение")
    void case_1() {
        expressions.add(new Expression.Declaration("qwerty"));
        expressions.add(new Expression.Declaration("qwerty"));

        Assertions.assertThrows(SemanticException.class, () -> ExpressionEvaluator.evaluate(expressions));
    }

    @Test
    @DisplayName("При обращении к необъявленной переменной выбрасывается исключение")
    void case_2() {
        expressions.add(new Expression.Declaration("qwerty"));
        expressions.add(new Expression.Initialization("foo", "boo", Expression.Initialization.ValueType.IDENTIFIER));

        Assertions.assertThrows(SemanticException.class, () -> ExpressionEvaluator.evaluate(expressions));
    }

}