package Calculator;

public enum VariableType {
    DECLARATION("var"),
    INITIALIZATION("let"),
    UNKNOWN("");

    String keyword;

    VariableType(String keyword) {
        this.keyword = keyword;
    }
}
