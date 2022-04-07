package Calculator;

public abstract class Variable {
    final VariableType type;
    final String identifier;
//    String value;

    Variable(VariableType type, String identifier/*, String value*/) {
        this.type = type;
        this.identifier = identifier;
//        this.value = value;
    }

}
