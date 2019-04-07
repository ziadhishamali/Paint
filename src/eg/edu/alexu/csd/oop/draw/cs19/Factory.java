package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public interface Factory {

	/**
	 * It creates a new shape of a certain type and return it.
	 * 
	 * @param type
	 *            ...the type of shape we want to create.
	 * @param startPoint
	 *            ...the start point of the shape.
	 * @param endPoint
	 *            ...the end point of the shape.
	 * @param dump
	 *            ...a point used to draw the triangle
	 * @return ... returns the shape we created.
	 */
	public Shape createShape(String type, Point startPoint, Point endPoint, Point dump);

	/**
	 * 
	 * @return ... it returns the drawing engine we are working with.
	 */
	public DrawingEngine getDrawingEngine();

	/**
	 * it shows a dialog containg textfields for the user to enter the properties of
	 * an unknown added shape.
	 * 
	 * @param type
	 *            ...the type of the shape we are creating.
	 * @param startPoint
	 *            ...the start point of the shape.
	 * @return ... it returns the shape we've created.
	 * @throws Exception
	 *             ... it throws an exception in case something went wrong with the
	 *             shape we are creating.
	 */
	public Shape showPropUnknown(String type, Point startPoint) throws Exception;

}
