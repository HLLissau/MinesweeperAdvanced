import java.awt.Point;
import java.util.ArrayList;

//Model class containing game
public class MinesweeperModel{
	public char[][] knownGameState;
	private char[][] gameState;
	private Boolean isGameStarted;
	private  Boolean[][] clickedFields;
	private ArrayList<Point> availableFields;
	private int endCondition;
	private int m;
	private int bombAmount;
	private int n;
	public final int MINGRIDSIZE = 4;
	public final int MAXGRIDSIZE = 100;
	public final int MAXGRIDHEIGHT = 50;
	private int flagsplaced;
	private boolean gameStopped;
	
	
	
	/*
	 *  (made by all)
	 * Creates a standard (n x m) game where bombAmount specifies 
	 * the amount of bombs to be generated later on
	 * The constructor also makes sure that m,n is in the interval [4,100]
	 * and that bombAmount is less than the total number of fields (m*n) and greater than 0
	 * Input: Game board size given as m by n, along with amount of bombs.
	 */
	public MinesweeperModel(int m, int n, int bombAmount) {
		
		if (m > MAXGRIDSIZE) m = MAXGRIDSIZE;
		else if (m < MINGRIDSIZE) m = MINGRIDSIZE;
		if (n > MAXGRIDHEIGHT) n = MAXGRIDHEIGHT;
		else if (n < MINGRIDSIZE) n = MINGRIDSIZE;
		if (bombAmount >= m*n) bombAmount = m*n-1;
		else if (bombAmount < 1) bombAmount = 1;
		
		this.knownGameState = new char[m][n] ;
		this.gameState = new char[m][n];
		this.m=m;
		this.n=n;
		this.clickedFields = new Boolean[m][n];
		this.bombAmount= bombAmount;
		this.flagsplaced=0;
		isGameStarted= false;
		gameStopped = false;
		setClickedFields();
		
	}
	/*
	 * Erik
	 * Set Array of clicked field to false. run at start of a new game.
	 */
	public void setClickedFields() {
		for (int i=0;i<this.n;i++) {
			for (int j=0;j<this.m;j++) {
				this.clickedFields[j][i]=false;
			}
					
		}
	}
	/*
	 * Erik
	 * transfer given [x,y] set from gamestate to knowngamestate.
	 * Input: Point position to update
	 * Output: number of neighbors (9 = bombs).
	 */
	public int getPos( Point nextPos) {
		if (!isGameStarted) {
			isGameStarted = true;
			
			randomBombGenerator(bombAmount, nextPos );
			nearBombs();
		}
		this.knownGameState[nextPos.x][nextPos.y]=(char) this.gameState[nextPos.x][nextPos.y];
		int cell = this.knownGameState[nextPos.x][nextPos.y];
		testConditions(nextPos);
		return cell;
	}
	
	/*
	 * Harald
	 * Sets all elements in gameState[][] to 0, bombs are inserted hereafter randomly.
	 * Creates list of unoccupied fields for placement of bombs.
	 * For each iteration of the loop, puts a bomb on an unoccupied field, and removes field from list.
	 * Bombs are represented by the number 9.
	 * Generates gameState after user makes first click to ensure game is not lost in first click.
	 * Input: Number of bombs, first clicked element by user.
	 */
	public void randomBombGenerator(int bombAmount, Point firstClicked) {
		this.bombAmount=bombAmount;
		int fieldAmount=(this.m*this.n);
		this.availableFields = new ArrayList<Point>();
		for (int i=0; i<this.n; i++) {
			for ( int j=0; j<this.m;j++) {
				this.gameState[j][i]=0;
				availableFields.add(new Point(j,i));
			}
		}
		availableFields.remove(firstClicked.x+this.m*firstClicked.y);
		for (int i=0; i<bombAmount;i++) {
			int nextBomb= (int) (Math.random()*(fieldAmount-i-1));
			Point nextBombPlacement = availableFields.remove(nextBomb);
			this.gameState[nextBombPlacement.x][nextBombPlacement.y]= 9;
		}
	}
	
	/*
	 * Erik
	 * Iterates through gameState[][] looking for bombs.
	 * For each bomb the neighbors value is increased by one, except if it is already a bomb.
	 */
	public void nearBombs() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++) {
				if (gameState[j][i] == 9) {
					for (int k = -1; k <= 1; k++) {
						for (int l =-1; l <= 1; l++) { 
							//The following if statement ensures that the program stays within the array
							if (i+k >= 0 && i+k < this.n && j+l >=0 && j+l < this.m) {
								gameState[j+l][i+k] += 1;
								if (gameState[j+l][i+k] > 9) {
									gameState[j+l][i+k] = 9;
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/*
	 * Anton
	 * Looks if gameover or victory conditions are met. 
	 * input: Point of last pressed button
	 * 
	 */
	private void testConditions(Point nextTile) {
		if (defeatCondition(nextTile)) {
			this.endCondition =9;
		} else if (victoryCondition()) {
			this.endCondition =8;
			
		}
	}
	
	/*
	 * Anton
	 * Victory condition are met, if all buttons, except bombs are pressed.
	 *  
	 * output: Boolean value.
	 */
	private boolean victoryCondition() {
		return ((this.m*this.n)-this.bombAmount == getAmountClickedFields());
	}
	/*
	 * Anton
	 * Defeat condition are met, 
	 * Input: Button pressed position
	 * Output: Boolean value.
	 */
	private boolean defeatCondition(Point nextTile) {
		return gameState[nextTile.x][nextTile.y] == 9;
	}
	
	/*
	 * Harald
	 * Return how many buttons have been pressed
	 * Output : int with amount
	 */
	public int getAmountClickedFields() {
		int clicked =0;
		for (int i=0; i<this.n;i++) {
			for (int j=0; j<this.m;j++) {
				if (this.clickedFields[j][i]==true) {
					clicked++;
					
				}
			}
		}
		return clicked;
	}
	
	/*
	 * (Made by group)
	 * The remaining functions are used to get/set/check game parameters
	 */
	
	public int getm() {
		return this.m;
	}
	
	public int getn() {
		return this.n;
	}
	
	public int getBombAmount() {
		return this.bombAmount;
	}
	
	public void placeflag() {
		this.flagsplaced++;
	}
	public void removeflag() {
		this.flagsplaced--;
	}
	public int getFlagPlaced() {
		return this.flagsplaced;
	}
	
	public int getEndCondition() {
		return this.endCondition;
	}
	public boolean isGAmeStarted() {
		return this.isGameStarted;
	}
	public boolean isGameStopped() {
		return this.gameStopped;
	}
	public void stopGame() {
		this.gameStopped=true;
	}
	public void clickbutton(Point pos) {
		this.clickedFields[pos.x][pos.y]=true;
	}
	public Boolean buttonclicked(Point pos) {
		return this.clickedFields[pos.x][pos.y];
	}
}
