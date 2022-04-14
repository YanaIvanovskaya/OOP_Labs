package exceptions;

public class SyntaxException extends CalculatorException {

    public SyntaxError cause;

    public SyntaxException(SyntaxError cause) {
        this.cause = cause;
    }

}
