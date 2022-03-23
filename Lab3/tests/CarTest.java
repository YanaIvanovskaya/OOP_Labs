import Car.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarTest {

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
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertTrue(car.turnOffEngine());
        Assertions.assertFalse(car.isTurnedOn());
    }

    @Test
    void turnOnEngine() {
        Car car = Car.createCustomCar(
                false,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertTrue(car.turnOnEngine());
        Assertions.assertTrue(car.isTurnedOn());
    }

    @Test
    void case_turn_off_if_gear_not_neutral_and_speed_not_zero() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                45,
                Car.Gear.TWO
        );
        Assertions.assertFalse(car.turnOffEngine());
    }

    @Test
    void setSpeed() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.ONE
        );
        Assertions.assertTrue(car.setSpeed(15));
        Assertions.assertEquals(15, car.getSpeed());
        Assertions.assertEquals(Car.Direction.FORWARD, car.getDirection());
    }

    @Test
    void case_set_speed_zero() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                3,
                Car.Gear.ONE
        );
        Assertions.assertTrue(car.setSpeed(0));
        Assertions.assertEquals(0, car.getSpeed());
        Assertions.assertEquals(Car.Direction.STAND, car.getDirection());
    }

    @Test
    void case_set_speed_if_gear_is_reverse() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.REVERSE
        );
        Assertions.assertTrue(car.setSpeed(10));
        Assertions.assertEquals(10, car.getSpeed());
        Assertions.assertEquals(Car.Direction.BACK, car.getDirection());
    }

    @Test
    void case_set_speed_if_gear_is_neutral() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                3,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setSpeed(10));
        Assertions.assertEquals(3, car.getSpeed());
    }

    @Test
    void case_set_negative_speed() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                3,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setSpeed(-10));
        Assertions.assertEquals(3, car.getSpeed());
    }

    @Test
    void case_set_speed_more_than_150() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                90,
                Car.Gear.FIVE
        );
        Assertions.assertFalse(car.setSpeed(200));
        Assertions.assertEquals(90, car.getSpeed());
    }

    @Test
    void case_set_speed_if_it_not_in_range_of_gear() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                35,
                Car.Gear.THREE
        );
        Assertions.assertFalse(car.setSpeed(90));
        Assertions.assertEquals(35, car.getSpeed());
    }

    @Test
    void case_set_speed_if_engine_is_turn_off() {
        Car car = Car.createCustomCar(
                false,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setSpeed(15));
        Assertions.assertEquals(0, car.getSpeed());
    }

    @Test
    void setGear() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                25,
                Car.Gear.ONE
        );
        Assertions.assertTrue(car.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.TWO, car.getGear());
    }

    @Test
    void case_set_gear_if_engine_turn_off() {
        Car car = Car.createCustomCar(
                false,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_gear_if_current_speed_not_in_range() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                10,
                Car.Gear.ONE
        );
        Assertions.assertFalse(car.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.ONE, car.getGear());
    }

    @Test
    void case_set_reverse_gear_if_speed_not_zero() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                10,
                Car.Gear.ONE
        );
        Assertions.assertFalse(car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.ONE, car.getGear());
    }

    @Test
    void case_set_reverse_gear_if_direction_not_stand() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                0,
                Car.Gear.ONE
        );
        Assertions.assertFalse(car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.ONE, car.getGear());
    }

    @Test
    void case_set_reverse_gear_if_all_ok() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.ONE
        );
        Assertions.assertTrue(car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.REVERSE, car.getGear());
    }

    @Test
    void case_set_not_reverse_gear_if_current_reverse() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.BACK,
                5,
                Car.Gear.REVERSE
        );
        Assertions.assertFalse(car.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.REVERSE, car.getGear());
    }

    @Test
    void case_set_reverse_if_current_neutral_and_speed_zero() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertTrue(car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.REVERSE, car.getGear());
    }

    @Test
    void case_set_reverse_if_current_neutral_and_speed_not_zero() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.BACK,
                5,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setGear(Car.Gear.REVERSE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_neutral_if_current_reverse() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.BACK,
                5,
                Car.Gear.REVERSE
        );
        Assertions.assertTrue(car.setGear(Car.Gear.NEUTRAL));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_neutral_if_current_not_reverse() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                5,
                Car.Gear.ONE
        );
        Assertions.assertTrue(car.setGear(Car.Gear.NEUTRAL));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_not_neutral_gear_if_current_neutral() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.STAND,
                0,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setGear(Car.Gear.TWO));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_set_not_neutral_gear_if_current_neutral_and_direction_back() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.BACK,
                15,
                Car.Gear.NEUTRAL
        );
        Assertions.assertFalse(car.setGear(Car.Gear.ONE));
        Assertions.assertEquals(Car.Gear.NEUTRAL, car.getGear());
    }

    @Test
    void case_correct_car_info() {
        Car car = Car.createCustomCar(
                true,
                Car.Direction.FORWARD,
                5,
                Car.Gear.ONE
        );
        String expected = """
                ENGINE: ON
                SPEED: 5
                GEAR: ONE
                DIRECTION: FORWARD
                """;
        Assertions.assertEquals(expected, car.getInfo());
    }

}