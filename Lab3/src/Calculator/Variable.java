//package Calculator;
//
//import java.util.Objects;
//
//public abstract class Variable {
//    final VariableType type;
//    final String identifier;
//
//    Variable(VariableType type, String identifier) {
//        this.type = type;
//        this.identifier = identifier;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Variable variable = (Variable) o;
//        return type == variable.type && Objects.equals(identifier, variable.identifier);
//    }
//
//}
