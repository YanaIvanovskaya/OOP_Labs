package Calculator;

public class ReassignmentVariable extends Variable {

    public String value;

    ReassignmentVariable(String identifier, String value) {
        super(VariableType.INITIALIZATION, identifier);
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReassignmentVariable {" +
//                "keyword=" + keyword.value +
                ", identifier='" + identifier + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
