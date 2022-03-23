package Car;

public class Car {

    private static final int MAX_SPEED = 150;

    private boolean isTurnedOn;
    private Direction direction;
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

        final int value;
        final int maxSpeed;
        final int minSpeed;

        Gear(int value, int min, int max) {
            this.value = value;
            this.maxSpeed = max;
            this.minSpeed = min;
        }

        static Gear getGearFromStringOrNull(String gear) {
            for (Gear g : values()) {
                if (String.valueOf(g.value).equals(gear)) {
                    return g;
                }
            }
            return null;
        }
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public Gear getGear() {
        return gear;
    }

    private Car() {
    }

    public static Car createCar() {
        Car car = new Car();
        car.isTurnedOn = false;
        car.direction = Direction.STAND;
        car.speed = 0;
        car.gear = Gear.NEUTRAL;
        return car;
    }

    public static Car createCustomCar(
            boolean isTurnedOn,
            Direction direction,
            int speed,
            Gear gear
    ) {
        Car car = new Car();
        car.isTurnedOn = isTurnedOn;
        car.direction = direction;
        car.speed = speed;
        car.gear = gear;
        return car;
    }

    public boolean turnOnEngine() {
        isTurnedOn = true;
        return true;
    }

    public boolean turnOffEngine() {
        if (speed == 0 && gear == Gear.NEUTRAL) {
            isTurnedOn = false;
            return true;
        }

        return false;
    }

    public boolean setGear(Gear gear) {
        if (!isTurnedOn) {
            System.out.println("Двигатель заглушен. Нельзя изменить передачу");
            return false;
        }
        boolean canSwitch;
        boolean canSwitchReverse = speed == 0 && direction == Direction.STAND;
        boolean isSpeedInRange = speed >= gear.minSpeed && speed <= gear.maxSpeed;

        switch (gear) {
            case REVERSE -> canSwitch = canSwitchReverse;
            case NEUTRAL -> canSwitch = true;
            default -> {
                switch (this.gear) {
                    case REVERSE -> canSwitch = canSwitchReverse;
                    case NEUTRAL -> canSwitch = isSpeedInRange && direction != Direction.BACK;
                    default -> canSwitch = isSpeedInRange;
                }
            }
        }
        if (canSwitch) {
            this.gear = gear;
        } else System.out.println("Нельзя переключиться на эту передачу");
        return canSwitch;
    }

    public boolean setSpeed(int speed) {
        boolean isNeutral = gear == Gear.NEUTRAL;
        boolean isSpeedInRangeOfCurrentGear = speed >= gear.minSpeed && speed <= gear.maxSpeed;

        if (speed < 0) {
            System.out.println("Переключение на отрицательную скорость невозможно");
            return false;
        }

        if (!isTurnedOn) {
            System.out.println("Двигатель заглушен. Нельзя задать скорость");
            return false;
        }

        if (!isSpeedInRangeOfCurrentGear) {
            System.out.println("Скорость не входит в диапазон текущей передачи");
            return false;
        }

        if (isNeutral && speed > this.speed) {
            System.out.println("На нейтральной передаче нельзя разогнаться");
            return false;
        }

        this.speed = speed;
        if (direction == Direction.STAND) {
            switch (gear) {
                case REVERSE -> direction = Direction.BACK;
                default -> direction = Direction.FORWARD;
            }
        } else if (speed == 0) {
            direction = Direction.STAND;
        }
        return true;
    }

    public String getInfo() {
        return "ENGINE: " + (isTurnedOn ? "ON" : "OFF") + "\n"
                + "SPEED: " + speed + "\n"
                + "GEAR: " + gear + "\n"
                + "DIRECTION: " + direction + "\n";
    }

}
