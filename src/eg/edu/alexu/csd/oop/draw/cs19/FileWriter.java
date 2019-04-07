package eg.edu.alexu.csd.oop.draw.cs19;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;

public class FileWriter {

	/**
	 * it's a function that writes all the shapes to a file of type json.
	 * 
	 * @param path
	 *            ... the path of the file to bew written.
	 * @param shapes
	 *            ... the array of shapes to be written on the json file.
	 */
	public void JSONWriter(String path, Shape[] shapes) {

		File file = new File(path);
		BufferedWriter bw = null;
		try {

			file.createNewFile();

			bw = new BufferedWriter(new java.io.FileWriter(file));

			bw.write("{\"ShapeArray\" : [\n");

			for (int i = 0; i < shapes.length; i++) {

				bw.write("{ \"className\" : \" " + shapes[i].getClass().toString().split(" ")[1] + "\",\n");

				if ((Point) shapes[i].getPosition() == null) {
					bw.write("\"x\" : \"" + "null" + "\",\n");
					bw.write("\"y\" : \"" + "null" + "\",\n");
				} else {
					bw.write("\"x\" : \"" + ((Point) shapes[i].getPosition()).x + "\",\n");
					bw.write("\"y\" : \"" + ((Point) shapes[i].getPosition()).y + "\",\n");
				}

				// Iterator it = shapes[i].getProperties().entrySet().iterator();
				bw.write("{\"Map\" : [\n");
				bw.write("{ ");
				Map<String, Double> map = shapes[i].getProperties();
				// System.out.println(shapes[i].getProperties().size() +
				// "AHOOOOOOOOOOOOOOOOOO");
				if (map == null) {
					bw.write("} \n");
					bw.write("]} \n");
				} else {
					for (Entry<String, Double> entry : map.entrySet()) {
						// System.out.println(entry.getKey() + "/" + entry.getValue());
						bw.write("\"" + entry.getKey() + "\" : \"" + entry.getValue() + "\",\n");
					}
					bw.write("} \n");
					bw.write("]} \n");
				}

				/*
				 * while (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next(); fw.write("\""
				 * + pair.getKey() + "\" : \"" + pair.getValue() + "\",\n"); it.remove(); //
				 * avoids a ConcurrentModificationException }
				 */

				if (shapes[i].getColor() == null) {
					bw.write("\"color\" : \"" + "null" + "\",\n");
				} else {
					bw.write("\"color\" : \"" + shapes[i].getColor() + "\",\n");
				}

				if (shapes[i].getFillColor() == null) {
					bw.write("\"fillcolor\" : \"" + "null" + "\"\n");
				} else {
					bw.write("\"fillcolor\" : \"" + shapes[i].getFillColor() + "\"\n");
				}
				bw.write("},\n\n");

			}

			bw.write("]}");
			bw.flush();
			bw.close();

		} catch (Exception e) {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * it's a function that writes all the shapes to a file of type json.
	 * 
	 * @param path
	 *            ... the path of the file to bew written.
	 * @param shapes
	 *            ... the array of shapes to be written on the json file.
	 */
	public void XMLWriter(String path, Shape[] shapes) {

		File file = new File(path);
		BufferedWriter bw = null;
		try {

			file.createNewFile();

			bw = new BufferedWriter(new java.io.FileWriter(file));

			bw.write("<Paint>\n");

			for (int i = 0; i < shapes.length; i++) {

				bw.write("<Shape ");

				bw.write("id = " + "\"" + shapes[i].getClass().toString().split(" ")[1] + "\">\n");

				if ((Point) shapes[i].getPosition() == null) {
					bw.write("<x>" + "null" + "</x>\n");
					bw.write("<y>" + "null" + "</y>\n");
				} else {
					bw.write("<x>" + ((Point) shapes[i].getPosition()).x + "</x>\n");
					bw.write("<y>" + ((Point) shapes[i].getPosition()).y + "</y>\n");
				}

				bw.write("<map>\n");
				Map<String, Double> map = shapes[i].getProperties();

				if (map == null) {
					bw.write("</map>\n");
				} else {
					for (Entry<String, Double> entry : map.entrySet()) {
						// System.out.println(entry.getKey() + "/" + entry.getValue());
						bw.write("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">\n");
					}
					/*
					 * Iterator it = shapes[i].getProperties().entrySet().iterator(); while
					 * (it.hasNext()) { Map.Entry pair = (Map.Entry) it.next(); fw.write("<" +
					 * pair.getKey() + ">" + pair.getValue() + "</" + pair.getKey() + ">\n");
					 * it.remove(); // avoids a ConcurrentModificationException }
					 */

					bw.write("</map>\n");
				}

				if (shapes[i].getColor() == null) {
					bw.write("<color>" + "null" + "</color>\n");
				} else {
					bw.write("<color>" + shapes[i].getColor() + "</color>\n");
				}

				if (shapes[i].getFillColor() == null) {
					bw.write("<fillcolor>" + "null" + "</fillcolor>\n");
				} else {
					bw.write("<fillcolor>" + shapes[i].getFillColor() + "</fillcolor>\n");
				}

				bw.write("</Shape>\n");

			}

			bw.write("</Paint>");
			bw.flush();
			bw.close();

		} catch (Exception e) {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
