package Car;

public enum EngineControlError {
    CAR_IS_MOVING("Нельзя заглушить двигатель на ходу");

    public final String message;

    EngineControlError(String message) {
        this.message = message;
    }
}
