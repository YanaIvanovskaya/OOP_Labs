package Car;

public enum GearControlError {
    ENGINE_OFF("Двигатель заглушен. Нельзя изменить передачу"),
    CANNOT_SWITCH("Нельзя переключиться на эту передачу");

    public final String message;

    GearControlError(String message) {
        this.message = message;
    }
}
