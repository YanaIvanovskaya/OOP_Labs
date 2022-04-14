import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Parser {
    private Parser() {
    }

    public static Map<Integer, List<Token>> parse(@NotNull String[] code) {
        HashMap<Integer, List<Token>> tokensMap = new HashMap<>();

        int counter = 0;

        for (String line : code) {
            if (!line.trim().isEmpty()) {
                ArrayList<Token> tokens = new ArrayList<>();
                for (String word : line.trim().split("( )+")) {
                    tokens.add(TokenFactory.getFromString(word));
                }

                tokensMap.put(counter, tokens);
                counter++;
            }
        }

        return tokensMap;
    }

}
