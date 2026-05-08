package tp1.control.commands;
import tp1.view.Messages;
public abstract class AbstractCommand implements Command {
    private final String name;
    private final String shortcut;
    private final String details;
    private final String help;
    public AbstractCommand(String name, String shortcut, String details, String help) {
        this.name = name; this.shortcut = shortcut; this.details = details; this.help = help;
    }
    protected boolean matchCommandName(String word) { return word != null && (name.equalsIgnoreCase(word) || shortcut.equalsIgnoreCase(word)); }
    @Override public String helpText() { return Messages.COMMAND_HELP_TEXT.formatted(details, help); }
}
