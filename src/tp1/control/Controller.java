package tp1.control;

import java.util.Scanner;

import tp1.logic.Game;
import tp1.view.GameView;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private GameView view;
	Scanner input = new Scanner(System.in);

	public Controller(Game game, GameView view) {
		this.game = game;
		this.view = view;
	}


	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 * 
	 */
	public void run() {
		view.showWelcome();
		boolean play = true;
		view.showGame();
		while(play)
		{
			System.out.println("Command> ");
			String codigo = input.nextLine();
			System.out.println("Has escrito: " + codigo);
//			if(input.equals"salir" || input == "SALIR")
			{
				play = false;
			}

		}
	

		//TODO fill your code: The main loop that displays the game, asks the user for input, and executes the action.


		
		view.showEndMessage();
	}

}
