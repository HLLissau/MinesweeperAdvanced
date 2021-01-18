import java.awt.Point;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;

public class MinesweeperButtonTriangle extends Button{
	private Point position;
	private ArrayList<MinesweeperButtonTriangle> neightbours;
	public Boolean flag;
	private Polygon triangle;
	
	public MinesweeperButtonTriangle() {
		
		neightbours = new ArrayList<MinesweeperButtonTriangle>() ;
		this.flag = false;
		this.setMinWidth(23.0);
		this.setMaxWidth(23.0);
		this.setMinHeight(25.0);
		this.setMaxHeight(25.0);
			
	}
	
	
	public MinesweeperButtonTriangle(int x, int y) {
		this.position = new Point(x,y);
		neightbours = new ArrayList<MinesweeperButtonTriangle>() ;
		this.flag = false;
		this.setMinWidth(23.0);
		this.setMaxWidth(23.0);
		this.setMinHeight(25.0);
		this.setMaxHeight(25.0);
		this.setShape(triangle);
		
	}
	
	public Point getPos() {
		return this.position;
	}
	
	public ArrayList<MinesweeperButtonTriangle> getneighbours() {
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
						MinesweeperButtonTriangle temp = (MinesweeperButtonTriangle) list.get(((position.x+l)+((position.y+k)*x)));
						this.neightbours.add(temp);
						
					}
				}
			}
		}
	}

	public void setTriangle() {
		this.triangle = new Polygon();
		triangle.getPoints().addAll(new Double[]{0.0,0.0, 20.0,0.0, 10.0,20.0});
		this.setShape(triangle);
		
	}
	
	
	public void setInverseTriangle() {
		this.triangle = new Polygon();
		this.triangle.getPoints().addAll(new Double[] {20.0,20.0, 0.0,20.0, 10.0,0.0});
		this.setShape(triangle);
	}

}