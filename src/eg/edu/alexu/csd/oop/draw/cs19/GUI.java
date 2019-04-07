package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GUI {

	private Factory factory;
	private DrawingEngine drawingEngine;
	private MyGraphics myGraphics;
	private Point startPoint;
	private Point endPoint;
	private Point dump;
	private Shape s = null;
	private boolean triangleFlag = false;
	private boolean thirdPointFlag = false;

	public GUI(Factory factory) {

		this.factory = factory;
		this.startPoint = new Point();
		this.endPoint = new Point();
		this.drawingEngine = new IDrawingEngine();
		

		JFrame frame = new JFrame("Paint");
		JLabel label = new JLabel("Circle");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		frame.setSize(1500, 1000);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(50, 50, 50, 50));
		menuBar.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		menuBar.setBackground(Color.DARK_GRAY);

		// Define and add two drop down menu to the menu bar
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenu editMenu = new JMenu("Edit");
		editMenu.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenu drawMenu = new JMenu("Draw");
		drawMenu.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(drawMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem saveAction = new JMenuItem("Save");
		saveAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				drawingEngine.save("C:\\Users\\ziadh\\Desktop\\save.xml");
				drawingEngine.save("C:\\Users\\ziadh\\Desktop\\save.json");
				
				
			}
		});
		saveAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem loadAction = new JMenuItem("Load");
		loadAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem exitAction = new JMenuItem("Exit");
		exitAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem undoAction = new JMenuItem("Undo");
		undoAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				drawingEngine.undo();
				myGraphics.setShapes(drawingEngine.getShapes());
				myGraphics.repaint();
				
			}
		});
		undoAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem redoAction = new JMenuItem("Redo");
		redoAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				drawingEngine.redo();
				myGraphics.setShapes(drawingEngine.getShapes());
				myGraphics.repaint();
				
			}
		});
		redoAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem circleAction = new JMenuItem("Circle");
		circleAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(circleAction.getText());
			}
		});
		circleAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem ellipseAction = new JMenuItem("Ellipse");
		ellipseAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(ellipseAction.getText());
				triangleFlag = false;
			}
		});
		ellipseAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem rectangleAction = new JMenuItem("Rectangle");
		rectangleAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(rectangleAction.getText());
				triangleFlag = false;
			}
		});
		rectangleAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem squareAction = new JMenuItem("Square");
		squareAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(squareAction.getText());
				triangleFlag = false;
			}
		});
		squareAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem linSegmentAction = new JMenuItem("Line Segment");
		linSegmentAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(linSegmentAction.getText());
				triangleFlag = false;
			}
		});
		linSegmentAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		JMenuItem triangleAction = new JMenuItem("Triangle");
		triangleAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(triangleAction.getText());
				triangleFlag = true;
			}
		});
		triangleAction.setFont(new Font("Century Gothic", Font.PLAIN, 15));

		fileMenu.add(saveAction);
		fileMenu.add(loadAction);
		fileMenu.add(exitAction);
		editMenu.add(undoAction);
		editMenu.add(redoAction);
		drawMenu.add(circleAction);
		drawMenu.add(ellipseAction);
		drawMenu.add(squareAction);
		drawMenu.add(rectangleAction);
		drawMenu.add(linSegmentAction);
		drawMenu.add(triangleAction);
		
		frame.getContentPane().setLayout(null);
		
		JColorChooser colorChooser = new JColorChooser(Color.BLACK);
		frame.getContentPane().add(colorChooser);
		colorChooser.setBounds(1174, 577, 300, 350);
		
		JColorChooser colorChooserFill = new JColorChooser(Color.WHITE);
		frame.getContentPane().add(colorChooserFill);
		colorChooserFill.setBounds(1174, 153, 300, 350);
		
		this.myGraphics = new MyGraphics(new Dimension(1500, 1000), drawingEngine);
		myGraphics.setBounds(0, 0, 1484, 936);
		frame.getContentPane().add(myGraphics);

		// Adding listeners to the mouse pressed, released and dragged.
		myGraphics.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				if (triangleFlag && thirdPointFlag) {
					dump = new Point(e.getX(), e.getY());
					s = factory.createShape(label.getText(), startPoint, endPoint, dump);
				} else if (triangleFlag && !thirdPointFlag) {
					startPoint = new Point(e.getX(), e.getY());
				} else {
					startPoint = new Point(e.getX(), e.getY());
				}

			}

			public void mouseReleased(MouseEvent e) {

				if (triangleFlag && thirdPointFlag) {
					s = factory.createShape(label.getText(), startPoint, endPoint, dump);
					s.setPosition(startPoint);
					s.setColor(colorChooser.getColor());
					s.setFillColor(colorChooserFill.getColor());
					myGraphics.setShape(null);
					if (s != null) {
						drawingEngine.addShape(s);
						myGraphics.setShapes(drawingEngine.getShapes());
					}
					myGraphics.repaint();
					thirdPointFlag = false;
					
				} else if (triangleFlag && !thirdPointFlag) {
					thirdPointFlag = true;
				} else {
					endPoint = new Point(e.getX(), e.getY());
					if (s != null) {
						myGraphics.setShape(null);
						drawingEngine.addShape(s);
						myGraphics.setShapes(drawingEngine.getShapes());
						myGraphics.repaint();
					}
					
				}

			}
		});

		myGraphics.addMouseMotionListener(new MouseAdapter() {

			public void mouseDragged(MouseEvent e) {

				if (!thirdPointFlag) {
					endPoint = new Point(e.getX(), e.getY());
					if (label.getText().equals("Triangle")) {
						s = factory.createShape(label.getText(), startPoint, endPoint, endPoint);

					} else if (label.getText().equals("Line Segment")) {
						s = factory.createShape(label.getText(), startPoint, endPoint, endPoint);
					
					} else {
						s = factory.createShape(label.getText(), startPoint, endPoint, null);
					}
					s.setPosition(startPoint);
					s.setColor(colorChooser.getColor());
					s.setFillColor(colorChooserFill.getColor());
					myGraphics.setShape(s);
					myGraphics.repaint();
				}

			}

		});

		JLabel lblFillColor = new JLabel("Fill Color");
		lblFillColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFillColor.setFont(new Font("Berlin Sans FB", Font.PLAIN, 18));
		lblFillColor.setBounds(1270, 114, 95, 28);
		frame.getContentPane().add(lblFillColor);

		JLabel lblColor = new JLabel("Color");
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblColor.setFont(new Font("Berlin Sans FB", Font.PLAIN, 18));
		lblColor.setBounds(1270, 538, 95, 28);
		frame.getContentPane().add(lblColor);

		frame.setJMenuBar(menuBar);
		frame.setVisible(true);

		/*
		 * Set<String> prop = new HashSet<>(); String[] setArr = new
		 * String[prop.size()]; prop = pm.getProperties(s); setArr =
		 * prop.toArray(setArr); for (int i = 0; i < setArr.length; i++) {
		 * System.out.println(setArr[i]); }
		 */

	}
}
