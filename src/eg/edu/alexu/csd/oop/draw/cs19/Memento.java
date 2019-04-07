package eg.edu.alexu.csd.oop.draw.cs19;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Memento {

	private ArrayList<Shape> shapes;

	/**
	 * a method that saves the state of a list of shapes.
	 * 
	 * @param shapes
	 *            ... the list of shapes to be saved.
	 */
	public Memento(ArrayList<Shape> shapes) {

		this.shapes = new ArrayList<>();
		for (int i = 0; i < shapes.size(); i++) {
			this.shapes.add(shapes.get(i));
		}

	}

	/**
	 * gets the state of the list of shapes it has.
	 * 
	 * @return ... it returns the list of shapes.
	 */
	public ArrayList<Shape> getMemento() {

		ArrayList<Shape> shapes = new ArrayList<>();
		for (int i = 0; i < this.shapes.size(); i++) {
			shapes.add(this.shapes.get(i));
		}
		return shapes;

	}

}
