import exceptions.SyntaxError;
import exceptions.SyntaxException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ExpressionFactory {

    private ExpressionFactory() {
    }

    static public @NotNull Expression createFromTokens(@NotNull List<Token> tokens) throws SyntaxException {
        ensureNoUnexpected(tokens);

        Token.Keyword keyWord = (Token.Keyword) tokens.get(0);
        Expression result = null;
        switch (keyWord) {
            case VarDeclaration -> result = createVarDeclaration(tokens);
            case VarInitialization -> result = createVarInitialization(tokens);
        }
        if (result == null) {
            throw new SyntaxException(SyntaxError.NOT_A_STATEMENT);
        } else return result;
    }

    static private void ensureNoUnexpected(List<Token> tokens) throws SyntaxException {
        if (tokens.stream().anyMatch((Token token) -> token instanceof Token.Unexpected)) {
            throw new SyntaxException(SyntaxError.UNEXPECTED_TOKEN);
        }
    }

    static private Expression createVarDeclaration(List<Token> tokens) throws SyntaxException {
        boolean isVarDeclaration = tokens.size() == 2
                && tokens.get(0) == Token.Keyword.VarDeclaration
                && tokens.get(1) instanceof Token.Identifier;

        if (isVarDeclaration) {
            return new Expression.Declaration(tokens.get(1).getValue());
        } else throw new SyntaxException(SyntaxError.NOT_A_STATEMENT);
    }

    static private Expression createVarInitialization(List<Token> tokens) throws SyntaxException {
        boolean isVarInitialization = tokens.size() == 4
                && tokens.get(0) == Token.Keyword.VarInitialization
                && tokens.get(1) instanceof Token.Identifier
                && tokens.get(2) == Token.Symbol.Assignment
                && (tokens.get(3) instanceof Token.Identifier
                || tokens.get(3) instanceof Token.Number);

        if (isVarInitialization) {
            return new Expression.Initialization(
                    tokens.get(1).getValue(),
                    tokens.get(3).getValue()
            );
        } else throw new SyntaxException(SyntaxError.NOT_A_STATEMENT);
    }

}
