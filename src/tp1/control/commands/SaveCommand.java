package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class SaveCommand extends AbstractCommand {
    private String fileName;
    public SaveCommand() { this(null); }
    private SaveCommand(String fileName) { super(Messages.COMMAND_SAVE_NAME, Messages.COMMAND_SAVE_SHORTCUT, Messages.COMMAND_SAVE_DETAILS, Messages.COMMAND_SAVE_HELP); this.fileName = fileName; }
    @Override public Command parse(String[] words) throws CommandParseException {
        Command c = null;
        if (words.length > 0 && matchCommandName(words[0])) { if (words.length != 2) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); c = new SaveCommand(words[1]); }
        return c;
    }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException {
        try { game.save(fileName); view.showMessage(Messages.TAB + Messages.FILE_CORRECTLY_SAVED.formatted(fileName) + Messages.LINE_SEPARATOR); }
        catch (GameModelException e) { throw new CommandExecuteException(e.getMessage(), e.getCause()); }
    }
}
