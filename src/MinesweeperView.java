import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.sun.javafx.scene.control.IntegerField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MinesweeperView {
	MinesweeperController controller;
	Stage stage;
	String title;
	Image[] images;
	Label counter;
	Label highscore;
	Label timer;

	
	//MinesweeperView initiates constructor to make view
	public MinesweeperView() {
		setPictures();
	}
	
	//Set game parameters
	public void SetOptions(Stage topLevelStage, MinesweeperController controller, String title) {
		this.stage = topLevelStage;
		this.controller = controller;
		this.title = title;
		this.highscore = new Label(Integer.toString(77));
	}
	
	//Load pictures to images array
	public void setPictures() {
		this.images = new Image[13];
		
		for(int i = 0; i<12; i++) {
			String name ="images/" + i + ".png";
			images[i] = new Image(name);
			
		}
		images[12] = new Image("images/tile.png");
	}
	
	/*
	 * Load pictures from view to game. If out of bounds, last index is returned.
	 * Input: x (array index)
	 * Output: Image from array index (x)
	 */
	public Image getPicture(int x) {
		if (!(x<this.images.length)||(x<0)) {
			x = this.images.length-1;
		}
		
		return images[x];
	}
	
	//Main menu Stage
	public Stage mainMenu() {
		this.stage.setTitle("Minesweeper");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//New game button
		Button newGameButton = new Button();
		newGameButton.setStyle("-fx-background-color: transparent;");
		newGameButton.setGraphic(new ImageView(new Image("images/newgame.png")));
		newGameButton.setOnAction(e -> mainSetup());
		
		Button highScoreButton = new Button();
		highScoreButton.setStyle("-fx-background-color: transparent;");
		highScoreButton.setGraphic(new ImageView(new Image("images/highscore.png")));
		highScoreButton.setOnAction(e ->highScore());
		

		
		//Exit button
		Button exitButton = new Button();
		exitButton.setGraphic(new ImageView(new Image("images/exit.png")));
		exitButton.setStyle("-fx-background-color: transparent;");
		exitButton.setOnAction(e -> mainMenu());

			
		
		VBox layout = new VBox();

		

		layout.getChildren().addAll(newGameButton, highScoreButton, exitButton);

		layout.setPadding(new Insets(300,400,300,300));
		layout.setBackground(background);
		Scene scene = new Scene(layout, 1000, 750);
		
		stage.setScene(scene);
		return stage;
	}
	public void mainSetup() {
		this.stage.setTitle("Minesweeper (Setup Game)");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		//Easy button
		Button easyButton = new Button();
		easyButton.setGraphic(new ImageView(new Image("images/easy.png")));
		easyButton.setStyle("-fx-background-color: transparent;");
		easyButton.setOnAction(e -> controller.gotoNewGame(10,10,10));
		
	   
		//Medium button
		Button mediumButton = new Button();
		mediumButton.setText("Medium");
		mediumButton.setGraphic(new ImageView(new Image("images/medium.png")));
		mediumButton.setStyle("-fx-background-color: transparent;");
		mediumButton.setOnAction(e -> controller.gotoNewGame(16,16,40));
	   
		
		//Hard button
		Button hardButton = new Button();
		hardButton.setText("Hard");
		hardButton.setGraphic(new ImageView(new Image("images/hard.png")));
		hardButton.setStyle("-fx-background-color: transparent;");
		hardButton.setOnAction(e -> controller.gotoNewGame(30,16,99));
	   
		
		//Custom button
		Button customButton = new Button();
		customButton.setText("Custom");
		customButton.setGraphic(new ImageView(new Image("images/custom.png")));
		customButton.setStyle("-fx-background-color: transparent;");
		customButton.setOnAction(e -> customizeGame());
		
		//Back button
		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image("images/back.png")));
		backButton.setStyle("-fx-background-color: transparent;");
		backButton.setOnAction(e -> mainMenu());
		
									
		
		VBox layout = new VBox(50);
		layout.getChildren().addAll(easyButton, mediumButton, hardButton,customButton, backButton);
		layout.setPadding(new Insets(200,400,300,300));
		layout.setBackground(background);
		
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}
	
	
	
	
	//Sets the stage

	public void gameWindow() {
			
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		this.stage.setTitle(title);
		
		
		HBox menuBar = new HBox();
		counter = new Label(Integer.toString(controller.model.getBombAmount()));
		counter.setPadding(new Insets(50,100,50,100));
		//highscore
		highscore.setPadding(new Insets(50,100,50,100));
		//timer
		timer = new Label();
		timer.setText("0");
		timer.setPadding(new Insets(50,100,50,100));
		//top Menu bar		
		menuBar.getChildren().addAll(this.highscore,counter,timer);
		menuBar.setPadding(new Insets(50,400,0,300));
				
		//full game window
		BorderPane layout = new BorderPane();
		
		GridPane grid = controller.getGrid();
		grid.setPadding(new Insets(200,200,200,200));
		
		layout.setBackground(background);
		layout.setTop(menuBar);
		layout.setCenter(grid);
		Scene scene = new Scene(layout, 1000, 750);
		
		stage.setScene(scene);
		
	}
	
	/*
	 * Open game over window.
	 * Input: Title and message to display.
	 * Output: new Stage with game over window. This window contains a button, if pressed begins a new game.
	 */
	
	public void alertBox(String title, String text) {
		//controller.clearButtonAction();
		Stage window = new Stage();
		window.setTitle(title);
		window.setOnCloseRequest(e -> controller.gotoMainMenu());
		//Force user to interact with window
		window.initModality(Modality.APPLICATION_MODAL);
		
		//
		Label label = new Label();
		label.setText(text);
		
		//button (Begin new game)
		Button button = new Button();
		button.setText("Main Menu");
		button.setOnAction(e -> {
			controller.gotoMainMenu();
			window.close();
		});
		
		
		
		//Layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setMinWidth(200);
		layout.setAlignment(Pos.CENTER);
		Scene scene= new Scene(layout);
		window.setScene(scene);
		window.show();
		// When window is closed. opens a new game.
		
	}

	public void customizeGame() {
		this.stage.setTitle("Minesweeper");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		this.stage.setTitle("Minesweeper (Custom Game)");
		
		IntegerField n = new IntegerField();
		n.setValue(10);
		IntegerField m = new IntegerField();
		m.setValue(10);
		IntegerField bombs = new IntegerField();
		bombs.setValue(10);
		
		//New game button
		Button newGameButton = new Button();
		newGameButton.setText("Start game");
		newGameButton.setGraphic(new ImageView(new Image("images/startgame.png")));
		newGameButton.setStyle("-fx-background-color: transparent;");
		newGameButton.setOnAction(e ->  controller.gotoNewGame(n.getValue(),m.getValue(),bombs.getValue()));
		
		Button leaveButton = new Button();
		leaveButton.setGraphic(new ImageView(new Image("images/back.png")));
		leaveButton.setStyle("-fx-background-color: transparent;");
		leaveButton.setOnAction(e -> mainMenu());
									
		
		VBox layout = new VBox();
		layout.getChildren().addAll(n,m,bombs,newGameButton, leaveButton);
		layout.setPadding(new Insets(200,400,300,300));
		layout.setBackground(background);
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}

	public void highScore() {
		this.stage.setTitle("Minesweeper");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//easy
		VBox highscoreNumberList = new VBox();
		for (int i=0; i<10;i++) {
			Label temp = new Label();
			temp.setText(controller.highscore[i]);
			highscoreNumberList.getChildren().add(temp);
		}
		VBox highscoreNameList = new VBox();
		for (int i=30; i<40;i++) {
			Label temp = new Label();
			temp.setText(controller.highscore[i]);
			highscoreNameList.getChildren().add(temp);
		}
		
		HBox highscores = new HBox();
		highscores.getChildren().addAll(highscoreNumberList,highscoreNameList);
		
		
		Button returnButton = new Button();
		returnButton.setGraphic(new ImageView(new Image("images/back.png")));
		returnButton.setStyle("-fx-background-color: transparent;");
		returnButton.setOnAction(e -> mainMenu());
		
		VBox layout = new VBox();
		layout.getChildren().addAll(highscores, returnButton);
		layout.setPadding(new Insets(200,400,300,300));
		Scene scene = new Scene(layout, 1000, 750);
		layout.setBackground(background);
		stage.setScene(scene);
		
	}
	
	public int[] getGrapicInt(int number) {
		int[] image= new int[3];
		image[0] = number/100;
		image[1] = number/10;
		image[2] = number;
		return image;
	}
	
	public Image getNumberAsImage(int number);
	String image = "images/" + number + ".png"
	return new Image()
	
}








	