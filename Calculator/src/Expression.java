interface Expression {

    interface Variable extends Expression {
        String getName();
    }

    record Declaration(String identifier) implements Variable {
        @Override
        public String getName() {
            return identifier;
        }
    }

    record Initialization(String identifier, String value) implements Variable {
        @Override
        public String getName() {
            return identifier;
        }

        public boolean isRedeclaration() {
            return false;
        }
    }

}
