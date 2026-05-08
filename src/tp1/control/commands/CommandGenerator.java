package tp1.control.commands;
import java.util.Arrays;
import java.util.List;
import tp1.exceptions.CommandParseException;
import tp1.view.Messages;
public class CommandGenerator {
    private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(new LoadCommand(), new SaveCommand(), new AddObjectCommand(), new ActionCommand(), new UpdateCommand(), new ResetCommand(), new HelpCommand(), new ExitCommand());
    public static Command parse(String[] words) throws CommandParseException {
        Command command = null;
        int i = 0;
        while (i < AVAILABLE_COMMANDS.size() && command == null) { command = AVAILABLE_COMMANDS.get(i).parse(words); i++; }
        if (command == null) throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(words.length == 0 ? "" : words[0]));
        return command;
    }
    public static String commandHelp() {
        StringBuilder b = new StringBuilder();
        b.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
        for (Command c : AVAILABLE_COMMANDS) b.append(Messages.TAB).append(c.helpText()).append(Messages.LINE_SEPARATOR);
        return b.toString();
    }
}
