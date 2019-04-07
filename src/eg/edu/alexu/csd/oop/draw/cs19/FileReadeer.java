package eg.edu.alexu.csd.oop.draw.cs19;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.scene.paint.Color;

public class FileReadeer {

	private Shape shape;
	private Point position;
	private Color fillColor;
	private Color color;
	private boolean flagX = false;
	private boolean flagY = false;
	private boolean flagMap = false;
	private boolean flagShape = false;
	private boolean flagName = false;
	private boolean flagFirst = false;
	private ArrayList<Shape> shapes = new ArrayList<>();

	/**
	 * a function used to read a json file and return a list of the shapes it
	 * contains.
	 * 
	 * @param path
	 *            ... the path of the file to be read.
	 * @return ... it returns a list of shapes read from the json file.
	 */
	public ArrayList<Shape> JSONReader(String path) {

		// We need to provide file path as the parameter:
		// double backquote is to avoid compiler interpret words
		// like \test as \t (ie. as a escape sequence)
		File file = new File(path);
		BufferedReader br = null;
		shapes.clear();
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String st = "";
		try {
			st = br.readLine();
			String regex1 = "([{]{1})([\"])([a-zA-Z]*)([\"])([\\s]{1})([:])([\\s]{1})([\\[]{1})"; // Shape REGEX
			Pattern pattern1 = Pattern.compile(regex1);
			Matcher matcher1 = pattern1.matcher(st);
			if (st.matches(regex1)) {
				if (matcher1.find()) {
					if (matcher1.group(3).equals("ShapeArray")) {

						while (!st.equals("]}")) { /* Whole File */
							// shape = new IShape(); /* New Shape */
							position = new Point();
							flagX = false;
							flagY = false;

							while (!st.equals("},") && !st.equals("")) { /* ONE SHAPE */
								flagShape = true;
								if (!flagName) {
									st = br.readLine();
								}
								String regex2 = "([{]?)([\\s]?)([\"])([a-zA-Z]*)([\"])([\\s]?)([:])([\\s]?)([\"])([\\s]?)([0-9a-zA-Z\\.]*)([\\[]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([\\]]?)([\"])([\\,]?)"; // Compenent
																																																																								// REGEX
								Pattern pattern2 = Pattern.compile(regex2);
								Matcher matcher2 = pattern2.matcher(st);
								if (st.matches(regex2)) {
									if (matcher2.find()) {
										try {
											flagName = false;
											String compenant = matcher2.group(11) + matcher2.group(12)
													+ matcher2.group(13) + matcher2.group(14) + matcher2.group(15)
													+ matcher2.group(16) + matcher2.group(17) + matcher2.group(18)
													+ matcher2.group(19) + matcher2.group(20) + matcher2.group(21)
													+ matcher2.group(22) + matcher2.group(23) + matcher2.group(24)
													+ matcher2.group(25);
											String property = matcher2.group(4);
											if (property.equalsIgnoreCase("className")) {
												shape = (Shape) Class.forName(compenant).newInstance();
											} else if (property.equalsIgnoreCase("x")
													|| property.equalsIgnoreCase("y")) {
												if (property.equalsIgnoreCase("x")) {
													position.x = Integer.parseInt(compenant);
													flagX = true;
												} else {
													position.y = Integer.parseInt(compenant);
													flagY = true;
												}
											} else if (property.equalsIgnoreCase("color")
													|| property.equalsIgnoreCase("fillcolor")) {
												if (property.equalsIgnoreCase("color")) {
													color = Color.valueOf(compenant);
													shape.setColor(color);
												} else {
													fillColor = Color.valueOf(compenant);
													shape.setFillColor(fillColor);
												}
											}
											if (flagX == true && flagY == true) {
												shape.setPosition(position);
												flagX = false;
												flagY = false;
											}
										} catch (Exception e) {

										}
									}
								}
								String regex3 = "([{]{1})([\"])([a-zA-Z]*)([\"])([\\s]?)([:])([\\s]?)([\\[]{1})"; // Map
																													// REGEX
								Pattern pattern3 = Pattern.compile(regex3);
								Matcher matcher3 = pattern3.matcher(st);
								if (st.matches(regex3)) {
									matcher3 = pattern3.matcher(st);
									if (matcher3.find()) {
										if (matcher3.group(3).equals("Map")) {
											Map<String, Double> properties = new HashMap<>();
											flagMap = false;
											while (!st.equals("]} ")) {
												st = br.readLine();
												String regex4 = "([{]?)([\\s]?)([\"])([a-zA-Z0-9]*)([\\s]?)([a-zA-Z0-9]*)([\"])([\\s]?)([:])([\\s]?)([\"])([\\s]?)([0-9a-zA-Z\\.]*)([\"])([\\,]?)"; // MapCompenent
												// REGEX
												Pattern pattern4 = Pattern.compile(regex4);
												if (st.matches(regex4)) {
													Matcher matcher4 = pattern4.matcher(st);
													if (matcher4.find()) {
														try {
															String mapComponent = matcher4.group(13);
															String mapProperty = matcher4.group(4) + " "
																	+ matcher4.group(6);
															properties.put(mapProperty.trim(),
																	Double.parseDouble(mapComponent));
															flagMap = true;
														} catch (Exception e) {

														}
													} else {
														st = br.readLine();
													}
												}
											}
											if (flagMap == true) {
												shape.setProperties(properties);
												flagMap = false;
											}
										}
									}
								}
							}
							if (flagShape == true) {
								shapes.add(shape);
								flagShape = false;
							}
							st = br.readLine();
							if (st.equals("")) {
								flagName = true;
								flagFirst = true;
							} else if (flagFirst) {
								flagName = true;
								flagFirst = false;
							} else {
								flagName = false;
							}
						}
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shapes;
	}

	/**
	 * a function used to read a xml file and return a list of the shapes it
	 * contains.
	 * 
	 * @param path
	 *            ... the path of the file to be read.
	 * @return ... it returns a list of shapes read from the xml file.
	 */
	public ArrayList<Shape> XMLReader(String path) {

		// We need to provide file path as the parameter:
		// double backquote is to avoid compiler interpret words
		// like \test as \t (ie. as a escape sequence)
		File file = new File(path);
		BufferedReader br = null;
		shapes.clear();
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String st;
		try {
			st = br.readLine();
			if (st.equals("<Paint>")) {
				while (!st.equals("</Paint>")) { /* Whole File */

					// shape = new IShape(); /* New Shape */
					position = new Point();
					flagX = false;
					flagY = false;

					st = br.readLine();
					String regex1 = "([\\<])([a-zA-Z]*)([\\s]?)([a-z]*)([\\s]?)([\\=])([\\s]?)([\"])([a-zA-Z0-9\\.]*)([\"])([\\>])"; // Shape
																																		// REGEX
					Pattern pattern1 = Pattern.compile(regex1);
					if (st.matches(regex1)) {
						Matcher matcher1 = pattern1.matcher(st);
						if (matcher1.find()) {
							try {
								String name = matcher1.group(9);
								shape = (Shape) Class.forName(name).newInstance();
							} catch (Exception e) {

							}
						}

						while (!st.equals("</Shape>")) { /* ONE SHAPE */
							flagShape = true;
							st = br.readLine();
							String regex2 = "([\\<])([a-zA-Z]*)([\\s]?)([a-zA-Z]*?)([\\>])([0-9a-zA-Z\\.]*)([\\[]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([a-zA-Z\\.]?)([\\=]?)([0-9]*?)([\\,]?)([\\]]?)([\\<])([\\/])([a-zA-Z]*)([\\s]?)([a-zA-Z]*?)([\\>])"; // Compenants
																																																																										// REGEX
							Pattern pattern2 = Pattern.compile(regex2);
							if (st.matches(regex2)) {
								Matcher matcher2 = pattern2.matcher(st);
								if (matcher2.find()) {
									try {
										String component = matcher2.group(6) + matcher2.group(7) + matcher2.group(8)
												+ matcher2.group(9) + matcher2.group(10) + matcher2.group(11)
												+ matcher2.group(12) + matcher2.group(13) + matcher2.group(14)
												+ matcher2.group(15) + matcher2.group(16) + matcher2.group(17)
												+ matcher2.group(18) + matcher2.group(19) + matcher2.group(20);
										String property = matcher2.group(2);

										if (property.equalsIgnoreCase("x") || property.equalsIgnoreCase("y")) {
											if (property.equalsIgnoreCase("x")) {
												position.x = Integer.parseInt(component);
												flagX = true;
											} else {
												position.y = Integer.parseInt(component);
												flagY = true;
											}
										} else if (property.equalsIgnoreCase("color")
												|| property.equalsIgnoreCase("fillcolor")) {
											if (property.equalsIgnoreCase("color")) {
												color = Color.valueOf(component);
												shape.setColor(color);
											} else {
												fillColor = Color.valueOf(component);
												shape.setFillColor(fillColor);
											}
										}
										if (flagX == true && flagY == true) {
											shape.setPosition(position);
											flagX = false;
											flagY = false;
										}
									} catch (Exception e) {

									}
								}

							} else if (st.equals("<map>")) {
								Map<String, Double> properties = new HashMap<>();
								flagMap = false;
								while (!st.equals("</map>")) {
									st = br.readLine();
									String regex3 = "([\\<])([a-zA-Z0-9]*)([\\s]?)([a-zA-Z0-9]*?)([\\>])([0-9a-zA-Z\\.]*)([\\<])([\\/])([a-zA-Z0-9]*)([\\s]?)([a-zA-Z0-9]*?)([\\>])"; // MapCompenants
									// REGEX
									Pattern pattern3 = Pattern.compile(regex3);
									if (st.matches(regex3)) {
										Matcher matcher3 = pattern3.matcher(st);
										if (matcher3.find()) {
											try {
												String mapComponent = matcher3.group(6);
												String mapProperty = matcher3.group(2) + " " + matcher3.group(4);
												properties.put(mapProperty.trim(), Double.parseDouble(mapComponent));
												flagMap = true;
											} catch (Exception e) {

											}
										}
									}
								}
								if (flagMap == true) {
									shape.setProperties(properties);
									flagMap = false;
								}
							}
						}
						if (flagShape == true) {
							shapes.add(shape);
							flagShape = false;
						}
					}
				}
			} else {
				return null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return shapes;

	}

}
