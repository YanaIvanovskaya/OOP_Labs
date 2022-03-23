package Car;

public enum Command {
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

    public static Command getCommandFromString(String str) {
        for (Command command : values()) {
            if (str.trim().matches(command.pattern + command.paramPattern)) {
                return command;
            }
        }
        return UNKNOWN;
    }

}
