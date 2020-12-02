package commands;

public class Controller {
    private static Command command;
    private Controller() {}
    public static void executeCommand(Command command) {
        command.execute();
    }
    public static void execute() {
        command.execute();
        command = null;
    }
    public static void setCommand(Command command) {
        Controller.command = command;
    }
}
