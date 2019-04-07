package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;

public class Polygon extends IShape {
	
	public Polygon() {
		super();
		this.getProperties().put("Length", 0.0);
		this.getProperties().put("Width", 0.0);
		this.getProperties().put("X1", 0.0);
		this.getProperties().put("Y1", 0.0);
		this.getProperties().put("X2", 0.0);
		this.getProperties().put("Y2", 0.0);
	}
	
	public Polygon(Double par1, Double par2, Point startPoint, Point endPoint) {
		
		super();
		
		this.getProperties().put("Length", par1);
		this.getProperties().put("Width", par2);
		this.getProperties().put("X1", startPoint.getX());
		this.getProperties().put("Y1", startPoint.getY());
		this.getProperties().put("X2", endPoint.getX());
		this.getProperties().put("Y2", endPoint.getY());
		
		
		/*if (type.equals("Rectangle")) {
			
			this.getProperties().put("Length", 0.0);
			this.getProperties().put("Width", 0.0);
			
		} else if (type.equals("Square")) {
			
			this.getProperties().put("Side Length", 0.0);
			
		} else if (type.equals("Line Segment")) {
			
			this.getProperties().put("X1", 0.0);
			this.getProperties().put("Y1", 0.0);
			this.getProperties().put("X2", 0.0);
			this.getProperties().put("Y2", 0.0);
			
		} else if (type.equals("Triangle")) {
			
			this.getProperties().put("X1", 0.0);
			this.getProperties().put("Y1", 0.0);
			this.getProperties().put("X2", 0.0);
			this.getProperties().put("Y2", 0.0);
			this.getProperties().put("X3", 0.0);
			this.getProperties().put("Y3", 0.0);
			
		}*/
		
		
	}
	
	@Override
	public void draw(Object canvas) {

		int x = (int) Math.round(getProperties().get("X1"));
		int y = (int) Math.round(getProperties().get("Y1"));
		int l = (int) Math.round(getProperties().get("X2"));
		int m = (int) Math.round(getProperties().get("Y2"));
		
		GraphicsContext gc = (GraphicsContext) canvas;

		/*Color tempFill = getFillColor();
		javafx.scene.paint.Color colorFill = new javafx.scene.paint.Color(tempFill.getRed(), tempFill.getGreen(), tempFill.getBlue(), tempFill.getTransparency());
		Color temp = getColor();
		javafx.scene.paint.Color color = new javafx.scene.paint.Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getTransparency());*/
		
		gc.setFill(getFillColor());
		gc.fillPolygon(new double[] {x, l, l, x}, new double[] {y, y, m, m}, 4);
		gc.setStroke(getColor());
		gc.strokePolygon(new double[] {x, l, l, x}, new double[] {y, y, m, m}, 4);

	}
	
}
