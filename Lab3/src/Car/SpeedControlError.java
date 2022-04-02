package Car;

public enum SpeedControlError {
    NEGATIVE_SPEED("Переключение на отрицательную скорость невозможно"),
    ENGINE_OFF("Двигатель заглушен. Нельзя задать скорость"),
    NOT_IN_RANGE("Скорость не входит в диапазон текущей передачи"),
    NEUTRAL_GEAR("На нейтральной передаче нельзя разогнаться");

    public final String message;

    SpeedControlError(String message) {
        this.message = message;
    }
}
