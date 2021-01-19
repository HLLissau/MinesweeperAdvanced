import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.sun.javafx.scene.control.IntegerField;

import javafx.application.Platform;
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
	
	HBox highscore;
	Label bombs;
	Label time;

	
	//MinesweeperView initiates constructor to make view
	public MinesweeperView() {
		setPictures();
	}
	
	//Set game parameters
	public void SetOptions(Stage topLevelStage, MinesweeperController controller, String title) {
		this.stage = topLevelStage;
		this.controller = controller;
		this.title = title;
		this.highscore = getstringHBox(controller.highscore[1]);
	}
	
	//Load pictures to images array
	public void setPictures() {
		this.images = new Image[13];
		
		for(int i = 0; i<12; i++) {
			String name ="images/" + i + "num.png";
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
		exitButton.setOnAction(e -> Platform.exit());
		
			
		
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
		
		mediumButton.setGraphic(new ImageView(new Image("images/medium.png")));
		mediumButton.setStyle("-fx-background-color: transparent;");
		mediumButton.setOnAction(e -> controller.gotoNewGame(16,16,40));
	   
		
		//Hard button
		Button hardButton = new Button();
		
		hardButton.setGraphic(new ImageView(new Image("images/hard.png")));
		hardButton.setStyle("-fx-background-color: transparent;");
		hardButton.setOnAction(e -> controller.gotoNewGame(30,16,99));
	   
		
		//Custom button
		Button customButton = new Button();
		
		customButton.setGraphic(new ImageView(new Image("images/custom.png")));
		customButton.setStyle("-fx-background-color: transparent;");
		customButton.setOnAction(e -> customizeGame());
		
		//Back button
		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image("images/back.png")));
		backButton.setStyle("-fx-background-color: transparent;");
		backButton.setOnAction(e -> mainMenu());
		
									
		
		VBox layout = new VBox(5);
		layout.getChildren().addAll(easyButton, mediumButton, hardButton,customButton, backButton);
		
		layout.setPadding(new Insets(300,300,300,400));
		layout.setBackground(background);
		
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}
	
	
	
	
	//Sets the stage

	public void gameWindow() {
			
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		this.stage.setTitle(title);
		
		
		HBox menuBar = new HBox(20);
		//bomb counter
		HBox counterLabel = getstringHBox("Bombs");
		this.bombs = new Label();
		this.bombs.setGraphic(getIntHBox(0));
		HBox bombsBox = new HBox();
		bombsBox.getChildren().addAll(counterLabel,bombs);
		
				
		//highscore
		highscore.setPadding(new Insets(50,100,50,100));
		
		//timer
		HBox timebox = new HBox();
		HBox timer = getstringHBox("Time");
		this.time= new Label();
		time.setGraphic(getIntHBox(000));
		timebox.getChildren().addAll(timer,time);
		
		
		
		//top Menu bar		
		menuBar.getChildren().addAll(this.highscore,bombsBox,timebox);
		
				
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
		
		window.sizeToScene();
		
		window.setOnCloseRequest(e -> controller.gotoMainMenu());
		//Force user to interact with window
		window.initModality(Modality.APPLICATION_MODAL);
		
		//
		Label label = new Label();
		label.setGraphic(getstringHBox(text));
		label.setPadding(new Insets(50,50,50,50));
		
		//button (Begin new game)
		Button button = new Button();
		button.setGraphic(getstringHBox("return"));
		
		button.setStyle("-fx-background-color: transparent;");
		button.setPadding(new Insets(50,50,50,50));
		button.setOnAction(e -> {
		controller.gotoMainMenu();
		window.close();
		});
		
		//background
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		//Layout
		VBox layout = new VBox(50);
		layout.getChildren().addAll(label, button);
		layout.setMinWidth(200);
		layout.setAlignment(Pos.CENTER);
		layout.setBackground(background);
		Scene scene= new Scene(layout);
		window.setScene(scene);
		window.show();
		// When window is closed. opens a new game.
		
	}

	public void customizeGame() {
		this.stage.setTitle("Minesweeper");
		
		
		
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
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		layout.setBackground(background);
		
		
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}

	public void highScore() {
		this.stage.setTitle("Minesweeper");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//dificulty list
		HBox dificulty = new HBox(150);
		dificulty.getChildren().add(new ImageView(new Image("images/easy.png")));
		dificulty.getChildren().add(new ImageView(new Image("images/medium.png")));
		dificulty.getChildren().add(new ImageView(new Image("images/hard.png")));
		dificulty.setPadding(new Insets(50,50,50,100));
		
		//easy
		VBox easy = getScoreVBox(0);
		
		//medium
		VBox medium = getScoreVBox(5);
		
		
		//hard
		VBox hard = getScoreVBox(10);
		hard.setPadding(new Insets(0,0,0,120));
		
		
		
		// all scores
		HBox highscores = new HBox(50);
		highscores.getChildren().addAll(easy,medium,hard);
		highscores.setPadding(new Insets(0,0,0,50));
		
		
		//return button
		Button returnButton = new Button();
		returnButton.setGraphic(new ImageView(new Image("images/back.png")));
		returnButton.setStyle("-fx-background-color: transparent;");
		returnButton.setOnAction(e -> mainMenu());
		returnButton.setPadding(new Insets(0,300,300,400));
		
		//all together now!
		VBox layout = new VBox();
		layout.getChildren().addAll(dificulty,highscores, returnButton);
		layout.setPadding(new Insets(150,0,0,0));
		Scene scene = new Scene(layout, 1000, 750);
		layout.setBackground(background);
		stage.setScene(scene);
		
	}
	
	public int[] getGrapicInt(int input) {
		int[] image= new int[3];
		int number= input;
		image[0] = number/100;
		number=number%100;
		image[1] = number/10;
		number=number%10;
		image[2] = number;
		return image;
	}
	
	public Image getNumberAsImage(int number) {
	String name = "images/" + number + ".png";
	return new Image(name);
	}
	
	public HBox getIntHBox(int input) {
		HBox numbers = new HBox();
		int[] numAsImage = getGrapicInt(input);
		numbers.getChildren().addAll(new ImageView(getNumberAsImage(numAsImage[0])),new ImageView(getNumberAsImage(numAsImage[1])),new ImageView(getNumberAsImage(numAsImage[2])));
		return numbers;
	}
	
	
	public HBox getstringHBox(String input) {
		HBox temp = new HBox();
		for(int i=0; i<input.length(); i++ ) {
			temp.getChildren().add(new ImageView(getStringAsImages(input.charAt(i))));
		}
		
		return temp;
	}
	
	public Image getStringAsImages(char input) {
		
		// Filter input. Only a-z and A-Z is allowed as input. Other input returns ' '.
		if ((input >=65 && input <=90) || (input >=97 && input <=122)){
			return new Image( "images/" + input + ".png"); 
		}
		return new Image("images/space.png"); 
	}
	public VBox getScoreVBox(int x) {
		VBox temp = new VBox();
		temp.setPadding(new Insets(0,50,50,50));
		
		for (int i=0+x; i<5+x;i++) {
			//gets number as graphic
			int number = Integer.parseInt(controller.highscore[i]);
			HBox numbers = getIntHBox(number);
			
			
			//gets name
			HBox letters = getstringHBox(controller.highscore[i+15]);
			letters.setPadding(new Insets(2,2,2,2));
			
			//boxed together
			HBox player = new HBox();
			player.getChildren().addAll(numbers,letters);
			temp.getChildren().add(player);
		}
	return temp;
	}
}








	