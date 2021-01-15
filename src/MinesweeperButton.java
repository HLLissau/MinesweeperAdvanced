import java.awt.Point;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;


public class MinesweeperButton extends Button{
	private Point position;
	private ArrayList<MinesweeperButton> neightbours;
	public Boolean flag;

	public MinesweeperButton(int x, int y) {
		this.position = new Point(x,y);
		neightbours = new ArrayList<MinesweeperButton>() ;
		this.flag = false;
	}
	
	public Point getPos() {
		return this.position;
	}
	
	public ArrayList<MinesweeperButton> getneighbours() {
		return this.neightbours;
	}
	
	public void changeFlag() {
		if (flag) {
			this.flag = false ;
		} else {
			this.flag = true;
		}
		
	}
	
	public void setNeighbours(int x, int y, ObservableList<Node> list) {
		for (int k = -1; k <= 1; k++) {
			for (int l =-1; l <= 1; l++) {
				if (k!=0 || l!=0) {
					if (this.position.y+k >= 0 && this.position.y+k < y && this.position.x+l >=0 && this.position.x+l < x) {
						MinesweeperButton temp = (MinesweeperButton) list.get(((position.x+l)+((position.y+k)*x)));
						this.neightbours.add(temp);
						
					}
				}
			}
		}
	}
}
