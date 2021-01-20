import javafx.application.Application;
import javafx.stage.Stage;


public class startMinesweeper extends Application {
	int m,n,bombs;
	
	MinesweeperView view;
	MinesweeperModel model;
	MinesweeperController controller;
	
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
		m=10;
		n=10;
		bombs=10;
		
		view = new MinesweeperView();
		model = new MinesweeperModel(m,n,bombs);
		controller = new MinesweeperController(model, view, m,n,bombs);
		
		view.SetOptions(topLevelStage, controller, "Minesweeper");
		topLevelStage = view.mainMenu();
		
		//topLevelStage = view.basicGame();
		topLevelStage.show();
	}
}
	
	