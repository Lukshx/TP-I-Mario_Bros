package tp1.control.commands;
import java.util.Arrays;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class AddObjectCommand extends AbstractCommand {
    private String[] objectWords;
    public AddObjectCommand() { this(new String[0]); }
    private AddObjectCommand(String[] objectWords) { super(Messages.COMMAND_ADD_OBJECT_NAME, Messages.COMMAND_ADD_OBJECT_SHORTCUT, Messages.COMMAND_ADD_OBJECT_DETAILS, Messages.COMMAND_ADD_OBJECT_HELP); this.objectWords = objectWords; }
    @Override public Command parse(String[] words) throws CommandParseException {
        Command c = null;
        if (words.length > 0 && matchCommandName(words[0])) {
            if (words.length < 3) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
            c = new AddObjectCommand(Arrays.copyOfRange(words, 1, words.length));
        }
        return c;
    }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException {
        try { game.addObject(objectWords); view.showGame(); }
        catch (GameModelException e) { throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e); }
    }
}
