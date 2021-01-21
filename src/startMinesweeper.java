import javafx.application.Application;
import javafx.stage.Stage;


public class startMinesweeper extends Application {
		
	// Main program file. Contains launch for JavaFX
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/*
	 * Load arguments into game.
	 * creates a new Model, view and controller.
	 * Set options for game
	 * Open a game window
	 * 
	 * Input: arguments given by user(size of board and number of bombs)
	 * Output: new game window
	 * 	
	 */
	public void start(Stage topLevelStage) throws Exception {
		
		
		
		MinesweeperController game = new MinesweeperController();
		
		game.view.SetOptions(topLevelStage, game, "Minesweeper");
		topLevelStage = game.view.mainMenu();
		
		topLevelStage.show();
	}
}
	
	