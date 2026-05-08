package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
public class ActionCommand extends AbstractCommand {
    private Action[] actions;
    private int count;
    
    public ActionCommand() { 
    	this(new Action[0], 0); 
    }
    
    private ActionCommand(Action[] actions, int count) { 
    	super(Messages.COMMAND_ACTION_NAME, Messages.COMMAND_ACTION_SHORTCUT, Messages.COMMAND_ACTION_DETAILS, Messages.COMMAND_ACTION_HELP); 
    	this.actions = actions; this.count = count; 
    }
    
    @Override public Command parse(String[] words) throws CommandParseException {
        Command c = null;
        if (words.length > 0 && matchCommandName(words[0])) {
            if (words.length < 2) throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
            Action[] parsed = new Action[words.length - 1];
            int total = 0;
            for (int i = 1; i < words.length; i++) {
                Action action = Action.find(words[i]);
                if (action != null) { parsed[total] = action; total++; }
            }
            if (total == 0) throw new CommandParseException(Messages.INVALID_ACTION_COMMAND);
            c = new ActionCommand(parsed, total);
        }
        return c;
    }
    @Override public void execute(GameModel game, GameView view) throws CommandExecuteException {
        for (int i = 0; i < count; i++) game.addAction(actions[i]);
        game.update();
        view.showGame();
    }
}
