package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class ShapeFactory implements Factory {
	
	// Our drawing engine
	private DrawingEngine drawingEngine;
	
	// Constructor
	public ShapeFactory(DrawingEngine drawingEngine) {

		this.drawingEngine = drawingEngine;
	}

	@Override
	public Shape createShape(String type, Point startPoint, Point endPoint, Point dump) {
		if (type.equals("Circle")) {

			Double radius = distance(startPoint, endPoint);
			return new Elliptical(radius, radius);

		} else if (type.equals("Ellipse")) {

			Double shortRad = Math.abs(endPoint.getX() - startPoint.getX());
			Double longRad = Math.abs(endPoint.getY() - startPoint.getY());
			return new Elliptical(shortRad, longRad);

		} else if (type.equals("Square")) {

			Double sideLengthX = Math.abs(endPoint.getX() - startPoint.getX());
			Double sideLengthY = Math.abs(endPoint.getY() - startPoint.getY());

			Double sideLength = Math.max(sideLengthX, sideLengthY);
			Double diffLength = Math.abs(sideLengthX - sideLengthY);

			Double x = startPoint.getX();
			Double y = startPoint.getY();
			Double l = endPoint.getX();
			Double m = endPoint.getY();
				
			if (l <= x && m <= y) {   //2st Quad
	
					if (sideLengthX >= sideLengthY) {
		
						endPoint.setLocation(l, m - diffLength);
		
					} else if (sideLengthX < sideLengthY) {
		
						endPoint.setLocation(l - diffLength, m);
		
					}
	
			} else if (l <= x && m >= y) {  //3rd Quad
	
				if (sideLengthX >= sideLengthY) {
					
					endPoint.setLocation(l, m + diffLength);
	
				} else if (sideLengthX < sideLengthY) {
	
					endPoint.setLocation(l - diffLength, m);
	
				}
			} else if (l >= x && m <= y) {  //1st Quad
	
				if (sideLengthX >= sideLengthY) {
					
					endPoint.setLocation(l, m - diffLength);
	
				} else if (sideLengthX < sideLengthY) {
	
					endPoint.setLocation(l + diffLength, m);
	
				}
	
			} else if (l >= x && m >= y) {  //4th Quad
	
				if (sideLengthX >= sideLengthY) {
					
					endPoint.setLocation(l, m + diffLength);
	
				} else if (sideLengthX < sideLengthY) {
	
					endPoint.setLocation(l + diffLength, m);
	
				}
			}
			
			Shape s = new Polygon(sideLength, sideLength, startPoint, endPoint);
			s.setPosition(startPoint);
			return s;

		} else if (type.equals("Rectangle")) {

			Double length = Math.abs(endPoint.getX() - startPoint.getX());
			Double width = Math.abs(endPoint.getY() - startPoint.getY());

			Shape s = new Polygon(length, width, startPoint, endPoint);
			s.setPosition(startPoint);

			return s;

		} else if (type.equals("Triangle")) {

			return new Coordinate(startPoint, endPoint, dump);

		} else if (type.equals("Line Segment")) {

			return new Coordinate(startPoint, endPoint, endPoint);

		} else {
			
			return null;
			
		}

	}

	private Double distance(Point startPoint, Point endPoint) {

		Double res = 0.0;

		res = Math.sqrt((startPoint.getX() - endPoint.getX()) * (startPoint.getX() - endPoint.getX())
				+ (startPoint.getY() - endPoint.getY()) * (startPoint.getY() - endPoint.getY()));

		return res;

	}
	
	@Override
	public Shape showPropUnknown(String type, Point startPoint) throws Exception {
		
		List<Class<? extends Shape>> l = drawingEngine.getSupportedShapes();
		
		for (int i = 0; i < l.size(); i++) {
			
			String temp = l.get(i).getName();
			String[] temp2arr = temp.split("\\.");
			
			if (type.equals(temp2arr[temp2arr.length - 1])) {
				
				Shape shape;
				
				shape = l.get(i).newInstance();
				
				Dialog<Pair<String, String>> dialog = new Dialog<>();
			    dialog.setTitle("Properties of the Shape");
			    
			    List<TextField> list = new ArrayList<>(); 

			    // Set the button types.
			    ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
			    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

			    GridPane gridPane = new GridPane();
			    gridPane.setHgap(10);
			    gridPane.setVgap(10);
			    gridPane.setPadding(new Insets(20, 150, 10, 10));
			    int counter = 0;
			    for (String key : shape.getProperties().keySet()) {
			    	
			    	TextField t = new TextField();
			    	t.setPromptText(key);
			    	list.add(t);
			    	gridPane.add(t, 0, counter);
			    	counter+=2;
			    	
			    }

			    dialog.getDialogPane().setContent(gridPane);

			    // Request focus on the username field by default.
			    Platform.runLater(() -> list.get(0).requestFocus());

			    // Convert the result to a username-password-pair when the login button is clicked.
			    dialog.setResultConverter(dialogButton -> {
			        if (dialogButton == loginButtonType) {
			        	for (int j = 0; j < list.size(); j++) {
			        		
			        		for (String key : shape.getProperties().keySet()) {
			        			
			        			try {
			        				
			        				String t = list.get(j).getText();
			        				Double res = Double.parseDouble(t);
			        				shape.getProperties().put(key, res);
			        				j++;
			        				if (j >= list.size()) {
			        					break;
			        				}
			        				
			        			} catch(Exception e) {
			        				
			        			}
			        			
			        		}
			        		
			        	}
			        	
			        	shape.setPosition(startPoint);
			            return null;
			        }
			        return null;
			    });

			    dialog.showAndWait();
			    
			    return shape;
				
			}
			
		}
		
		return null;
		
	}

	@Override
	public DrawingEngine getDrawingEngine() {
		return this.drawingEngine;
	}

}
