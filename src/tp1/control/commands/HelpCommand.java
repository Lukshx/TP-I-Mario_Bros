package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class HelpCommand extends NoParamsCommand {
    public HelpCommand() { super(Messages.COMMAND_HELP_NAME, Messages.COMMAND_HELP_SHORTCUT, Messages.COMMAND_HELP_DETAILS, Messages.COMMAND_HELP_HELP); }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException { view.showMessage(CommandGenerator.commandHelp()); }
}
