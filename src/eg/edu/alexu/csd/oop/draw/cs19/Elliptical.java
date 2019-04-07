package eg.edu.alexu.csd.oop.draw.cs19;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class Elliptical extends IShape {
	
	public Elliptical() {
		super();
		this.getProperties().put("Short Radius", 0.0);
		this.getProperties().put("Long Radius", 0.0);
	}
	
	public Elliptical(Double par1, Double par2) {
		
		super();
		
		this.getProperties().put("Short Radius", par1);
		this.getProperties().put("Long Radius", par2);
		
		/*if (type.equals("Circle")) {
			
			this.getProperties().put("Radius", 0.0);
			
		} else if (type.equals("Ellipse")) {
			
			this.getProperties().put("Short Radius", 0.0);
			this.getProperties().put("Long Radius", 0.0);
			
		}*/
		
	}
	
	@Override
	public void draw(Object canvas) {
		
		double x = getPosition().getX();
		double y = getPosition().getY();
		double sR = getProperties().get("Short Radius");
		double lR = getProperties().get("Long Radius");
		
		GraphicsContext gc = (GraphicsContext) canvas;

		/*Color tempFill = getFillColor();
		javafx.scene.paint.Color colorFill = new javafx.scene.paint.Color(tempFill.getRed(), tempFill.getGreen(), tempFill.getBlue(), tempFill.getTransparency());
		Color temp = getColor();
		javafx.scene.paint.Color color = new javafx.scene.paint.Color(temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getTransparency());*/
		
		gc.setFill(getFillColor());
		gc.fillOval(x - sR, y - lR, 2 * sR, 2 * lR);
		gc.setStroke(getColor());
		gc.strokeOval(x - sR, y - lR, 2 * sR, 2 * lR);
		
		
		/*Pane p = new Pane();
		p.setClip(new Ellipse(x, y, lR, sR));*/
		
	}

}
