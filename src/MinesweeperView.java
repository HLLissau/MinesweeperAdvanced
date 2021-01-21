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


//File containing all windows used in game.


public class MinesweeperView {
	
	private MinesweeperController controller;
	private Stage stage;
	private String title;
	
	public Image[] images; //imported graphic
	private Label bombs;
	private Label time;
	public int nAmount;
	public int mAmount;
	public int bombAmount;

	
	/*
	 * Construct new view.
	 	 */
	public MinesweeperView() {
	}
	
	/*
	 * Set view options 
	 * Import pictures to internal Image[]
	 */
	public void SetOptions(Stage topLevelStage, MinesweeperController controller, String title) {
		setPictures();
		this.stage = topLevelStage;
		this.controller = controller;
		this.title = title;
		
	}
	
	/*
	 * Import pictures to internal Image Array.
	 */
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
	
	
	/*
	 * Main menu window.
	 */
	public Stage mainMenu() {
		//Title and background
		this.stage.setTitle("Minesweeper");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		////Buttons
		//New game button
		Button newGameButton = new Button();
		newGameButton.setStyle("-fx-background-color: transparent;");
		newGameButton.setGraphic(new ImageView(new Image("images/newgame.png")));
		newGameButton.setOnAction(e -> mainSetup());
		//Highscorebutton
		Button highScoreButton = new Button();
		highScoreButton.setStyle("-fx-background-color: transparent;");
		highScoreButton.setGraphic(new ImageView(new Image("images/highscore.png")));
		highScoreButton.setOnAction(e ->highScore());
		//Exit button
		Button exitButton = new Button();
		exitButton.setGraphic(new ImageView(new Image("images/exit.png")));
		exitButton.setStyle("-fx-background-color: transparent;");
		exitButton.setOnAction(e -> Platform.exit());
		
		//Button layoutbox		
		VBox layout = new VBox();
		layout.getChildren().addAll(newGameButton, highScoreButton, exitButton);
		layout.setPadding(new Insets(300,400,300,300));
		layout.setBackground(background);
		
		//scene set to size and with buttons.
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		return stage;
	}
	
	
	/*
	 * Game setup window.
	 */
	
	public void mainSetup() {
		//Title and background
		this.stage.setTitle("Minesweeper (Setup Game)");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		////Buttons
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
		
		//Buttons Layoutbox
		VBox layout = new VBox(5);
		layout.getChildren().addAll(easyButton, mediumButton, hardButton,customButton, backButton);
		layout.setPadding(new Insets(300,300,300,400));
		layout.setBackground(background);
		
		//Scene set to size and with Buttons
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}
	
	
	
	
	/*
	 * Game window.
	 */

	public void gameWindow() {
		
		//Set background and Title
		this.stage.setTitle(title);	
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//////Top menu bar.
		
		////highscore
		// Label for highscore text
		Label highscoreLabel = new Label();
		highscoreLabel.setGraphic(getstringHBox("Highscore"));
		//Label with highscore name
		Label name = new Label();	
		name.setGraphic(getstringHBox(controller.highscore.get((5*controller.getDificulty())+15)));
		name.setGraphic(getstringHBox(controller.highscore.get((5*controller.getDificulty())+15)));
		name.setPadding(new Insets(0,20,20,20));
		//Label with time of highscore
		Label score = new Label();
		score.setGraphic(getIntHBox(Integer.parseInt(controller.highscore.get(5*controller.getDificulty()))));
		score.setScaleX(1.5);
		score.setScaleY(1.5);
		score.setPadding(new Insets(10,0,0,0));
		//Hbox to set name and score together.
		HBox player = new HBox();
		player.getChildren().addAll(score,name);
		player.setPadding(new Insets(20,0,0,100));
		// completed highscore box
		VBox highscore = new VBox();
		highscore.getChildren().addAll(highscoreLabel,player);
		
		////bomb counter
		//Bomb label
		HBox counterLabel = getstringHBox("Bombs");
		//Bomb counter
		this.bombs = new Label();
		this.bombs.setGraphic(getIntHBox(0));
		this.bombs.setScaleX(1.5);
		this.bombs.setScaleY(1.5);
		this.bombs.setPadding(new Insets(20,0,0,70));
		//Completed graphic
		VBox bombsBox = new VBox();
		bombsBox.getChildren().addAll(counterLabel,bombs);
				
		////Timer
		//Timer label
		HBox timer = getstringHBox("Time");
		//Timer graphics
		this.time= new Label();
		this.time.setGraphic(getIntHBox(000));
		this.time.setScaleX(1.5);
		this.time.setScaleY(1.5);
		this.time.setPadding(new Insets(20,0,0,70));
		//Completed graphic
		VBox timebox = new VBox();
		timebox.getChildren().addAll(timer,time);
		
		////top Menu bar
		//Menubox with highscore,bombs and timer
		HBox menuBar = new HBox(80);
		menuBar.getChildren().addAll(highscore,bombsBox,timebox);
		menuBar.setPadding(new Insets(10,0,0,0));
				
		
		//Grid with MinesweeperButtons
		GridPane grid = controller.getGrid();
		grid.setPadding(new Insets(300-(12*controller.model.getn()),0,0,500-(12*controller.model.getm())));
		
		//Game window
		BorderPane layout = new BorderPane();
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
	
	/*
	 * Open a new window with alert box
	 * This window is forced to stay on top of Game window, and game window is halted until this window is closed.
	 * Input: Title of Alertbox, Text to output from Alertbox 
	 */
	public void alertBox(String title, String text) {
		//open a new window and set title and background
		Stage window = new Stage();
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		window.setTitle(title);
		window.sizeToScene();
		//Force user to interact with window
		window.initModality(Modality.APPLICATION_MODAL);
		//Close action. Check if highscore is beaten and close window, and goes back to main menu.
		window.setOnCloseRequest(e -> {
			if (controller.getDificulty()<3 && controller.model.getEndCondition()==8) {
				try {
					controller.checkHighScore();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			controller.gotoMainMenu();
		});  
		
		
		//Label with text for user
		Label label = new Label();
		label.setGraphic(getstringHBox(text));
		label.setPadding(new Insets(50,50,50,50));
		//Button with return text.
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
		//Layout
		VBox layout = new VBox(50);
		layout.getChildren().addAll(label, button);
		layout.setMinWidth(200);
		layout.setAlignment(Pos.CENTER);
		layout.setBackground(background);
		Scene scene= new Scene(layout);
		
		window.setScene(scene);
		window.show();
		
		
	}

	/*
	 * Custom game menu
	 * User can define width and height and bomb amount.
	 * 
	 */
	public void customizeGame() {
		
		//Title and background
		this.stage.setTitle("Minesweeper (Custom Game)");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		//Set local variables for game
		nAmount=10;
		mAmount=10;
		bombAmount=10;
		
		////Number labels 
		//Label height
		Label n = new Label();
		n.setGraphic(getIntHBox(nAmount));
		n.setScaleX(1.5);
		n.setScaleY(1.5);
		n.setPadding(new Insets(17,0,0,0));
		//label width
		Label m = new Label();
		m.setScaleX(1.5);
		m.setScaleY(1.5);
		m.setPadding(new Insets(17,0,0,0));
		m.setGraphic(getIntHBox(mAmount));
		//label bombs
		Label bombs = new Label();
		bombs.setScaleX(1.5);
		bombs.setScaleY(1.5);
		bombs.setPadding(new Insets(17,0,0,0));
		bombs.setGraphic(getIntHBox(nAmount));
		
		/*
		 * Buttons for adding or subtracting amounts
		 * setOnAction checks input is valid and update labels
		 */
		//plus button n
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
		////plus button m
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
		//plus button bombs
		Button plusb = new Button();
		plusb.setGraphic(new ImageView("images/plus.png"));
		plusb.setPadding(new Insets(20,0,0,10));
		plusb.setBackground(null);
		plusb.setOnAction(e -> {
			bombAmount++;
			controller.checkAmount();
			bombs.setGraphic(getIntHBox(bombAmount));
		});
		
		//minus button n
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
		//minus button m
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
		//minus button bombs
		Button minusb = new Button();
		minusb.setPadding(new Insets(20,10,0,0));
		minusb.setBackground(null);
		minusb.setGraphic(new ImageView("images/minus.png"));
		minusb.setOnAction(e -> {
			bombAmount--;
			controller.checkAmount();
			bombs.setGraphic(getIntHBox(bombAmount));
		});
		
		//Input HBox for Height
		HBox widthfield = new HBox(10);
		widthfield.getChildren().addAll(minusn,n,plusn);
		widthfield.setPadding(new Insets(0,0,0,100));
		//Input HBox for width
		HBox heightfield = new HBox(10);
		heightfield.getChildren().addAll(minusm,m,plusm);
		heightfield.setPadding(new Insets(0,0,0,58));
		//Input HBox for Bombs
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
		
		//// Labels and buttons combined
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
		//back button
		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image("images/back.png")));
		backButton.setStyle("-fx-background-color: transparent;");
		backButton.setOnAction(e -> mainSetup());
									
		//Final layout combined
		VBox layout = new VBox(20);
		layout.getChildren().addAll(width,height,bombsHBox,newGameButton, backButton);
		layout.setPadding(new Insets(300,400,300,300));
		layout.setBackground(background);
		Scene scene = new Scene(layout, 1000, 750);
		stage.setScene(scene);
		
	}
	/*
	 * Highscore Screen.
	 * Show current highest scores and button to reset.
	 */
	
	public void highScore() {
		//set title and background
		this.stage.setTitle("Minesweeper (Highscore)");
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/background.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//Box with difficulty names 
		HBox dificulty = new HBox(150);
		dificulty.getChildren().add(new ImageView(new Image("images/easy.png")));
		dificulty.getChildren().add(new ImageView(new Image("images/medium.png")));
		dificulty.getChildren().add(new ImageView(new Image("images/hard.png")));
		dificulty.setPadding(new Insets(100,50,30,100));
		
		//easy game highscores
		VBox easy = getScoreVBox(0);
		//medium game highscores
		VBox medium = getScoreVBox(5);
		//hard game highscores
		VBox hard = getScoreVBox(10);
				
		// all scores combined into 1 HBox
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
		
		//reset button
		Button resetButton = new Button();
		resetButton.setGraphic(getstringHBox("reset"));
		resetButton.setStyle("-fx-background-color: transparent;");
		resetButton.setOnAction(e ->{
			controller.resetHighScore();	
			mainMenu();
	    });
		resetButton.setPadding(new Insets(0,300,300,400));
		
		//Buttons HBox
		HBox buttons = new HBox();
		buttons.getChildren().addAll(returnButton,resetButton);
		
		
		//Final layout 
		VBox layout = new VBox();
		layout.getChildren().addAll(dificulty,highscores,buttons);
		layout.setPadding(new Insets(150,0,0,0));
		Scene scene = new Scene(layout, 1000, 750);
		layout.setBackground(background);
		stage.setScene(scene);
		
	}
	
	/*
	 * takes a number between 0-999 and returns it an Array
	 * Input: integer
	 * Output: int[]
	 */
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
	
	/*
	 * fetches image representing the number from file.
	 * Input: Number
	 * Output: Image of the number
	 * *note: number have to be between 0-9
	 */
	public Image getNumberAsImage(int number) {
		String name = "images/" + number + ".png";
		return new Image(name);
	}
	
	/*
	 * Function for Graphical representation of numbers 
	 * Input: integer between 0-999
	 * Output: HBox with three images, showing same number
	 */
	public HBox getIntHBox(int input) {
		HBox numbers = new HBox();
		int[] numAsImage = getGrapicInt(input);
		numbers.getChildren().addAll(new ImageView(getNumberAsImage(numAsImage[0])),new ImageView(getNumberAsImage(numAsImage[1])),new ImageView(getNumberAsImage(numAsImage[2])));
		return numbers;
	}
	
	/*
	 * Function for Graphical representation of letters 
	 * Input: String
	 * Output: HBox with images representing the string input.
	 */
	public HBox getstringHBox(String input) {
		HBox temp = new HBox();
		for(int i=0; i<input.length(); i++ ) {
			temp.getChildren().add(new ImageView(getcharAsImages(input.charAt(i))));
		}
		return temp;
	}
	
	/*
	 * Function for Graphical representation of letter
	 * Input: char
	 * Output: Image of the char, or blank image.
	 */
	public Image getcharAsImages(char input) {
		
		// Filter input. Only a-z and A-Z is allowed as input. Other input returns ' '.
		if ((input >=65 && input <=90) || (input >=97 && input <=122)){
			return new Image( "images/" + input + ".png"); 
		} else {
			return new Image("images/space.png"); 
		}
	}
	
	/* Makes the scorebox for Highscore screen.
	 * Input: int. Used to fetch the correct input from highscore list
	 * Output: VBox with names and numbers
	 */
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
		//Set window title and background
		Stage window = new Stage();
		window.setTitle("New HighScore");
		window.sizeToScene();
		BackgroundImage backgroundfill = new BackgroundImage(new Image("images/backgroundNoTitle.png"), null, null, null, null);
		Background background = new Background(backgroundfill);
		
		
		//Force user to interact with window
		window.initModality(Modality.APPLICATION_MODAL);
		//Close request. saves highscore.
		window.setOnCloseRequest(e -> {	
			//catch error(File not found)
			try {
				controller.newHighscore(timeUsed);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}		
		});
		
		//String to show while waiting for user input.
		String text= "TMP";	
		controller.newName=text;
	
		//Label highscore message
		Label highsco = new Label();
		highsco.setGraphic(getstringHBox("New Highscore"));
		highsco.setPadding(new Insets(5,50,50,50));
		
		//Label insert name
		Label message = new Label();
		message.setGraphic(getstringHBox("Type name"));
		message.setPadding(new Insets(5,50,50,50));
		//Label user input
		Label label = new Label();
		label.setGraphic(getstringHBox(text));
		label.setPadding(new Insets(50,50,50,50));
		
		//button save
		Button button = new Button();
		button.setGraphic(getstringHBox("save"));
		button.setStyle("-fx-background-color: transparent;");
		button.setPadding(new Insets(5,50,50,50));
		//saves highscore and close window.
		button.setOnAction(e -> {
			//catch error(File not found)
			try {
				controller.newHighscore(timeUsed);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			window.close();	
		});
		
		
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
			//If buttons is A-Z, a-z or 'backspace'
			if ((input >=65 && input <=90) || (input >=97 && input <=122 || input==8 )) {
			
				//if temporary name is entered, if true, removes it.
				if (controller.newName=="TMP") {
					controller.newName="";
				}
				//removes last entered char if 'backspace' is pressed
				if(key.getCode()== KeyCode.BACK_SPACE) {
					String nameEntered = controller.newName;
					int number =(nameEntered.length()-1);
					//cannot go below 0 char entered
					if (number<=0) {
						number=0;
					}
					controller.newName= nameEntered.substring(0, number);
					label.setGraphic(getstringHBox(controller.newName));
				}else {
					//Input next char
					String nameEntered = "";
					controller.newName +=key.getCode();
					label.setGraphic(getstringHBox(controller.newName));
					nameEntered= controller.newName;
					//cannot exceed 3 chars
					if (nameEntered.length()>=4) {
						controller.newName= nameEntered.substring(0, 3);
						label.setGraphic(getstringHBox(controller.newName));
					}
				
				
				}
			}	
		});
		
	}
	/*
	 * update timer
	 */
	public void updateTime(int update) {
		this.time.setGraphic(getIntHBox(update));
	}
	/*
	 * update bombs
	 */
	public void updatebombs(int update) {
		this.bombs.setGraphic(getIntHBox(update));
	}
	
}








	