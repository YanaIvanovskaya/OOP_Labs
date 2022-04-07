//package Calculator;
//
//import org.jetbrains.annotations.NotNull;
//
//abstract class ExpressionParser {
//
//    protected static String identifierPattern = "[a-zA-Z](\\w|_)+";
////    protected static String doublePattern = "([1-9]) | ([1-9][0-9]+)";
//
////    public abstract Expression parse(String line) throws ParseException;
//
//}
//
//final class VariableParser extends ExpressionParser {
//
//    public static Variable getVariableOrNull(@NotNull String line) {
//        VariableType type = getVariableType(line);
//        return switch (type) {
//            case DECLARATION -> getDeclarationVariableOrNull(line);
//            case INITIALIZATION -> getInitializationVariableOrNull(line);
//            default -> null;
//        };
//    }
//
//    private static VariableType getVariableType(String line) {
//        for (VariableType type : VariableType.values()) {
//            if (line.startsWith(type.keyword)) {
//                return type;
//            }
//        }
//        return VariableType.UNKNOWN;
//    }
//
//    private static DeclarationVariable getDeclarationVariableOrNull(String line) {
//        String regex = VariableType.DECLARATION.keyword + " " + identifierPattern;
//        if (line.matches(regex)) {
//            return new DeclarationVariable(
//                    line.substring(line.indexOf(" ") + 1)
//            );
//        } else return null;
//    }
//
//    private static InitializationVariable getInitializationVariableOrNull(String line) {
//        String regex = VariableType.INITIALIZATION.keyword + " " + identifierPattern + "=([0-9|.]+)";
//        if (!line.matches(regex)) return null;
//        try {
//            double value = Double.parseDouble(line.substring(line.indexOf("=") + 1));
//            return new InitializationVariable(
//                    line.substring(line.indexOf(" ") + 1, line.indexOf("=")),
//                    value
//            );
//        } catch (NumberFormatException ex) {
//            return null;
//        }
//    }
//
//}