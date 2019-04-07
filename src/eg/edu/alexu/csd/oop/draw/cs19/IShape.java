package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.scene.paint.Color;

public class IShape implements Shape {

	private Map<String, Double> properties;
	private Point position;
	private Color fillColor;
	private Color color;

	public IShape() {

		this.properties = new HashMap<>();
		this.position = new Point();
		this.fillColor = new Color(0.0, 0.0, 0.0, 0.0);
		this.color = new Color(0.0, 0.0, 0.0, 0.0);

	}

	@Override
	public void setPosition(Object position) {
		this.position = (Point) position;
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		this.properties = properties;
	}

	@Override
	public Map<String, Double> getProperties() {
		return this.properties;
	}

	@Override
	public void setColor(Object color) {
		this.color = (Color) color;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setFillColor(Object color) {
		this.fillColor = (Color) color;
	}

	@Override
	public Color getFillColor() {
		return this.fillColor;
	}

	@Override
	public void draw(Object canvas) {

		Graphics g = (Graphics) canvas;
		canvas = g;
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		try {

			Shape newShape = this.getClass().newInstance();

			newShape.setPosition(this.getPosition());
			/*
			 * Iterator it = this.getProperties().entrySet().iterator(); while
			 * (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next(); newProp.put((String)
			 * pair.getKey(), (Double) pair.getValue()); it.remove(); }
			 */

			for (String key : this.getProperties().keySet()) {

				newShape.getProperties().put(key, this.getProperties().get(key));

			}

			newShape.setColor(this.getColor());
			newShape.setFillColor(this.getFillColor());

			return newShape;

		} catch (Exception e) {

			return null;
		}

	}

}
