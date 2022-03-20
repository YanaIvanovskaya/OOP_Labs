import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command {
    UNKNOWN(""),
    CANCEL("Cancel"),
    INFO("Info"),
    ENGINE_ON("EngineOn"),
    ENGINE_OFF("EngineOff"),
    SET_SPEED("SetSpeed ", "([0-9]+)"),
    SET_GEAR("SetGear ", "(-1|0|1|2|3|4|5)");

    final String pattern;
    String paramPattern = "";

    Command(String pattern) {
        this.pattern = pattern;
    }

    Command(String pattern, String paramPattern) {
        this.pattern = pattern;
        this.paramPattern = paramPattern;
    }

    static Command getCommandFromString(String str) {
        for (Command command : values()) {
            if (str.trim().matches(command.pattern + command.paramPattern)) {
                return command;
            }
        }
        return UNKNOWN;
    }

}


public class Main {

    public static void main(String[] args) {
        Car car = Car.createCar();
        System.out.println("""
                Машина создана.
                Вводите следующие команды для управления:
                Info - текущие показатели
                EngineOn - завести двигатель
                EngineOff - заглушить двигатель
                SetGear <int in range [-1;5]> - сменить передачу
                SetSpeed <int in range [0;150]> - сменить скорость
                Cancel - выход из программы
                """
        );

        try (Scanner scanner = new Scanner(System.in)) {
            Command command;
            do {
                String input = scanner.nextLine();
                command = Command.getCommandFromString(input);
                switch (command) {
                    case INFO -> System.out.println(car.getInfo());
                    case ENGINE_OFF -> {
                        if (car.turnOffEngine()) {
                            System.out.println("Двигатель заглушен");
                        }
                    }
                    case ENGINE_ON -> {
                        if (car.turnOnEngine()) {
                            System.out.println("Двигатель заведен");
                        }
                    }
                    case SET_GEAR -> {
                        Matcher gearMatcher = Pattern.compile(command.paramPattern).matcher(input);
                        if (gearMatcher.find()) {
                            String strGear = gearMatcher.group();
                            Car.Gear gear = Car.Gear.getGearFromStringOrNull(strGear);
                            if (gear == null) {
                                System.out.println("Неизвестная передача: " + strGear);
                            } else if (car.setGear(gear)) {
                                System.out.println("Передача переключена на " + gear);
                            }
                        }
                    }
                    case SET_SPEED -> {
                        Matcher speedMatcher = Pattern.compile(command.paramPattern).matcher(input);
                        if (speedMatcher.find()) {
                            String strSpeed = speedMatcher.group();
                            int speed;
                            try {
                                speed = Integer.parseInt(strSpeed);
                            } catch (NumberFormatException ignored) {
                                speed = -1;
                            }
                            if (speed == -1) {
                                System.out.println("Некорректная скорость: " + strSpeed);
                            } else if (car.setSpeed(speed)) {
                                System.out.println("Скорость переключена на " + speed);
                            }
                        }
                    }
                    case UNKNOWN -> System.out.println("Неизвестная команда");
                }
            } while (command != Command.CANCEL);
        }
    }

}
