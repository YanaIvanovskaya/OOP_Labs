import Car.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void case_unknown() {
        String original = "";
        Assertions.assertEquals(Command.UNKNOWN, Command.getCommandFromString(original));
    }

    @Test
    void case_info() {
        String original = " Info ";
        Assertions.assertEquals(Command.INFO, Command.getCommandFromString(original));
    }

    @Test
    void case_cancel() {
        String original = "Cancel";
        Assertions.assertEquals(Command.CANCEL, Command.getCommandFromString(original));
    }

    @Test
    void case_engine_off() {
        String original = "EngineOff";
        Assertions.assertEquals(Command.ENGINE_OFF, Command.getCommandFromString(original));
    }

    @Test
    void case_engine_on() {
        String original = "EngineOn";
        Assertions.assertEquals(Command.ENGINE_ON, Command.getCommandFromString(original));
    }

    @Test
    void case_set_speed_40() {
        String original = "SetSpeed 40";
        Assertions.assertEquals(Command.SET_SPEED, Command.getCommandFromString(original));
    }

    @Test
    void case_set_speed_0() {
        String original = "SetSpeed 0";
        Assertions.assertEquals(Command.SET_SPEED, Command.getCommandFromString(original));
    }

    @Test
    void case_set_speed_12345() {
        String original = "SetSpeed 12345";
        Assertions.assertEquals(Command.SET_SPEED, Command.getCommandFromString(original));
    }

    @Test
    void case_set_gear_1() {
        String original = "SetGear 2";
        Assertions.assertEquals(Command.SET_GEAR, Command.getCommandFromString(original));
    }

    @Test
    void case_set_gear_2() {
        String original = "SetGear -1";
        Assertions.assertEquals(Command.SET_GEAR, Command.getCommandFromString(original));
    }

    @Test
    void case_set_gear_3() {
        String original = "SetGear -4";
        Assertions.assertEquals(Command.UNKNOWN, Command.getCommandFromString(original));
    }
}