interface Expression {

    interface Variable extends Expression {
        String getName();

        String getValue();
    }

    record Declaration(String identifier) implements Variable {
        @Override
        public String getName() {
            return identifier;
        }

        @Override
        public String getValue() {
            return null;
        }
    }

    record Initialization(String identifier, String value, ValueType type) implements Variable {
        @Override
        public String getName() {
            return identifier;
        }

        @Override
        public String getValue() {
            return value;
        }

        enum ValueType {
            NUMBER,
            IDENTIFIER
        }
    }

}
