package Calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StatementParser {

    public static void main(String[] args) {

        String test = """
                var a
                var a=3
                var a=45
                """;

        Map<String, Double> variables = new HashMap<>();

        Pattern variableDeclarationRegex = Pattern.compile("var [a-zA-z](/w+)=([0-9]+)");

    }

}
