import exceptions.CalculatorException;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Calculator {

    public static void main(String[] args) {

    }

    public void calculate(File file) throws CalculatorException {
        String[] codeLines = new String[]{};

        Map<Integer, List<Token>> tokens = Parser.parse(codeLines);
        List<Expression> expressions = TokenConverter.convert(tokens);
        ExpressionEvaluator.evaluate(expressions);
    }

}
