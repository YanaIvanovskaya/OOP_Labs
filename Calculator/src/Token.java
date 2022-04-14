public interface Token {

    String getValue();

    record Unexpected(String value) implements Token {

        @Override
        public String getValue() {
            return value;
        }

    }

    record Identifier(String value) implements Token {

        @Override
        public String getValue() {
            return value;
        }

    }

    enum Keyword implements Token {

        VarDeclaration("var"),
        VarInitialization("let");

        public final String value;

        Keyword(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    enum Symbol implements Token {

        Assignment("=");

        public final String value;

        Symbol(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    record Number(String value) implements Token {

        @Override
        public String getValue() {
            return value;
        }

    }

}
