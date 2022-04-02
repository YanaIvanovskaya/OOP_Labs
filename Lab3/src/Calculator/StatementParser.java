//package Calculator;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//
//enum Statement {
//
//    UNKNOWN("",
//            VARIABLE_DECLARATION(),
//            VARIABLE_INITIALIZATION();
////    FUNCTION_INITIALIZATION,
////    FUNCTION_REDECLARATION;
//
//    String pattern;
//
//    Statement(String pattern) {
//        this.pattern = pattern;
//    }
//
//
//
//    static Statement getFromStringOrNull() {
//
//    }
//
//}
//
////class Variable {
////    private String identifier;
////    private double value;
////
////    private Variable(String identifier, double value) {
////        this.identifier = identifier;
////        this.value = value;
////    }
////
////    void setValueFrom(Variable variable) {
////        this.value = variable.value;
////    }
////
////    public enum Type {
////        DECLARATION,
////        INITIALIZATION;
////
////        String keyWord;
////
////
////    }
////
////    static Variable getFromStringOrNull() {
////
////    }
////
////}
//
//
//public final class StatementParser {
//
//
//    public static void main(String[] args) {
//
//        String varTest = """
//                var a
//                var  67d
//                var 65
//                var var_qwerty_
//                var ___
//                var
//                    var   qwerty
//                var &rt
//                """;
//
//        Map<String, Double> variables = new HashMap<>();
//
//
//
//        // var [identifier]
//        Pattern variableDeclarationRegex =
//                Pattern.compile("var " + identifierRegex + "=" + numberRegex);
//
//        // let [identifier]=[identifier]|double
//        Pattern variableInitializationRegex = Pattern.compile("let " + identifierRegex + "=" + "(" + numberRegex + "|" + identifierRegex + ")");
//
//        for (String line : varTest.split("\n")) {
//            System.out.println(line);
//        }
//    }
//
//    private static final String identifierRegex = "[a-zA-z](/w|_)+)";
//    private static final String integerNumber = "([1-9][0-9]+?)";
//    private static final String doubleNumber = "(0." + integerNumber + "|" + integerNumber + "." + integerNumber + ")";
//    private static final String numberRegex = "(" + integerNumber + "|" + doubleNumber + ")";
//
//    private Map<String, Double> variablesMap = new HashMap<>();
//
//    public boolean parse(String line) {
//        boolean hasError = false;
//
//        boolean isVariableDeclaration = line.startsWith("var");
//        boolean isVariableInitialization = line.startsWith("let");
//
//        if (isVariableDeclaration) {
//            boolean isValid  = Pattern.matches(variableDeclarationRegex,line)
//
//        } else if (isVariableInitialization) {
//
//        }
//
//        return false;
//    }
//
//
//}
