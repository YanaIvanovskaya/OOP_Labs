package Car;

public class Car {

    private static final int MAX_SPEED = 150;

    private boolean isTurnedOn;
    private Gear gear;
    private int speed;

    public enum Direction {
        FORWARD,
        BACK,
        STAND
    }

    public enum Gear {
        REVERSE(-1, 0, 20),
        NEUTRAL(0, 0, MAX_SPEED),
        ONE(1, 0, 30),
        TWO(2, 20, 50),
        THREE(3, 30, 60),
        FOUR(4, 40, 90),
        FIVE(5, 50, MAX_SPEED);

        public final int value;
        final int maxSpeed;
        final int minSpeed;

        Gear(int value, int min, int max) {
            this.value = value;
            this.maxSpeed = max;
            this.minSpeed = min;
        }

        public static Gear getGearFromStringOrNull(String gear) {
            for (Gear g : values()) {
                if (String.valueOf(g.value).equals(gear)) {
                    return g;
                }
            }
            return null;
        }

        public static boolean isCorrectSpeedForGear(int speed, Gear gear) {
            return speed >= gear.minSpeed && speed <= gear.maxSpeed;
        }
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public Direction getDirection() {
        if (speed == 0) {
            return Direction.STAND;
        } else return speed < 0 ? Direction.BACK : Direction.FORWARD;
    }

    public int getSpeed() {
        return Math.abs(speed);
    }

    public Gear getGear() {
        return gear;
    }

    private Car() {
    }

    public static Car createCar() {
        Car car = new Car();
        car.isTurnedOn = false;
        car.speed = 0;
        car.gear = Gear.NEUTRAL;
        return car;
    }

    public void turnOnEngine() {
        isTurnedOn = true;
    }

    public void turnOffEngine() {
        if (getSpeed() == 0 && gear == Gear.NEUTRAL) {
            isTurnedOn = false;
        } else throw new EngineControlException(EngineControlError.CAR_IS_MOVING);
    }

    public void setGear(Gear gear) throws GearControlException {
        if (!isTurnedOn) {
            throw new GearControlException(GearControlError.ENGINE_OFF);
        }
        boolean canSwitch;
        boolean canSwitchReverse = getSpeed() == 0;
        boolean isSpeedInRange = getSpeed() >= gear.minSpeed && getSpeed() <= gear.maxSpeed;

        switch (gear) {
            case REVERSE -> canSwitch = canSwitchReverse;
            case NEUTRAL -> canSwitch = true;
            default -> {
                switch (this.gear) {
                    case REVERSE -> canSwitch = canSwitchReverse;
                    case NEUTRAL -> canSwitch = isSpeedInRange && speed >= 0;
                    default -> canSwitch = isSpeedInRange;
                }
            }
        }
        if (canSwitch) this.gear = gear;
        else throw new GearControlException(GearControlError.CANNOT_SWITCH);
    }

    public void setSpeed(int speed) throws SpeedControlException {
        if (speed < 0) throw new SpeedControlException((SpeedControlError.NEGATIVE_SPEED));

        if (!isTurnedOn) throw new SpeedControlException(SpeedControlError.ENGINE_OFF);

        boolean inNotInRange = speed >= gear.minSpeed && speed <= gear.maxSpeed;
        if (!inNotInRange)
            throw new SpeedControlException(SpeedControlError.NOT_IN_RANGE);

        boolean isNeutral = gear == Gear.NEUTRAL;
        if (isNeutral && speed > getSpeed())
            throw new SpeedControlException(SpeedControlError.NEUTRAL_GEAR);

        this.speed = (gear == Gear.REVERSE) ? -speed : speed;
    }

    public String getInfo() {
        return "ENGINE: " + (isTurnedOn() ? "ON" : "OFF") + "\n"
                + "SPEED: " + getSpeed() + "\n"
                + "GEAR: " + getGear() + "\n"
                + "DIRECTION: " + getDirection() + "\n";
    }

}
