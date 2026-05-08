package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class ExitCommand extends NoParamsCommand {
    public ExitCommand() { super(Messages.COMMAND_EXIT_NAME, Messages.COMMAND_EXIT_SHORTCUT, Messages.COMMAND_EXIT_DETAILS, Messages.COMMAND_EXIT_HELP); }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException { game.exit(); }
}
