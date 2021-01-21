import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Point;
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
import javafx.util.Duration;

//
public class MinesweeperController  {
	public MinesweeperModel model;
	public MinesweeperView view;
	private ObservableList<Node> children; //children is an array which contains a map of buttons and images.
	private GridPane grid;
	private Timeline timeline;
	private int time;
	private int dificulty; // 0 is easy, 1 is advance, 2 is hard, 3 is custom.
	public String newName;
	public ArrayList<String> highscore;
	
	/*
	 * Import View and highscore into controller.
	 */
	public MinesweeperController() throws FileNotFoundException{
		
		this.model = new MinesweeperModel(10,10,10);
		this.highscore = loadHighscore();
		this.view = new MinesweeperView();
		
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
		//basiscase
		if (!(model.buttonclicked(pressedButton.getPos()))) {
			
			
			//check if button got flag
			if (!(pressedButton.flag)) {
				model.clickbutton(pressedButton.getPos());
				
				if (!(model.isGAmeStarted())) {
							startTimer();
				}
				//Changes button to picture
				grid.getChildren().remove(pressedButton);
				int cell = getNext(pressedButton.getPos());
				grid.add(new ImageView(view.getPicture(cell)), pressedButton.getPos().x, pressedButton.getPos().y);
				
				//recursive function. run if no neighbours got bombs.
				if (cell==0) {
					
					ArrayList<MinesweeperButton> temp = pressedButton.getneighbours();
					while (temp.size()>0) {
						buttonPressed(temp.remove(0));
					}
				}
			}
				// test if game is finished, if not, test if game is won/lost.
				if (!(model.isGameStopped())){
					checkEndCondition(model, pressedButton.getPos());
				}
			}
		}
		
	
	
	/*
	 * Creates GridPane and inserts buttons. Updates ObservableList, with MinesweeperButtons.
	 * Output: New GridPane
	 */
	public GridPane getGrid() {
		
		grid = new GridPane();
		for (int i =0; i<model.getn(); i++) {
			for (int j =0; j<model.getm(); j++) {
				MinesweeperButton button = new MinesweeperButton(j,i);
				button.setGraphic(new ImageView(view.images[12]));
				button.setOnMouseClicked(event -> {
					//System.out.println();
					
					if  (event.getButton()== MouseButton.PRIMARY) {
						buttonPressed(button);
						
					} else if (event.getButton()== MouseButton.SECONDARY) {
						if (model.isGAmeStarted()) {
							
							if (!(button.flag)) {
								button.setGraphic(new ImageView(view.images[10]));
								model.placeflag();
							} else {
                        		button.setGraphic(new ImageView(view.images[12]));
								model.removeflag();
							}
							button.changeFlag();
							updateViewflags();
						}
					}
				});
				
				grid.add(button, j, i);
			}
		
		}
		
		children = grid.getChildren();
		
		for (int i=0;i<children.size();i++) {
			MinesweeperButton button = (MinesweeperButton) children.get(i);
			button.setNeighbours(model.getm(),model.getn(),children);
		}
		
		return grid;
	}
	
	/*
	 * Set model to new game with Input parameters.
	 * Input: Width,Height,Bombs,difficulty
	 */
	public void gotoNewGame(int m, int n, int bombAmount, int dificulty) {
		model = new MinesweeperModel(m,n,bombAmount);
		this.time=0;
		
		this.dificulty= dificulty;
		view.gameWindow();
	}

	/*
	 * Set window to main menu
	 */
	public void gotoMainMenu() {
		view.mainMenu();
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
			model.stopGame();
			timeline.stop();
		}
		if (condition == 9) {
			view.alertBox("Defeat", "game over");
			model.stopGame();
			timeline.stop();
		}
	
	}
	
	
	
	public void updateViewflags() {
		int temp = model.getFlagPlaced();
		view.updatebombs(temp);
	}
	
	/*
	 * Begin Timeline object. Set time interval to 1 second.
	 */
	 public void startTimer() {
	        timeline = new Timeline(
	            new KeyFrame(Duration.seconds(0),
	                e ->ticToc()),
	            new KeyFrame(Duration.seconds(1)));
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();
	    }
	
	 /*
	  * action set to happen each second.
	  * time variable updated and cannot exceed 999.
	  * graphic for time label updated.
	  */
	 public void ticToc() {
		 this.time++;
		 if (time>=1000) {
			 time=999;
		 }
		 view.updateTime(time);
		 
	 }
	 
	 /*
	  * Load highscore from file("highscore.mwp") and store it internally in ArrayList
	  * Output: ArrayList<String> with loaded highscore.
	  * 
	  */
	 public ArrayList<String> loadHighscore() throws FileNotFoundException {
		 ArrayList<String> loadedhighscore= new ArrayList<String>();
		 Scanner file = new Scanner(new File("highscore.mwp"));
		 for (int i=0; i<40;i++) {
			 loadedhighscore.add(file.next());
	     }
		 return loadedhighscore;
	 } 
	 
	 /*
	  * Save highscore to file. Run whenever a score is beaten by player.
	  */
	 
	 public void saveHighScore() throws FileNotFoundException {
		 
		 PrintWriter pw = new PrintWriter("highscore.mwp");
		 for(int i=0; i<40; i++ ) {
			 pw.write(highscore.get(i)+" ");
		 }
		 pw.close();
	 }
	 
	
	 /*
	  * Runs through Highscore and check if a player beat a previous score.
	  * If score is beaten, a new window is opened, where player enters name.
	  * 
	  */
	 
	 public void checkHighScore() throws FileNotFoundException {
		 int updated =0;
		 int list= this.dificulty*5;
		 for (int i=list;i<list+5;i++) {
			 if ((Integer.parseInt(this.highscore.get(i))>this.time) && updated==0) {
				 updated=1;
				 view.newHighScoreName(i);
				 
				 ;
			 }
		 }
		 
	 }	 
	 
	 /*
	  * Player is entered into highscore.
	  * Input: time it took to beat level(Highscore placement) 
	  */
		 
	public void newHighscore(int place) throws FileNotFoundException {
		this.highscore.add(place, Integer.toString(time));
		this.highscore.remove(20/(3-this.dificulty));
		//this.highscore.add(place+15,"Bos");
		this.highscore.add(place+20, this.newName);
		this.highscore.remove(20/(3-this.dificulty)+20);
		
		;
		saveHighScore();
	}
	
	/*
	 * Reset highscore to default parameters and saves the highscore to file.
	 */
	
	public void resetHighScore() {
		this.highscore.clear();
		for (int i=0; i<20;i++) {
			this.highscore.add("999");
		}
		for (int i=0; i<20;i++) {
			this.highscore.add("tmp");
		}
		//catch file not found
		try {
			saveHighScore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * During Custom game setup, this function checks if given inputs will be accepted.
	 * If any input exeeds usable values, they are set to the min or max settings.
	 */
	public void checkAmount() {
		
		
		if (view.mAmount<=model.MINGRIDSIZE){
			view.mAmount=model.MINGRIDSIZE;
		}
		if (view.mAmount>=model.MAXGRIDSIZE) {
			view.mAmount=model.MAXGRIDSIZE;
		}
		if (view.nAmount<=model.MINGRIDSIZE){
			view.nAmount=model.MINGRIDSIZE;
		}
		if (view.nAmount>=model.MAXGRIDHEIGHT) {
			view.nAmount=model.MAXGRIDHEIGHT;
		}
		
		if (view.bombAmount<=0) {
			view.bombAmount=1;
		}
		if (view.bombAmount>=99 ) {
			view.bombAmount=99;
		}
		if (view.bombAmount>=(view.mAmount*view.nAmount)) {
			view.bombAmount=(view.mAmount*view.nAmount)-1;
		}
			
	}
	
	/*
	 * Check which diffifulty game is started.
	 * Output: game dificulty.
	 */
	 
	 public int getDificulty() {
		 return this.dificulty;
	 }
		 
	 
}	

	