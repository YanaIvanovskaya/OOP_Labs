package exceptions;

public class SemanticException extends CalculatorException {

    public SemanticError cause;

    public SemanticException(SemanticError cause) {
        this.cause = cause;
    }
}
