package Car;

final public class SpeedControlException extends IllegalStateException {
    public final SpeedControlError error;

    SpeedControlException(SpeedControlError error) {
        this.error = error;
    }
}
