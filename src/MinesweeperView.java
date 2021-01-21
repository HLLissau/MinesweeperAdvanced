import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;



import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MinesweeperView {
	MinesweeperController controller;
	Stage stage;
	String title;
	Image[] images;
	
	
	Label bombs;
	Label time;
	int nAmount;
	int mAmount;
	int bombAmount;

	
	//MinesweeperView initiates constructor to make view
	public MinesweeperView() {
		setPictures();
	}
	
	//Set game parameters
	public void SetOptions(Stage topLevelStage, MinesweeperController controller, String title) {
		this.stage = topLevelStage;
		this.controller = controller;
		this.title = title;
		
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
		easyButton.setOnAction(e -> controller.gotoNewGame(10,10,10,0));
		
	   
		//Medium button
		Button mediumButton = new Button();
		
		mediumButton.setGraphic(new ImageView(new Image("images/medium.png")));
		mediumButton.setStyle("-fx-background-color: transparent;");
		mediumButton.setOnAction(e -> controller.gotoNewGame(16,16,40,1));
	   
		
		//Hard button
		Button hardButton = new Button();
		
		hardButton.setGraphic(new ImageView(new Image("images/hard.png")));
		hardButton.setStyle("-fx-background-color: transparent;");
		hardButton.setOnAction(e -> controller.gotoNewGame(30,16,99,2));
	   
		
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
		
		
		
		//bomb counter
		HBox counterLabel = getstringHBox("Bombs");
		this.bombs = new Label();
		this.bombs.setGraphic(getIntHBox(0));
		this.bombs.setScaleX(1.5);
		this.bombs.setScaleY(1.5);
		this.bombs.setPadding(new Insets(20,0,0,70));
		VBox bombsBox = new VBox();
		bombsBox.getChildren().addAll(counterLabel,bombs);
		
			
		//highscore
		VBox highscore = new VBox();
		Label highscoreLabel = new Label();
		highscoreLabel.setGraphic(getstringHBox("Highscore"));
		HBox player = new HBox();
		Label score = new Label();
		int record = 0;
		Label name = new Label();
		record = Integer.parseInt(controller.highscore.get(5*controller.getDificulty()));
		name.setGraphic(getstringHBox(controller.highscore.get((5*controller.getDificulty())+15)));
		score.setGraphic(getIntHBox(record));
		score.setScaleX(1.5);
		score.setScaleY(1.5);
		score.setPadding(new Insets(10,0,0,0));
		
		name.setGraphic(getstringHBox(controller.highscore.get((5*controller.getDificulty())+15)));
		name.setPadding(new Insets(0,20,20,20));
		
		player.getChildren().addAll(score,name);
		player.setPadding(new Insets(20,0,0,100));
		
		highscore.getChildren().addAll(highscoreLabel,player);
	
		
		
		//timer
		VBox timebox = new VBox();
		HBox timer = getstringHBox("Time");
		this.time= new Label();
		this.time.setGraphic(getIntHBox(000));
		this.time.setScaleX(1.5);
		this.time.setScaleY(1.5);
		this.time.setPadding(new Insets(20,0,0,70));
		timebox.getChildren().addAll(timer,time);
		
		//top Menu bar		
		HBox menuBar = new HBox(80);
		menuBar.getChildren().addAll(highscore,bombsBox,timebox);
		menuBar.setPadding(new Insets(10,0,0,0));
				
		//full game window
		BorderPane layout = new BorderPane();
		
		GridPane grid = controller.getGrid();
		grid.setPadding(new Insets(300-(12*controller.model.getn()),0,0,500-(12*controller.model.getm())));
		
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
		
		window.setOnCloseRequest(e -> {
			//check for highscore. catch exeption error.
			if (controller.getDificulty()<3) {
				try {
					controller.checkHighScore();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			controller.gotoMainMenu();
		});
		
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
			window.close();
			//check if game is new,medium,hard and if game was won
			if (controller.getDificulty()<3 && controller.model.getEndCondition()==8) {
				try {
					controller.checkHighScore();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}		
			controller.gotoMainMenu();
			
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
		nAmount=10;
		mAmount=10;
		bombAmount=10;
		
		//input fields
		Label n = new Label();
		n.setGraphic(getIntHBox(nAmount));
		n.setScaleX(1.5);
		n.setScaleY(1.5);
		n.setPadding(new Insets(17,0,0,0));
		Label m = new Label();
		m.setScaleX(1.5);
		m.setScaleY(1.5);
		m.setPadding(new Insets(17,0,0,0));
		m.setGraphic(getIntHBox(mAmount));
		Label bombs = new Label();
		bombs.setScaleX(1.5);
		bombs.setScaleY(1.5);
		bombs.setPadding(new Insets(17,0,0,0));
		bombs.setGraphic(getIntHBox(nAmount));
		
		this.stage.setTitle("Minesweeper (Custom Game)");
		
		
		//plus button
		Button plusn = new Button();
		plusn.setGraphic(new ImageView("images/plus.png"));
		plusn.setPadding(new Insets(20,0,0,10));
		plusn.setBackground(null);
		plusn.setOnAction(e -> {
			nAmount++;
			controller.checkAmount();
			n.setGraphic(getIntHBox(nAmount));
			bombs.setGraphic(getIntHBox(bombAmount));
			
		});
		Button plusm = new Button();
		plusm.setGraphic(new ImageView("images/plus.png"));
		plusm.setPadding(new Insets(20,0,0,10));
		plusm.setBackground(null);
		plusm.setOnAction(e -> {
			mAmount++;
			controller.checkAmount();
			m.setGraphic(getIntHBox(mAmount));
			bombs.setGraphic(getIntHBox(bombAmount));
			
		});
		Button plusb = new Button();
		plusb.setGraphic(new ImageView("images/plus.png"));
		plusb.setPadding(new Insets(20,0,0,10));
		plusb.setBackground(null);
		plusb.setOnAction(e -> {
			bombAmount++;
			controller.checkAmount();
			bombs.setGraphic(getIntHBox(bombAmount));
		
			
		});
		//minus button
		Button minusn = new Button();
		minusn.setBackground(null);
		minusn.setPadding(new Insets(20,10,0,0));
		minusn.setGraphic(new ImageView("images/minus.png"));
		minusn.setOnAction(e -> {
			nAmount--;
			controller.checkAmount();
			n.setGraphic(getIntHBox(nAmount));
			bombs.setGraphic(getIntHBox(bombAmount));
			
		});
		Button minusm = new Button();
		minusm.setBackground(null);
		minusm.setGraphic(new ImageView("images/minus.png"));
		minusm.setPadding(new Insets(20,10,0,0));
		minusm.setOnAction(e -> {
			mAmount--;
			controller.checkAmount();
			m.setGraphic(getIntHBox(mAmount));
			bombs.setGraphic(getIntHBox(bombAmount));
			
		});
		Button minusb = new Button();
		minusb.setPadding(new Insets(20,10,0,0));
		minusb.setBackground(null);
		minusb.setGraphic(new ImageView("images/minus.png"));
		minusb.setOnAction(e -> {
			bombAmount--;
			controller.checkAmount();
			bombs.setGraphic(getIntHBox(bombAmount));
			
		});
		
		//HBox input fields
		HBox widthfield = new HBox(10);
		widthfield.getChildren().addAll(minusn,n,plusn);
		widthfield.setPadding(new Insets(0,0,0,100));
		HBox heightfield = new HBox(10);
		heightfield.getChildren().addAll(minusm,m,plusm);
		heightfield.setPadding(new Insets(0,0,0,58));
		HBox bombsHBoxfield = new HBox(10);
		bombsHBoxfield.getChildren().addAll(minusb,bombs,plusb);
		bombsHBoxfield.setPadding(new Insets(0,0,0,100));
		
		
		//Information labels
		Label nLabel = new Label();
		nLabel.setGraphic(getstringHBox("width"));
		Label mLabel = new Label();
		mLabel.setGraphic(getstringHBox("height"));
		Label bLabel = new Label();
		bLabel.setGraphic(getstringHBox("Bombs"));
		
		
		//Hbox for labels and fields
		HBox width = new HBox(10);
		width.getChildren().addAll(nLabel,widthfield);
		HBox height = new HBox(10);
		height.getChildren().addAll(mLabel,heightfield);
		HBox bombsHBox = new HBox(10);
		bombsHBox.getChildren().addAll(bLabel,bombsHBoxfield);
		bombsHBox.setPadding(new Insets(0,0,30,00));
		
		//New game button
		Button newGameButton = new Button();
		newGameButton.setText("Start game");
		newGameButton.setGraphic(new ImageView(new Image("images/startgame.png")));
		newGameButton.setStyle("-fx-background-color: transparent;");
		newGameButton.setOnAction(e ->  controller.gotoNewGame(nAmount,mAmount,bombAmount,3));
		
		Button leaveButton = new Button();
		leaveButton.setGraphic(new ImageView(new Image("images/back.png")));
		leaveButton.setStyle("-fx-background-color: transparent;");
		leaveButton.setOnAction(e -> mainSetup());
									
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(width,height,bombsHBox,newGameButton, leaveButton);
		layout.setPadding(new Insets(300,400,300,300));
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
		dificulty.setPadding(new Insets(100,50,30,100));
		
		//easy
		VBox easy = getScoreVBox(0);
		
		//medium
		VBox medium = getScoreVBox(5);
		
		
		//hard
		VBox hard = getScoreVBox(10);
		//hard.setPadding(new Insets(0,0,0,120));
		
		
		
		// all scores
		HBox highscores = new HBox(50);
		highscores.getChildren().addAll(easy,medium,hard);
		highscores.setPadding(new Insets(0,0,0,50));
		highscores.setScaleY(0.8);
		
		//return button
		Button returnButton = new Button();
		returnButton.setGraphic(new ImageView(new Image("images/back.png")));
		returnButton.setStyle("-fx-background-color: transparent;");
		returnButton.setOnAction(e -> mainMenu());
		returnButton.setPadding(new Insets(0,110,200,100));
		
		//return button
		Button resetButton = new Button();
		resetButton.setGraphic(getstringHBox("reset"));
		resetButton.setStyle("-fx-background-color: transparent;");
		resetButton.setOnAction(e ->{
		controller.resetHighScore();	
		mainMenu();
	    });
		resetButton.setPadding(new Insets(0,300,300,400));
				
		HBox buttons = new HBox();
		buttons.getChildren().addAll(returnButton,resetButton);
		
		
		//all together now!
		VBox layout = new VBox();
		layout.getChildren().addAll(dificulty,highscores,buttons);
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
		VBox temp = new VBox(10);
		temp.setPadding(new Insets(0,5,25,50));
		
		for (int i=0+x; i<5+x;i++) {
			//gets number as graphic
			int number = Integer.parseInt(controller.highscore.get(i));
			HBox numbers = getIntHBox(number);
			numbers.setPadding(new Insets(15,20,1,1));
			numbers.setScaleX(2);
			numbers.setScaleY(2);
			
			
			//gets name
			HBox letters = getstringHBox(controller.highscore.get(i+20));
			letters.setPadding(new Insets(2,2,2,2));
			
			//boxed together
			HBox player = new HBox();
			player.getChildren().addAll(numbers,letters);
			temp.getChildren().add(player);
		}
	return temp;
	}
	
	/*  Window pressented when Highscore is beaten.
	 *  input: Time used to win
	 *  output: none.
	 */
	
	
	public void newHighScoreName(int timeUsed) {
		//controller.clearButtonAction();
		
		String text= "TMP";	
		controller.newName=text;
		Stage window = new Stage();
		window.setTitle("New HighScore");
		window.sizeToScene();
		//Force user to interact with window
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e -> {
			
			//catch error(File not found)
			try {
				controller.newHighscore(timeUsed);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}		
		});
	
		// highscore message
		Label highsco = new Label();
		highsco.setGraphic(getstringHBox("New Highscore"));
		highsco.setPadding(new Insets(5,50,50,50));
		// insert name
		Label message = new Label();
		message.setGraphic(getstringHBox("Type name"));
		message.setPadding(new Insets(5,50,50,50));
		
		
		
		// user input
		Label label = new Label();
		label.setGraphic(getstringHBox(text));
		label.setPadding(new Insets(50,50,50,50));
		
		//button (save)
		Button button = new Button();
		button.setGraphic(getstringHBox("save"));
		button.setStyle("-fx-background-color: transparent;");
		button.setPadding(new Insets(5,50,50,50));
		button.setOnAction(e -> {
			
			//catch error(File not found)
			try {
				controller.newHighscore(timeUsed);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			window.close();	
		});
		
		//background
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		//Layout
		VBox layout = new VBox(10);
		layout.getChildren().addAll(highsco,message,label, button);
		layout.setMinWidth(200);
		layout.setAlignment(Pos.CENTER);
		layout.setBackground(background);
		Scene scene= new Scene(layout);
		window.setScene(scene);
		window.show();
		// When window is closed. opens a new game.
		
		//following code handles user input and updates name
		layout.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			
			String inputstring = key.getCode().getChar();
			char input = inputstring.charAt(0);
			if ((input >=65 && input <=90) || (input >=97 && input <=122 || input==8 )) {
			
			
				if (controller.newName=="TMP") {
					controller.newName="";
				}
			
				if(key.getCode()== KeyCode.BACK_SPACE) {
					String nameEntered = controller.newName;
					int number =(nameEntered.length()-1);
					if (number<=0) {
						number=0;
					}
					controller.newName= nameEntered.substring(0, number);
					label.setGraphic(getstringHBox(controller.newName));
				}else {
					String nameEntered = "";
					controller.newName +=key.getCode();
					label.setGraphic(getstringHBox(controller.newName));
					nameEntered= controller.newName;
					if (nameEntered.length()>=4) {
						controller.newName= nameEntered.substring(0, 3);
						label.setGraphic(getstringHBox(controller.newName));
					}
				
				
				}
			}	
		});
		
	}
	
	public void updateTime(int update) {
		this.time.setGraphic(getIntHBox(update));
	}
	public void updatebombs(int update) {
		this.bombs.setGraphic(getIntHBox(update));
	}
	
}








	