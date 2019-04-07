package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.scene.canvas.GraphicsContext;

public class IDrawingEngine implements DrawingEngine {

	private ArrayList<Shape> shapes;
	private CareTaker ct;
	private int undoCounter;
	private int redoCounter;
	private int counter;
	private static final int MAXUNDO = 20;
	private FileWriter fileWriter;
	private FileReadeer fileReader;

	public IDrawingEngine() {

		shapes = new ArrayList<>();
		ct = new CareTaker();
		ct.add(new Memento(shapes));
		undoCounter = ct.getSizeUndo() - 1;
		redoCounter = 0;
		counter = MAXUNDO;
		fileWriter = new FileWriter();
		fileReader = new FileReadeer();

	}

	@Override
	public void refresh(Object canvas) {
		
		for (int i = 0; i < shapes.size(); i++) {

			shapes.get(i).draw(canvas);

		}

	}

	@Override
	public void addShape(Shape shape) {
		shapes.add(shape);
		Memento state = new Memento(shapes);
		ct.add(state);
		undoCounter = ct.getSizeUndo() - 1;
		counter++;
		if (counter > MAXUNDO) {
			counter = MAXUNDO;
		}
	}

	@Override
	public void removeShape(Shape shape) {
		shapes.remove(shape);
		Memento state = new Memento(shapes);
		ct.add(state);
		undoCounter = ct.getSizeUndo() - 1;
		counter++;
		if (counter > MAXUNDO) {
			counter = MAXUNDO;
		}
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		try {
			
			int index = shapes.indexOf(oldShape);
			System.out.println("the found index of the old shape is : " + index);
			shapes.remove(index);
			shapes.add(index, newShape);
			Memento state = new Memento(shapes);
			ct.add(state);
			undoCounter = ct.getSizeUndo() - 1;
			counter++;
			if (counter > MAXUNDO) {
				counter = MAXUNDO;
			}
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapesTemp = new Shape[shapes.size()];
		return shapes.toArray(shapesTemp);
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> list = new ArrayList<>();

		JarReader j = new JarReader();

		try {
			list.add((Class<? extends Shape>) Class.forName(getClass().getPackage().getName() + ".Elliptical"));
			list.add((Class<? extends Shape>) Class.forName(getClass().getPackage().getName() + ".Coordinate"));
			list.add((Class<? extends Shape>) Class.forName(getClass().getPackage().getName() + ".Polygon"));

			// gets the absolute path of the directory of the Round Rectangle jar file
			String[] temp = getClass().getCanonicalName().split(getClass().getPackage().getName());
			File file = new File(temp[0]);
			System.out.println("Package name: " + file.getAbsolutePath());
			
			list.addAll((Collection<? extends Class<? extends Shape>>) j.getClassesfromJar(file.getAbsolutePath() + "\\RoundRectangle.jar"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(list);
		
		return list;
	}

	@Override
	public void undo() {

		if (counter - 1 < 0 || undoCounter - 1 < 0) {
			return;

		}
		
		undoCounter--;
		redoCounter++;
		counter--;
		shapes = ct.getUndo(undoCounter).getMemento();

	}

	@Override
	public void redo() {

		if (counter > MAXUNDO || redoCounter - 1 < 0) {
			return;
		}
		
		redoCounter--;
		undoCounter++;
		counter++;
		shapes = ct.getRedo(redoCounter).getMemento();
	}

	@Override
	public void save(String path) {

		String[] tempPath = path.split("\\.");
		if (tempPath[tempPath.length - 1].equalsIgnoreCase("XML")) {
			fileWriter.XMLWriter(path, getShapes());
			System.out.println("Drawing Engine Loaded: " + path);
		} else if (tempPath[tempPath.length - 1].equalsIgnoreCase("JSON")) {
			fileWriter.JSONWriter(path, getShapes());
			System.out.println("Drawing Engine Loaded: " + path);
		}
		
	}

	@Override
	public void load(String path) {

		String[] tempPath = path.split("\\.");
		if (tempPath[tempPath.length - 1].equalsIgnoreCase("XML")) {
			shapes = new ArrayList<>();
			ct = new CareTaker();
			ct.add(new Memento(shapes));
			undoCounter = ct.getSizeUndo() - 1;
			redoCounter = 0;
			counter = MAXUNDO;
			shapes = fileReader.XMLReader(path);
			System.out.println("Drawing Engine Loaded: " + path);
		} else if (tempPath[tempPath.length - 1].equalsIgnoreCase("JSON")) {
			shapes = new ArrayList<>();
			ct = new CareTaker();
			ct.add(new Memento(shapes));
			undoCounter = ct.getSizeUndo() - 1;
			redoCounter = 0;
			counter = MAXUNDO;
			shapes = fileReader.JSONReader(path);
			System.out.println("Drawing Engine Loaded: " + path);
		}

	}

}
