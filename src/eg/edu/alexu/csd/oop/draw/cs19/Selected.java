package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class Selected {
	
	private DrawingEngine drawingEngine;	
	Shape[] shapes;
	
	public Selected(DrawingEngine drawingEngine) {
		this.drawingEngine = drawingEngine;
		this.shapes = this.drawingEngine.getShapes();
	}
	
	private double triArea(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3) { 
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))/ (Double) 2.000000000000000000); 
	} 
	
	public int IsSelected(Point clicked) {
		
		Double x = (double) clicked.x;
		Double y = (double) clicked.y;
		System.out.println(drawingEngine.getShapes().length);
		
		this.shapes = this.drawingEngine.getShapes();
		
		System.out.println(shapes.length);
		
		for (int i = shapes.length - 1; i >= 0; i--) {
			Map<String, Double> shapeMap = shapes[i].getProperties();
			Point shapeCenter = (Point) shapes[i].getPosition();
			int l = shapeMap.size();
			
			if (l == 2) {  //Circle Or Ellipse
				Double r1 = (Double) shapeMap.get("Long Radius");
				Double r2 = (Double) shapeMap.get("Short Radius");
				Double h = (double) shapeCenter.x;
				Double k = (double) shapeCenter.y;
				if (r1.equals(r2)) { //Circle
					double equ1 = Math.sqrt( ( (x - h) * (x - h) ) + ( (y - k) * (y - k) ) );
					if (equ1 <= r1) {
						return i;
					}
				} else { //Ellipse
					double equ2 =   ( Math.pow((x - h) , 2) / Math.pow(r2, 2) ) + ( Math.pow((y - k) , 2) / Math.pow(r1, 2) ) ; 
					if (equ2 <= 1) {
						return i;
					}
					
				}
			} else if (l == 6) { //Square Or Rectangle
				Double x1 = (Double) shapeMap.get("X1");
				Double y1 = (Double) shapeMap.get("Y1");
				Double x2 = (Double) shapeMap.get("X2");
				Double y2 = (Double) shapeMap.get("Y2");
				Double x3 = x1;
				Double y3 = y2;
				Double x4 = x2;
				Double y4 = y1;
				
				/* Calculate area of rectangle ABCD */
		        double A = triArea(x1, y1, x2, y2, x3, y3)+  
		        		triArea(x1, y1, x4, y4, x2, y2); 
		      
		        /* Calculate area of triangle PAB */
		        double A1 = triArea(x, y, x1, y1, x3, y3); 
		      
		        /* Calculate area of triangle PBC */
		        double A2 = triArea(x, y, x1, y1, x4, y4); 
		      
		        /* Calculate area of triangle PCD */
		        double A3 = triArea(x, y, x2, y2, x4, y4); 
		      
		        /* Calculate area of triangle PAD */
		        double A4 = triArea(x, y, x2, y2, x3, y3);
		      
		        /* Check if sum of A1, A2, A3 and A4  
		        is same as A */
		        if (Math.abs(A - (A1 + A2 + A3 + A4)) <= 0.0132132131564)
		        	return i; 

				
				
				
			} else if (l == 7) { //Triangle Or LineSegment
				
				Double x1 = (Double) shapeMap.get("X1");
				Double y1 = (Double) shapeMap.get("Y1");
				Double x2 = (Double) shapeMap.get("X2");
				Double y2 = (Double) shapeMap.get("Y2");
				Double x3 = (Double) shapeMap.get("X3");
				Double y3 = (Double) shapeMap.get("Y3");
				
				if (x2.equals(x3) && y2.equals(y3)) { //lineSegmnet
					Double dxc = x - x1;
					Double dyc = y - y1;
					Double dxl = x2 - x1;
					Double dyl = y2 - y1;
					
					Double s1 = dxc / dxl;
					Double s2 = dyc / dyl;
					
					if (Math.abs(s1 - s2) <= 0.15) {
						if (s1 / s2 >= 0 && s1 / s2 <= 1) {
							return i;
						}
					}

				} else { //Triangle
					/* Calculate area of triangle ABC */
			        double A = triArea (x1, y1, x2, y2, x3, y3); 
			       
			       /* Calculate area of triangle PBC */  
			        double A1 = triArea (x, y, x2, y2, x3, y3); 
			       
			       /* Calculate area of triangle PAC */  
			        double A2 = triArea (x1, y1, x, y, x3, y3); 
			       
			       /* Calculate area of triangle PAB */   
			        double A3 = triArea (x1, y1, x2, y2, x, y); 
			         
			       /* Check if sum of A1, A2 and A3 is same as A */
			        if (Math.abs(A - (A1 + A2 + A3)) <= 0.2)
			        	return i; 
				}
				
			}
			
		}
		return -1;
	}
	
	
	
	public void moveSelected(Shape s, Point newPoint) {
		
		System.out.println("in the select: " + s.getClass());
		
		if (s.getClass().getName().contains("Elliptical")) {
			Point p = new Point(((Point)s.getPosition()).x + newPoint.x, ((Point)s.getPosition()).y + newPoint.y);
			s.setPosition(p);
		}
		
		if (s.getClass().getName().contains("Polygon")) {
			Point p = new Point(((Point)s.getPosition()).x + newPoint.x, ((Point)s.getPosition()).y + newPoint.y);
			s.setPosition(p);
			double x1 = s.getProperties().get("X1");
			double x2 = s.getProperties().get("X2");
			double y1 = s.getProperties().get("Y1");
			double y2 = s.getProperties().get("Y2");
			s.getProperties().put("X1", x1 + newPoint.x);
			s.getProperties().put("X2", x2 + newPoint.x);
			s.getProperties().put("Y1", y1 + newPoint.y);
			s.getProperties().put("Y2", y2 + newPoint.y);
		}
		
		if (s.getClass().getName().contains("Coordinate")) {
			Point p = new Point(((Point)s.getPosition()).x + newPoint.x, ((Point)s.getPosition()).y + newPoint.y);
			s.setPosition(p);
			double x1 = s.getProperties().get("X1");
			double x2 = s.getProperties().get("X2");
			double y1 = s.getProperties().get("Y1");
			double y2 = s.getProperties().get("Y2");
			double x3 = s.getProperties().get("X3");
			double y3 = s.getProperties().get("Y3");
			s.getProperties().put("X1", x1 + newPoint.x);
			s.getProperties().put("X2", x2 + newPoint.x);
			s.getProperties().put("Y1", y1 + newPoint.y);
			s.getProperties().put("Y2", y2 + newPoint.y);
			s.getProperties().put("X3", x3 + newPoint.x);
			s.getProperties().put("Y3", y3 + newPoint.y);
		}
		
		
	}
	
	
	public void resizeShape(Shape shape, Double zoomFactor) {
		
		Double prevX = 0.0;
		Double diffX = 0.0;
		Double prevY = 0.0;
		Double diffY = 0.0;
		
		for (String key : shape.getProperties().keySet()) {
			
			if (key.equals("X1")) {
				
				prevX = shape.getProperties().get(key);
				diffX = (prevX * zoomFactor) - prevX;
				continue;
				
			}
			
			if (key.equals("Y1")) {
				
				prevY = shape.getProperties().get(key);
				diffY = (prevY * zoomFactor) - prevY;
				continue;
				
			}
			
			if (key.equals("X2") || key.equals("X3")) {
				shape.getProperties().put(key, Math.abs((shape.getProperties().get(key) * zoomFactor) - diffX));
			} else if (key.equals("Y2") || key.equals("Y3")) {
				shape.getProperties().put(key, Math.abs((shape.getProperties().get(key) * zoomFactor) - diffY));
			} else {
				shape.getProperties().put(key, shape.getProperties().get(key) * zoomFactor);
			}

		}
				
	}

}
