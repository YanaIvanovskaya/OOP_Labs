import Car.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarTest {

    private static Car testCar = Car.createCar();

    @BeforeEach
    void set_default_car() {
        testCar = Car.createCar();
    }

    @Test
    void createCar() {
        Car car = Car.createCar();
        Assertions.assertEquals(Car.Direction.STAND, car.getDirection());
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    void turnOffEngine() {
        testCar.turnOnEngine();
        Assertions.assertDoesNotThrow(testCar::turnOffEngine);
        Assertions.assertFalse(testCar.isTurnedOn());
    }

    @Test
    void test_custom_car_creation() {
        for (int i = 0; i < 151; i++) {
            Car car = getDrivenCarWithSpeed(i);
            Assertions.assertEquals(i, car.getSpeed());
            Assertions.assertTrue(Car.Gear.isCorrectSpeedForGear(car.getSpeed(), car.getGear()));
        }
    }

    private static Car getDrivenCarWithSpeed(int speed) {
        Car car = Car.createCar();
        car.turnOnEngine();
        car.setGear(Car.Gear.ONE);
        int startSpeed = 0;
        while (startSpeed <= speed) {
            try {
//                System.out.println(startSpeed);
                car.setSpeed(startSpeed);
                startSpeed++;
            } catch (SpeedControlException ex) {
                if (ex.error == SpeedControlError.NOT_IN_RANGE) {
                    try {
                        Car.Gear newGear;
                        switch (car.getGear()) {
                            case ONE -> newGear = Car.Gear.TWO;
                            case TWO -> newGear = Car.Gear.THREE;
                            case THREE -> newGear = Car.Gear.FOUR;
                            case FOUR -> newGear = Car.Gear.FIVE;
                            default -> newGear = null;
                        }
                        if (newGear != null) {
                            car.setGear(newGear);
                        } else break;
                    } catch (GearControlException ex2) {
                        System.out.println(ex2.error.message);
                    }
                }
            }
        }
        return car;
    }

    @Test
    void turnOnEngine() {
        Assertions.assertDoesNotThrow(testCar::turnOnEngine);
        Assertions.assertTrue(testCar.isTurnedOn());
    }

    @Test
    void case_turn_off_if_gear_not_neutral_and_speed_not_zero() {
        Car car = getDrivenCarWithSpeed(10);
        Assertions.assertThrows(EngineControlException.class, car::turnOffEngine);
    }

    @Test
    void setSpeed() {
        Car car = getDrivenCarWithSpeed(5);
        Assertions.assertDoesNotThrow(() -> car.setSpeed(5));
        Assertions.assertEquals(5, car.getSpeed());
        Assertions.assertEquals(Car.Direction.FORWARD, car.getDirection());
    }

    @Test
    void case_set_speed_zero() {
        Car car = getDrivenCarWithSpeed(0);
        Assertions.assertDoesNotThrow(() -> car.setSpeed(0));
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertEquals(Car.Direction.STAND, car.getDirection());
    }

    @Test
    void case_set_negative_speed() {
        Car car = getDrivenCarWithSpeed(0);
        Assertions.assertThrows(SpeedControlException.class, () -> car.setSpeed(-2));
    }

    @Test
    void case_set_speed_if_gear_is_reverse() {
        testCar.turnOnEngine();
        testCar.setGear(Car.Gear.REVERSE);
        Assertions.assertDoesNotThrow(() -> testCar.setSpeed(10));
        Assertions.assertEquals(10, testCar.getSpeed());
        Assertions.assertEquals(Car.Direction.BACK, testCar.getDirection());
    }

    @Test
    void case_set_speed_if_gear_is_neutral() {
        Car car = getDrivenCarWithSpeed(3);
        car.setGear(Car.Gear.NEUTRAL);
        Assertions.assertThrows(SpeedControlException.class, () -> car.setSpeed(10));
        Assertions.assertEquals(3, car.getSpeed());
    }

    @Test
    void case_set_speed_more_than_150() {
        Car car = getDrivenCarWithSpeed(90);
        Assertions.assertThrows(SpeedControlException.class, () -> car.setSpeed(200));
        Assertions.assertEquals(90, car.getSpeed());
    }

    @Test
    void case_set_speed_if_it_not_in_range_of_gear() {
        Car car = getDrivenCarWithSpeed(35);
        Assertions.assertThrows(SpeedControlException.class, () -> car.setSpeed(90));
        Assertions.assertEquals(35, car.getSpeed());
    }

    @Test
    void case_set_speed_if_engine_is_turn_off() {
        Assertions.assertThrows(SpeedControlException.class, () -> testCar.setSpeed(15));
        Assertions.assertEquals(0, testCar.getSpeed());
    }

    @Test
    void setGear() {
        Car car = getDrivenCarWithSpeed(25);
        Assertions.assertDoesNotThrow(() -> car.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.TWO, car.getGear());
    }

    @Test
    void case_set_gear_if_engine_turn_off() {
        Assertions.assertThrows(GearControlException.class, () -> testCar.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, testCar.getGear());
    }

    @Test
    void case_set_gear_if_current_speed_not_in_range() {
        Car car = getDrivenCarWithSpeed(10);
        Assertions.assertThrows(GearControlException.class, () -> car.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.ONE, car.getGear());
    }

    @Test
    void case_set_reverse_gear_if_speed_not_zero() {
        Car car = getDrivenCarWithSpeed(10);
        Assertions.assertThrows(GearControlException.class, () -> car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.ONE, car.getGear());
    }

    @Test
    void case_set_reverse_gear_if_all_ok() {
        Car car = getDrivenCarWithSpeed(0);
        Assertions.assertDoesNotThrow(() -> car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.REVERSE, car.getGear());
    }

    @Test
    void case_set_not_reverse_gear_if_current_reverse() {
        testCar.turnOnEngine();
        testCar.setGear(Car.Gear.REVERSE);
        testCar.setSpeed(5);

        Assertions.assertThrows(GearControlException.class, () -> testCar.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.REVERSE, testCar.getGear());
    }

    @Test
    void case_set_reverse_if_current_neutral_and_speed_zero() {
        testCar.turnOnEngine();
        Assertions.assertDoesNotThrow(() -> testCar.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.REVERSE, testCar.getGear());
    }

    @Test
    void case_set_reverse_if_current_neutral_and_speed_not_zero() {
        testCar.turnOnEngine();
        testCar.setGear(Car.Gear.REVERSE);
        testCar.setSpeed(5);
        testCar.setGear(Car.Gear.NEUTRAL);

        Assertions.assertThrows(GearControlException.class, () -> testCar.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, testCar.getGear());
    }

    @Test
    void case_set_neutral_if_current_reverse() {
        testCar.turnOnEngine();
        testCar.setGear(Car.Gear.REVERSE);
        testCar.setSpeed(5);

        Assertions.assertDoesNotThrow(() -> testCar.setGear(Car.Gear.NEUTRAL));
        Assertions.assertEquals(Car.Gear.NEUTRAL, testCar.getGear());
    }

    @Test
    void case_set_neutral_if_current_not_reverse() {
        Car car = getDrivenCarWithSpeed(5);
        Assertions.assertDoesNotThrow(() -> car.setGear(Car.Gear.NEUTRAL));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_not_neutral_gear_if_current_neutral() {
        testCar.turnOnEngine();
        Assertions.assertThrows(GearControlException.class, () -> testCar.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.NEUTRAL, testCar.getGear());
    }

    @Test
    void case_set_not_neutral_gear_if_current_neutral_and_direction_back() {
        testCar.turnOnEngine();
        testCar.setGear(Car.Gear.REVERSE);
        testCar.setSpeed(15);
        testCar.setGear(Car.Gear.NEUTRAL);

        Assertions.assertThrows(GearControlException.class, () -> testCar.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, testCar.getGear());
    }

    @Test
    void case_correct_car_info() {
        Car car = getDrivenCarWithSpeed(5);
        String expected = """
                ENGINE: ON
                SPEED: 5
                GEAR: ONE
                DIRECTION: FORWARD
                """;
        Assertions.assertEquals(expected, car.getInfo());
    }

}