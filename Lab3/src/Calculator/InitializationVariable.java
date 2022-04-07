package Calculator;

public class InitializationVariable extends Variable {

    public double value;

    InitializationVariable(String identifier, double value) {
        super(VariableType.INITIALIZATION, identifier);
        this.value = value;
    }

    @Override
    public String toString() {
        return "InitializationVariable {" +
//                "keyword=" + keyword.value +
                ", identifier='" + identifier + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
