import java.awt.Point;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;

public class MinesweeperButton extends Button{
	private Point position;
	private ArrayList<MinesweeperButton> neightbours;
	public Boolean flag;
	private Polygon triangle;
	
	
	public MinesweeperButton() {
		neightbours = new ArrayList<MinesweeperButton>() ;
		this.flag = false;
		this.setMinWidth(23.0);
		this.setMaxWidth(23.0);
		this.setMinHeight(25.0);
		this.setMaxHeight(25.0);
	}
	
	public MinesweeperButton(int x, int y) {
		this.position = new Point(x,y);
		neightbours = new ArrayList<MinesweeperButton>() ;
		this.flag = false;
		this.setMinWidth(23.0);
		this.setMaxWidth(23.0);
		this.setMinHeight(25.0);
		this.setMaxHeight(25.0);
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
	
	public void setNeighbours(MinesweeperButton temp) {
		this.neightbours.add(temp);
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
