package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Coordinate extends IShape {
	
	public Coordinate() {
		super();
		this.getProperties().put("X1", 0.0);
		this.getProperties().put("Y1", 0.0);
		this.getProperties().put("X2", 0.0);
		this.getProperties().put("Y2", 0.0);
		this.getProperties().put("X3", 0.0);
		this.getProperties().put("Y3", 0.0);
		this.getProperties().put("Length", 0.0);
	}
	
	public Coordinate(Point point1, Point point2, Point point3) {

		super();
		
		this.getProperties().put("X1", point1.getX());
		this.getProperties().put("Y1", point1.getY());
		this.getProperties().put("X2", point2.getX());
		this.getProperties().put("Y2", point2.getY());
		this.getProperties().put("X3", point3.getX());
		this.getProperties().put("Y3", point3.getY());
		this.getProperties().put("Length", 0.0);
		
	}
	
	@Override
	public void draw(Object canvas) {
				
		int x = (int) Math.round(getProperties().get("X1"));
		int y = (int) Math.round(getProperties().get("Y1"));
		int l = (int) Math.round(getProperties().get("X2"));
		int m = (int) Math.round(getProperties().get("Y2"));
		int b = (int) Math.round(getProperties().get("X3"));
		int c = (int) Math.round(getProperties().get("Y3"));
		
		GraphicsContext gc = (GraphicsContext) canvas;
		
		if (l == b && m == c) {

			/*Color tempFill = getFillColor();
			javafx.scene.paint.Color colorFill = new javafx.scene.paint.Color(tempFill.getRed(), tempFill.getGreen(), tempFill.getBlue(), tempFill.getTransparency());
			Color temp = getColor();
			javafx.scene.paint.Color color = new javafx.scene.paint.Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getTransparency());*/
			
			gc.setStroke(getColor());
			gc.strokePolygon(new double[] {x, l, x}, new double[] {y, m, y}, 2);

		} else { //Triangle Case

			/*Color tempFill = getFillColor();
			javafx.scene.paint.Color colorFill = new javafx.scene.paint.Color(tempFill.getRed(), tempFill.getGreen(), tempFill.getBlue(), tempFill.getTransparency());
			Color temp = getColor();
			javafx.scene.paint.Color color = new javafx.scene.paint.Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getTransparency());*/
			
			gc.setFill(getFillColor());
			gc.fillPolygon(new double[] {x, l, b}, new double[] {y, m, c}, 3);
			gc.setStroke(getColor());
			gc.strokePolygon(new double[] {x, l, b}, new double[] {y, m, c}, 3);
			
		}
	}

}
