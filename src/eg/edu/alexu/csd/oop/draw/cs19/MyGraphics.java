package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class MyGraphics extends JComponent {

	private Shape tempShape;
	private Shape[] shapes  = {};
	private DrawingEngine drawingEngine;
	
    private static final long serialVersionUID = 1L;

    MyGraphics(Dimension d, DrawingEngine drawingEngine) {
        setPreferredSize(d);
        this.drawingEngine = drawingEngine;
    }
    
    public void setShapes(Shape[] shapes) {
    	this.shapes = shapes;
    }
    
    public void setShape(Shape shape) {
    	this.tempShape = shape;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        
        
        drawingEngine.refresh(g);
        
        if (tempShape != null) {
        	tempShape.draw(g);
        }
        
    }
    
}
