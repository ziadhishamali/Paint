package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private Factory factory;
	private DrawingEngine drawingEngine;
	private Point startPoint;
	private Point endPoint;
	private Point dump;
	private Shape s = null;
	private Shape temp = null;
	private Shape tempResize = null;
	private boolean triangleFlag = false;
	private boolean thirdPointFlag = false;
	private boolean selectBtnFlag = false;
	private boolean newSelect = true;
	private List<Shape> selectedShapes = new ArrayList<>();
	private List<Shape> copiedShapes = new ArrayList<>();
	private Selected select;
	private boolean firstChange = true;
	private Shape newShape;
	private int scrollCounter = 0;
	private int offSetCounter = 50;
	private int globalIndex = 0;

	public Button undoBtn;
	public Button redoBtn;
	public Button saveBtn;
	public Button loadBtn;
	public Button selectBtn;
	public ToolBar toolBar;

	public ColorPicker colorPicker;
	public ColorPicker fillColorPicker;

	public Slider zoomSlider;

	public ComboBox<String> shapesCombo;

	public Pane mainPane;
	public Pane canvasPane;
	public Canvas canvas;
	public GraphicsContext gc;

	private String text = "";
	private Stage primaryStage;

	/**
	 * Listener to the keyboard key pressing
	 * 
	 * @param e
	 *            the keyEvent
	 * @throws CloneNotSupportedException
	 */
	public void canvasListenerKeyPressed(KeyEvent e) throws CloneNotSupportedException {

		if (selectBtnFlag) {

			if (e.isControlDown()) {
				if (e.getCode() == KeyCode.C) {

					if (!selectedShapes.isEmpty()) {
						copiedShapes.clear();
						offSetCounter = 50;
					}

					for (int i = 0; i < selectedShapes.size(); i++) {

						try {
							copiedShapes.add((Shape) selectedShapes.get(i).clone());
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

					}

				}
			}

		}

		if (e.isControlDown()) {
			if (e.getCode() == KeyCode.V) {

				for (int i = 0; i < copiedShapes.size(); i++) {

					Shape shapeTemp = (Shape) copiedShapes.get(i).clone();
					Point p = new Point(offSetCounter, 0);
					select.moveSelected(shapeTemp, p);
					drawingEngine.addShape(shapeTemp);

				}

				offSetCounter += 50;
				drawAll();

			}
		}

		if (e.isControlDown()) {
			if (e.getCode() == KeyCode.Z) {

				drawingEngine.undo();
				drawAll();

			} else if (e.getCode() == KeyCode.Y) {

				drawingEngine.redo();
				drawAll();

			} else if (e.getCode() == KeyCode.S) {

				saveListener();

			} else if (e.getCode() == KeyCode.L) {

				loadListener();

			}
		}

		if (selectBtnFlag) {

			if (!e.isControlDown()) {
				if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {

					for (int i = 0; i < selectedShapes.size(); i++) {

						drawingEngine.removeShape(selectedShapes.get(i));

						selectedShapes.remove(i);

						i--;

						drawAll();

					}

				}
			}

		}

	}

	/**
	 * Listener for the scrolling on canvas for resizing shapes
	 * 
	 * @param e
	 *            the scrollEvent
	 */
	/*
	 * public void canvasListenerScrolled(ScrollEvent e) {
	 * 
	 * if (selectBtnFlag) {
	 * 
	 * // zoom factor by which the properties of the shape is multiplied Double
	 * zoomFactor = 1.1;
	 * 
	 * if (e.getDeltaY() < 0) { zoomFactor = 0.9; }
	 * 
	 * for (int i = 0; i < selectedShapes.size(); i++) {
	 * 
	 * try { newShape = (Shape) selectedShapes.get(i).clone();
	 * 
	 * select.resizeShape(newShape, zoomFactor);
	 * 
	 * s = newShape;
	 * 
	 * if (firstChange) { tempResize = selectedShapes.get(i); firstChange = false; }
	 * 
	 * // drawingEngine.updateShape(selectedShapes.get(i), newShape);
	 * 
	 * selectedShapes.remove(i); selectedShapes.add(i, newShape); globalIndex = i;
	 * 
	 * drawAll();
	 * 
	 * scrollCounter++;
	 * 
	 * Thread th = new Thread(() -> { try { Thread.sleep(1000); if (scrollCounter ==
	 * 1) {
	 * 
	 * System.out.println("Scroll fifnished !!"); if (selectBtnFlag) {
	 * drawingEngine.updateShape(tempResize, newShape);
	 * selectedShapes.remove(globalIndex); selectedShapes.add(globalIndex,
	 * newShape); firstChange = true; // temp = newShape; s = null; drawAll(); }
	 * 
	 * }
	 * 
	 * scrollCounter--; } catch (Exception e1) { e1.printStackTrace(); } });
	 * th.setDaemon(true); th.start();
	 * 
	 * } catch (CloneNotSupportedException e1) { e1.printStackTrace(); }
	 * 
	 * } }
	 * 
	 * }
	 */

	/**
	 * Function we call when the value of the slider we use in resize changes.
	 * 
	 * @param factor
	 *            the factor by which we multiply the properties of the shape.
	 */
	public void sliderListenerPressed(Double factor) {
		if (selectBtnFlag) {

			// zoom factor by which the properties of the shape is multiplied
			Double zoomFactor = factor;

			for (int i = 0; i < selectedShapes.size(); i++) {

				try {
					newShape = (Shape) selectedShapes.get(i).clone();

					select.resizeShape(newShape, zoomFactor);

					s = newShape;

					if (firstChange) {
						tempResize = selectedShapes.get(i);
						firstChange = false;
					}

					// drawingEngine.updateShape(selectedShapes.get(i), newShape);

					selectedShapes.remove(i);
					selectedShapes.add(i, newShape);
					globalIndex = i;

					drawAll();

				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	/**
	 * the function we call when the user finishes the resize of the shapes to save
	 * that new shape.
	 */
	public void sliderListenerStop() {

		if (selectBtnFlag) {

			System.out.println("Scroll finished !!");
			zoomSlider.setValue(1);

			if (selectBtnFlag) {
				if (tempResize != null) {
					try {
						drawingEngine.updateShape(tempResize, newShape);
						selectedShapes.remove(globalIndex);
						selectedShapes.add(globalIndex, newShape);
						firstChange = true;
						// temp = newShape;
						s = null;
						drawAll();
					} catch (Exception e) {

					}
				}
			}

		}

	}

	/**
	 * Listener for the mouse clicks on the canvas
	 * 
	 * @param e
	 *            the mouseEvent
	 */
	public void canvasListenerPressed(MouseEvent e) {

		if (selectBtnFlag) {

			sliderListenerStop();
			zoomSlider.setValue(1);

			if (!e.isControlDown()) {
				selectedShapes.clear();
				newSelect = true;
			}

			firstChange = true;

			startPoint = new Point((int) e.getX(), (int) e.getY());

			System.out.println(drawingEngine.getShapes().length);
			int index = select.IsSelected(new Point((int) e.getX(), (int) e.getY()));
			System.out.println("Done");

			if (index != -1) {

				System.out.println(index);
				selectedShapes.add(drawingEngine.getShapes()[index]);

			}

		} else {
			if (triangleFlag && thirdPointFlag) {
				dump = new Point((int) e.getX(), (int) e.getY());
				s = factory.createShape(text, startPoint, endPoint, dump);
			} else if (triangleFlag && !thirdPointFlag) {
				startPoint = new Point((int) e.getX(), (int) e.getY());
			} else {
				startPoint = new Point((int) e.getX(), (int) e.getY());
			}

			System.out.println(startPoint);
		}

	}

	/**
	 * Listener for the mouse release on the canvas
	 * 
	 * @param e
	 *            the mouseEvent
	 */
	public void canvasListenerReleased(MouseEvent e) {

		if (!selectBtnFlag) {
			if (triangleFlag && thirdPointFlag) {
				s = factory.createShape(text, startPoint, endPoint, dump);
				s.setPosition(startPoint);
				s.setColor(colorPicker.getValue());
				s.setFillColor(fillColorPicker.getValue());
				if (s != null) {
					drawingEngine.addShape(s);
					s = null;
					drawAll();
				}
				drawAll();
				thirdPointFlag = false;

			} else if (triangleFlag && !thirdPointFlag) {
				thirdPointFlag = true;
			} else {
				endPoint = new Point((int) e.getX(), (int) e.getY());
				if (s != null) {
					drawingEngine.addShape(s);
					s = null;
					drawAll();
				}

			}
		} else {

			try {

				if (temp != null && !newSelect) {
					drawingEngine.updateShape(temp, newShape);
					selectedShapes.remove(globalIndex);
					selectedShapes.add(globalIndex, newShape);
					firstChange = true;
					s = null;
					drawAll();
				} else if (!newSelect) {
					newSelect = false;
				}

			} catch (Exception e1) {

			}

		}

	}

	/**
	 * Listener for the mouse dragging on the canvas.
	 */
	public void canvasListenerDragged(MouseEvent e) {

		if (!selectBtnFlag) { // checks if the select button is clicked or not

			// select button not clicked

			if (!thirdPointFlag) { // checks if the third point is needed or not

				// the new end point
				endPoint = new Point((int) e.getX(), (int) e.getY());

				if (text.equals("Triangle")) { // checks if the shape requires three points to be drawn i.e. triangle

					s = factory.createShape(text, startPoint, endPoint, endPoint);

				} else {
					s = factory.createShape(text, startPoint, endPoint, null);
				}

				if (s == null) { // the shape isnot from the supported shapes

					try { // checks if the shape is in an extinsible plugin jar and if so creates it

						s = factory.showPropUnknown(text, startPoint);

					} catch (Exception e1) {

						e1.printStackTrace();

					}

				}

				// sets the position of the created shape
				s.setPosition(startPoint);
				try { // tries to set the color using the javaFX color type

					s.setColor(colorPicker.getValue());
					s.setFillColor(fillColorPicker.getValue());

				} catch (Exception e3) { // set the color using the swing color type

					java.awt.Color color = new java.awt.Color((int) colorPicker.getValue().getRed(),
							(int) colorPicker.getValue().getRed(), (int) colorPicker.getValue().getRed());
					java.awt.Color colorFill = new java.awt.Color((int) fillColorPicker.getValue().getRed(),
							(int) fillColorPicker.getValue().getRed(), (int) fillColorPicker.getValue().getRed());
					s.setColor(color);
					s.setFillColor(colorFill);

				}

				// redraws the canvas again
				drawAll();

			}

		} else { // the select button is clicked and we encounter a move

			// gets the end point of the mouse
			endPoint = new Point((int) e.getX(), (int) e.getY());
			newSelect = false;

			// loops through all the selected shapes
			for (int i = 0; i < selectedShapes.size(); i++) {

				try {

					// clone selected shape to be moved
					newShape = (Shape) selectedShapes.get(i).clone();

					// p is a point that represents the difference between the start point and the
					// end point of the move
					Point p = new Point(endPoint.x - startPoint.x, endPoint.y - startPoint.y);

					// move the selected shape to the new position
					select.moveSelected(newShape, p);

					s = newShape;

					if (firstChange) {
						temp = selectedShapes.get(i);
						firstChange = false;
					}

					selectedShapes.remove(i);
					selectedShapes.add(i, newShape);
					globalIndex = i;

					drawAll();

				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

			}

			// update the start point of the select to the current end point
			startPoint = new Point(endPoint.x, endPoint.y);

		}

	}

	/**
	 * Listener for the combo box containing all drawable shapes
	 */
	public void comboListener() {

		// gets the name of the shape to be drawn
		text = shapesCombo.getSelectionModel().getSelectedItem();

		// knows if the shape requires more than two points to be drawn i.e. triangle
		if (text.equals("Triangle")) {
			triangleFlag = true;
			thirdPointFlag = false;
		} else {

			triangleFlag = false;
			thirdPointFlag = false;

		}

	}

	/**
	 * Listener for the select button
	 */
	public void selectListener() {

		if (!selectBtnFlag) {
			selectBtnFlag = true;
			shapesCombo.setDisable(true);
			zoomSlider.setDisable(false);
			zoomSlider.setValue(1);
		} else {
			selectBtnFlag = false;
			shapesCombo.setDisable(false);
			zoomSlider.setDisable(true);
			zoomSlider.setValue(1);
			shapesCombo.requestFocus();
			selectedShapes.clear();
		}

	}

	/**
	 * Listener for the undo button
	 */
	public void undoListener() {

		drawingEngine.undo();
		drawAll();

	}

	/**
	 * Listener for the redo button
	 */
	public void redoListener() {

		drawingEngine.redo();
		drawAll();

	}

	/**
	 * Listener for the save button
	 */
	public void saveListener() {

		String savePath = "";

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Save");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"),
				new FileChooser.ExtensionFilter("JSON", "*.json"));

		try {
			savePath = fileChooser.showSaveDialog(this.primaryStage).getAbsolutePath();

			System.out.println("Saved at: " + savePath);

			drawingEngine.save(savePath);

			drawAll();
		} catch (Exception e) {

		}

	}

	/**
	 * Listener for the load button
	 */
	public void loadListener() {

		String loadPath = "";

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Load");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"),
				new FileChooser.ExtensionFilter("JSON", "*.json"));

		try {
			loadPath = fileChooser.showOpenDialog(this.primaryStage).getAbsolutePath();
			System.out.println("loaded from: " + loadPath);
			drawingEngine.load(loadPath);
			selectBtnFlag = false;
			triangleFlag = false;
			thirdPointFlag = false;
			newSelect = true;
			shapesCombo.setDisable(false);
			firstChange = true;
			scrollCounter = 0;
			offSetCounter = 50;
			globalIndex = 0;
			select = new Selected(drawingEngine);
			zoomSlider.setDisable(true);
			zoomSlider.setValue(1);
			selectedShapes.clear();
			drawAll();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error !!");
			error.setHeaderText(null);
			error.setContentText("Please Choose a compatible file !!");
			error.showAndWait();
		}

	}

	/**
	 * Listener for the reset button
	 */
	public void resetListener() {

		// reinitiate the drawing engine i.e. removes all shapes and history
		try {
			this.drawingEngine = this.drawingEngine.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		selectBtnFlag = false;
		triangleFlag = false;
		thirdPointFlag = false;
		newSelect = true;
		shapesCombo.setDisable(false);
		firstChange = true;
		scrollCounter = 0;
		offSetCounter = 50;
		globalIndex = 0;
		select = new Selected(drawingEngine);
		selectedShapes.clear();
		zoomSlider.setDisable(true);
		zoomSlider.setValue(1);
		drawAll();

	}

	/**
	 * Listener for the plugin button
	 */
	public void pluginListener() {

		String loadPath = "";

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Load");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR", "*.jar"));

		try {
			loadPath = fileChooser.showOpenDialog(this.primaryStage).getAbsolutePath();
			System.out.println("loaded from: " + loadPath);

			JarReader jarReader = new JarReader();

			jarReader.getClassesfromJar(loadPath);

			List<Class<? extends Shape>> l = drawingEngine.getSupportedShapes();

			String temp = l.get(l.size() - 1).getName();
			String[] temp2arr = temp.split("\\.");

			this.shapesCombo.getItems().add(temp2arr[temp2arr.length - 1]);
		} catch (Exception e) {

		}

	}

	public void colorPickerListener() {

		if (selectBtnFlag) {

			for (int i = 0; i < selectedShapes.size(); i++) {

				try {

					// clone selected shape to be changed
					newShape = (Shape) selectedShapes.get(i).clone();

					newShape.setColor(colorPicker.getValue());

					drawingEngine.updateShape(selectedShapes.get(i), newShape);

					selectedShapes.remove(i);
					selectedShapes.add(i, newShape);

					drawAll();

				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

			}

		}

	}

	public void fillColorPickerListener() {

		if (selectBtnFlag) {

			for (int i = 0; i < selectedShapes.size(); i++) {

				try {

					// clone selected shape to be changed
					newShape = (Shape) selectedShapes.get(i).clone();

					newShape.setFillColor(fillColorPicker.getValue());

					drawingEngine.updateShape(selectedShapes.get(i), newShape);

					selectedShapes.remove(i);
					selectedShapes.add(i, newShape);

					drawAll();

				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

			}

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * initializes the controller.
	 * 
	 * @param factory
	 *            ... the factory from which shapes are created.
	 */
	public void initiateController(Factory factory, Stage primaryStage) {

		this.factory = factory;
		this.startPoint = new Point();
		this.endPoint = new Point();
		this.drawingEngine = factory.getDrawingEngine();
		this.canvas = new Canvas();
		this.gc = canvas.getGraphicsContext2D();
		this.canvasPane.getChildren().add(canvas);
		this.select = new Selected(this.drawingEngine);
		this.primaryStage = primaryStage;
		this.drawingEngine.getSupportedShapes();

		canvas.widthProperty().bind(canvasPane.widthProperty());
		canvas.heightProperty().bind(canvasPane.heightProperty());

		this.shapesCombo.getItems().addAll("Circle", "Ellipse", "Square", "Rectangle", "Triangle", "Line Segment");
		this.shapesCombo.setValue("Circle");
		text = "Circle";

		this.colorPicker.setValue(Color.BLACK);

		this.zoomSlider.setDisable(true);
		this.zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (zoomSlider.isValueChanging()) {
					if (Double.valueOf(old_val.toString()) < Double.valueOf(new_val.toString())) {
						sliderListenerPressed(1.1);
					} else {
						sliderListenerPressed(0.9);
					}

				} else {
					sliderListenerStop();
				}
				System.out.println(new_val.doubleValue());
			}
		});

	}

	/**
	 * redraws everything on the canvas.
	 */
	public void drawAll() {

		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		try {
			drawingEngine.refresh(gc);
		} catch (Exception e) {

		}

		if (s != null) {
			s.draw(gc);
		}

	}

}
