package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class UpdateCommand extends AbstractCommand {
    public UpdateCommand() { super(Messages.COMMAND_UPDATE_NAME, Messages.COMMAND_UPDATE_SHORTCUT, Messages.COMMAND_UPDATE_DETAILS, Messages.COMMAND_UPDATE_HELP); }
    @Override public Command parse(String[] words) throws CommandParseException {
        Command c = null;
        if (words.length == 1 && words[0].isEmpty()) c = this;
        else if (words.length > 0 && matchCommandName(words[0])) { if (words.length != 1) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); c = this; }
        return c;
    }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException { game.update(); view.showGame(); }
}
