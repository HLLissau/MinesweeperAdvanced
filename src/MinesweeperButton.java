import java.awt.Point;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class MinesweeperButton extends Button{
	private Point position;
	private ArrayList<MinesweeperButton> neightbours;
	public Boolean flag;
	
	/*
	 * contructor. set button size, Point position and create Arraylist<>
	 */
	public MinesweeperButton(int x, int y) {
		this.position = new Point(x,y);
		neightbours = new ArrayList<MinesweeperButton>() ;
		this.flag = false;
		this.setMinWidth(23.0);
		this.setMaxWidth(23.0);
		this.setMinHeight(25.0);
		this.setMaxHeight(25.0);
	}
	
	/*
	 * Output: Point position
	 */
	public Point getPos() {
		return this.position;
	}
	
	/*
	 * Output ArrayList with neighbours
	 */
	public ArrayList<MinesweeperButton> getneighbours() {
		return this.neightbours;
	}
    
	/*
	 * Changes flag boolean
	 */
	public void changeFlag() {
		if (flag) {
			this.flag = false ;
		} else {
			this.flag = true;
			
		}
		
	}
	
	/*
	 * Sets ArrayList<MinesweeperButton> with neighbouring buttons. Used for autoclear.
	 * Input:  m,n are game.model size, Observable list of all MinesweeperButtons 
	 */
	public void setNeighbours(int m, int n, ObservableList<Node> list) {
		for (int k = -1; k <= 1; k++) {
			for (int l =-1; l <= 1; l++) {
				if (k!=0 || l!=0) {
					if (this.position.y+k >= 0 && this.position.y+k < n && this.position.x+l >=0 && this.position.x+l < m) {
						MinesweeperButton temp = (MinesweeperButton) list.get(((position.x+l)+((position.y+k)*m)));
						this.neightbours.add(temp);
						
					}
				}
			}
		}
	}
}
