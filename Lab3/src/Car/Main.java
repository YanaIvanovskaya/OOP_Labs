package Car;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Car car = Car.createCar();

        printProgramInfo();

        try (Scanner scanner = new Scanner(System.in)) {
            Command command;
            do {
                String input = scanner.nextLine();
                command = Command.getCommandFromString(input);
                switch (command) {
                    case INFO -> System.out.println(car.getInfo());
                    case ENGINE_OFF -> processCommandEngineOff(car);
                    case ENGINE_ON -> processCommandEngineOn(car);
                    case SET_GEAR -> processCommandSetGear(car, input);
                    case SET_SPEED -> processCommandSetSpeed(car, input);
                    case UNKNOWN -> System.out.println("Неизвестная команда");
                }
            } while (command != Command.CANCEL);
        }
    }

    private static void printProgramInfo() {
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
    }

    private static void processCommandEngineOff(Car car) {
        try {
            car.turnOffEngine();
            System.out.println("Двигатель заглушен");
        } catch (EngineControlException ex) {
            System.out.println(ex.error.message);
        }
    }

    private static void processCommandEngineOn(Car car) {
        car.turnOnEngine();
        System.out.println("Двигатель заведен");
    }

    private static void processCommandSetGear(Car car, String input) {
        Command command = Command.SET_GEAR;
        Matcher gearMatcher = Pattern.compile(command.paramPattern).matcher(input);

        String strGear = gearMatcher.find() ? gearMatcher.group() : "";
        Car.Gear gear = Car.Gear.getGearFromStringOrNull(strGear);

        if (gear == null) {
            System.out.println("Неизвестная передача: " + strGear);
        } else try {
            car.setGear(gear);
            System.out.println("Передача переключена на " + gear);
        } catch (GearControlException ex) {
            System.out.println(ex.error.message);
        }
    }

    private static void processCommandSetSpeed(Car car, String input) {
        Command command = Command.SET_SPEED;
        Matcher speedMatcher = Pattern.compile(command.paramPattern).matcher(input);

        String strSpeed = speedMatcher.find() ? speedMatcher.group() : "";
        int speed = strSpeed.matches("([0-9]+)") ? Integer.parseInt(strSpeed) : -1;
        if (speed == -1) {
            System.out.println("Некорректная скорость: " + strSpeed);
        } else try {
            car.setSpeed(speed);
            System.out.println("Скорость переключена на " + speed);
        } catch (SpeedControlException ex) {
            System.out.println(ex.error.message);
        }
    }

}
