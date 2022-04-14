//package Calculator;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//class VariableParserTest {
//
//    @Test
//    void getVariableOrNull() {
//
//    }
//
//    @Test
//    @DisplayName("Проверка объявления переменной")
//    void case_initialization_variable() {
//        HashMap<String, DeclarationVariable> correctDeclaration = new HashMap<>();
//        correctDeclaration.put("var q", new DeclarationVariable("q"));
//        correctDeclaration.put("var q1", new DeclarationVariable("q1"));
//        correctDeclaration.put("var q_1", new DeclarationVariable("q_1"));
//        correctDeclaration.put("var _", new DeclarationVariable("_"));
//        correctDeclaration.put("var Q8_45_W", new DeclarationVariable("Q8_45_W"));
//
//        for (Map.Entry<String, DeclarationVariable> entry : correctDeclaration.entrySet()) {
//            Assertions.assertEquals(entry.getValue(), VariableParser.getVariableOrNull(entry.getKey()));
//        }
//    }
//
//    @Test
//    @DisplayName("Некорректное выражение должно приводить к исключению")
//    void case_line_is_not_variable_expression() {
//
//    }
//
//    @Test
//    @DisplayName("Идентификатор переменной не может начинаться с цифры")
//    void case_identifier_starts_with_number() {
//
//    }
//
//    @Test
//    @DisplayName("Идентификатор переменной может начинаться с нижнего подчеркивания")
//    void case_identifier_starts_with_underscore() {
//
//    }
//
//    @Test
//    @DisplayName("Проверка разных вариаций идентификатора переменной")
//    void case_identifier_different_kinds() {
//
//    }
//
//
//    @Test
//    void case_declaration_variable() {
//
//    }
//
//    @Test
//    void case_reassignment_variable() {
//
//    }
//
//    @Test
//    void case_invalid_identifier() {
//
//    }
//
//    @Test
//    void case_invalid_value() {
//
//    }
//
//}