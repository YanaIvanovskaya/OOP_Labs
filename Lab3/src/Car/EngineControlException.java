package Car;

final public class EngineControlException extends IllegalStateException {
    final EngineControlError error;

    EngineControlException(EngineControlError error) {
        this.error = error;
    }
}
