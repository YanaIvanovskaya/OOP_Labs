import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TokenConverter {

    private TokenConverter() {
    }

    public static @NotNull List<Expression> convert(
            @NotNull Map<Integer, List<Token>> tokenMap
    ) {
        ArrayList<Expression> expressions = new ArrayList<>();

        for (Map.Entry<Integer, List<Token>> codeLine : tokenMap.entrySet()) {
            List<Token> tokens = codeLine.getValue();
            if (!tokens.isEmpty()) {
                expressions.add(ExpressionFactory.createFromTokens(tokens));
            }
        }
        return expressions;
    }

}
