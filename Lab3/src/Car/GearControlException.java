package Car;

final public class GearControlException extends IllegalStateException {
    public final GearControlError error;

    GearControlException(GearControlError error) {
        this.error = error;
    }
}

