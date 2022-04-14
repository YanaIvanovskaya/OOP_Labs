import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public final class TokenFactory {

    private TokenFactory() {
    }

    static public Token getFromString(@NotNull String value) {
        return switch (value) {
            case "var" -> Token.Keyword.VarDeclaration;
            case "let" -> Token.Keyword.VarInitialization;
            case "=" -> Token.Symbol.Assignment;
            default -> getFromString2(value);
        };
    }

    private static Token getFromString2(String value) {
        Pattern identifier = Pattern.compile("[a-zA-Z]((\\w|_)+|)");
        if (value.matches(identifier.pattern())) {
            return new Token.Identifier(value);
        }

        Pattern number = Pattern.compile("(-|)[0-9]+(.([0-9]+)|)");
        if (value.matches(number.pattern())) {
            return new Token.Number(value);
        }

        return new Token.Unexpected(value);
    }

}
