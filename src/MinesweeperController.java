import java.util.ArrayList;
import java.awt.Button;
import java.util.Scanner;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MinesweeperController  {
	MinesweeperModel model;
	MinesweeperView view;
	//children is an array which contains a map of buttons and images for JavaFX
	ObservableList<Node> children;
	GridPane grid;
	private int m;
	private int bombAmount;
	private int n;
	Timeline timeline;
	int time;
	public String[] highscore;
	
	//Import model and view to controller through constructor
	public MinesweeperController(MinesweeperModel model, MinesweeperView view,
								 int m, int n, int bombAmount) throws FileNotFoundException{
		this.view = view;
		this.model = model;
		this.m =m;
		this.n = n;
		this.bombAmount = bombAmount;
		this.highscore = loadHighscore();
	}
	
	/*
	 *Calls model for change in position.
	 *Input: point from view.
	 *Output: number of neighbors (9 = bombs).
	 */
	public int getNext(Point updatepoint) {
		int cell = model.getPos(updatepoint);
		return cell;	
	}
	
	/*
	 *Program is informed which button the user has pressed. Checks endCondition to see if game is still operational.
	 *When button is pressed, function removes button and inserts image.
	 *Input: MinesweeperButton.
	 */
	public void buttonPressed(MinesweeperButton pressedButton) {
		if (!(pressedButton.flag)) {
			grid.getChildren().remove(pressedButton);
			int cell = getNext(pressedButton.getPos());
			grid.add(new ImageView(view.getPicture(cell)), pressedButton.getPos().x, pressedButton.getPos().y);
			
			
			/*int gameState = model.testConditions(button.getPos());
			childrens = grid.getChildren(); */
			
			if (cell==0) {
				
				ArrayList<MinesweeperButton> temp = pressedButton.getneighbours();
				while (temp.size()>0) {
					buttonPressed(temp.remove(0));
				}
			}
		}
			
			checkEndCondition(model, pressedButton.getPos());
			
	}
		
	
	
	/*
	 * Creates GridPane and inserts buttons. Updates children, with list of buttons.
	 * Output: New GridPane
	 */
	public GridPane getGrid() {
		
		grid = new GridPane();
		for (int i =0; i<this.n; i++) {
			for (int j =0; j<this.m; j++) {
				MinesweeperButton button = new MinesweeperButton(j,i);
				button.setGraphic(new ImageView(view.images[12]));
				button.setOnMouseClicked(event -> {
					//System.out.println();
					if (!(model.isGAmeStarted())) {
							startTimer();
						}
					if  (event.getButton()== MouseButton.PRIMARY) {
						buttonPressed(button);
						
							
						
					} else if (event.getButton()== MouseButton.SECONDARY) {
						button.changeFlag();
						
						if (button.flag) {
                            button.setGraphic(new ImageView(view.images[10]));
                            model.placeflag();
                        } else {
                            button.setGraphic(new ImageView(view.images[12]));
                            model.removeflag();
                        }
						updateViewflags();
					}
					
				});
				
				grid.add(button, j, i);
			}
		
		}
		
		children = grid.getChildren();
		
		for (int i=0;i<children.size();i++) {
			MinesweeperButton button = (MinesweeperButton) children.get(i);
			button.setNeighbours(this.m,this.n,children);
		}
		
		return grid;
	}
	
	/*
	 * Ask model for new game. lose input stage. View loads new game.
	 * Input: Stage
	 */
	public void gotoNewGame(Stage thisStage) {
		model = new MinesweeperModel(this.m,this.n,bombAmount);
	}
	
	public void gotoNewGame(int m, int n, int bombAmount) {
		model = new MinesweeperModel(m,n,bombAmount);
		this.n = model.getn();
		this.m = model.getm();
		this.bombAmount = model.getBombAmount();
		
		view.gameWindow();
	}

	
	public void gotoMainMenu() {
		model = new MinesweeperModel(this.m,this.n,bombAmount);
		view.mainMenu();
	}
	
	/*
	 * Remaining buttons are deactivated 
	 */
	public void clearButtonAction() {
		for (int i =0; i< ((this.m*this.n)-model.getAmountClickedFields()); i++) {
			MinesweeperButton temp =(MinesweeperButton) children.get(i);
			temp.setOnAction(null);
		}
	}
	
	/*
	 * Checks for victory and defeat condition.
	 * If endCondition value is 8, the user has won.
	 * If endCondition value is 9, the user has lost.
	 * If endCondition value is 0, the game continues.
	 * Input: Minesweeper controller and point.
	 */
	public void checkEndCondition(MinesweeperModel minesweeperModel, Point point) {
		
		int condition =model.getEndCondition();
		if (condition == 8) {
			view.alertBox("Victory", "Congratulations. You won!");
			
			timeline.stop();
		}
		if (condition == 9) {
			view.alertBox("Defeat", "game over");
			timeline.stop();
		}
	
	}
	public void updateViewflags() {
		System.out.println("update");
		int temp = model.getFlagPlaced();
		view.bombs.setGraphic(view.getIntHBox(temp));
	}
	
	 public void startTimer() {
	        timeline = new Timeline(
	            new KeyFrame(Duration.seconds(0),
	                e ->ticToc()),
	            new KeyFrame(Duration.seconds(1)));
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();
	    }
	 public void ticToc() {
		 this.time++;
		 view.time.setGraphic(view.getIntHBox(this.time));
		 
	 }
	 
	 public String[] loadHighscore() throws FileNotFoundException {
		 String[] loadedhighscore= new String[60];
		 Scanner file = new Scanner(new File("src/highscore.txt"));
		 for (int i=0; i<30;i++) {
			 loadedhighscore[i] = file.next();
	     }
		
		 return loadedhighscore;
	 } 
	 public void saveHighScore() throws FileNotFoundException {
		 
		 PrintWriter pw = new PrintWriter("src/highscore.txt");
		 for(int i=0; i<30; i++ ) {
			 pw.write(highscore[i]);
		 }
		 pw.close();
	 }
	 
	 
}	

	