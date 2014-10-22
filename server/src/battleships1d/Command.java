package battleships1d;

/**
 * Created by Thomas on 21/10/2014.
 */
public class Command {

    private String command;
    private String[] parameters;

    public static Command parseCommand(String command) {
        String[] split = command.split("::");
        if (split.length == 0) return null;
        Command commandObject = new Command();
        commandObject.command = split[0];
        commandObject.parameters = new String[split.length - 1];

        for(int i = 0; i < commandObject.parameters.length; i++) {
            commandObject.parameters[i] = split[i + 1];
        }
        return commandObject;
    }

    public String getRawCommand() {
        String cmd = command + "::";
        for (String s : parameters) cmd += s + "::";
        return cmd.substring(0, cmd.length() - 2);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
