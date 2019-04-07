package eg.edu.alexu.csd.oop.draw.cs19;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import eg.edu.alexu.csd.oop.draw.Shape;

public class JarReader {

	/**
	 * it's used to open and search inside a jar file for any class that extends
	 * Shape interface.
	 * 
	 * @param jarName
	 *            ... hte jar file path.
	 * @return ... it returns a list with the classes that extends the Shape
	 *         interface.
	 */
	public List<Class> getClassesfromJar(String jarName) {

		ArrayList<Class> l = new ArrayList<>();

		try {
			URL url = new URL("jar:file:" + jarName + "!/");
			URL[] classURL = new URL[] { url };
			ClassLoader classLoad = new URLClassLoader(classURL);

			JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
			JarEntry jarEntry;

			while (true) {

				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().endsWith(".class"))) {

					String t = jarEntry.getName().replaceAll("/", "\\.");
					String[] temp = t.split("\\.");

					System.out.println(t.substring(0, t.length() - 6));

					Class c = classLoad.loadClass(t.substring(0, t.length() - 6));

					System.out.println(c.toString());

					if (c.newInstance() instanceof Shape) {

						l.add(c);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return l;
	}

}
