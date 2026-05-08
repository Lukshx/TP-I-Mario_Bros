package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class ResetCommand extends AbstractCommand {
    private final Integer level;
    public ResetCommand() { this(null); }
    private ResetCommand(Integer level) { super(Messages.COMMAND_RESET_NAME, Messages.COMMAND_RESET_SHORTCUT, Messages.COMMAND_RESET_DETAILS, Messages.COMMAND_RESET_HELP); this.level = level; }
    @Override public Command parse(String[] words) throws CommandParseException {
        Command c = null;
        if (words.length > 0 && matchCommandName(words[0])) {
            if (words.length > 2) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
            if (words.length == 1) c = new ResetCommand();
            else {
                try { c = new ResetCommand(Integer.parseInt(words[1])); }
                catch (NumberFormatException e) { throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(words[1]), e); }
            }
        }
        return c;
    }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException {
        try { if (level == null) game.reset(); else game.reset(level); view.showGame(); }
        catch (GameModelException e) { throw new CommandExecuteException(e.getMessage(), e.getCause()); }
    }
}
