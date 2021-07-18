package raytracer.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import raytracer.core.def.Accelerator;
import raytracer.core.def.StandardObj;
import raytracer.geom.GeomFactory;
import raytracer.geom.Primitive;
import raytracer.math.Point;
import raytracer.math.Vec3;

/**
 * Represents a model file reader for the OBJ format
 */
public class OBJReader {

	/**
	 * Reads an OBJ file and uses the given shader for all triangles. While loading
	 * the triangles they are inserted into the given acceleration structure
	 * accelerator.
	 *
	 * @param filename    The file to read the data from
	 * @param accelerator The target acceleration structure
	 * @param shader      The shader which is used by all triangles
	 * @param scale       The scale factor which is responsible for scaling the
	 *                    model
	 * @param translate   A vector representing the translation coordinate with
	 *                    which all coordinates have to be translated
	 * @throws IllegalArgumentException If the filename is null or the empty string,
	 *                                  the accelerator is null, the shader is null,
	 *                                  the translate vector is null, the translate
	 *                                  vector is not finite or scale does not
	 *                                  represent a legal (finite) floating point
	 *                                  number
	 */
	public static void read(final String filename, final Accelerator accelerator, final Shader shader,
			final float scale, final Vec3 translate) throws FileNotFoundException {
		read(new BufferedInputStream(new FileInputStream(filename)), accelerator, shader, scale, translate);
	}

	/**
	 * Reads an OBJ file and uses the given shader for all triangles. While loading
	 * the triangles they are inserted into the given acceleration structure
	 * accelerator.
	 *
	 * @param in          The InputStream of the data to be read.
	 * @param accelerator The target acceleration structure
	 * @param shader      The shader which is used by all triangles
	 * @param scale       The scale factor which is responsible for scaling the
	 *                    model
	 * @param translate   A vector representing the translation coordinate with
	 *                    which all coordinates have to be translated
	 * @throws IllegalArgumentException If the InputStream is null, the accelerator
	 *                                  is null, the shader is null, the translate
	 *                                  vector is null, the translate vector is not
	 *                                  finite or scale does not represent a legal
	 *                                  (finite) floating point number
	 */
	public static void read(final InputStream in, final Accelerator accelerator, final Shader shader, final float scale,
			final Vec3 translate) throws FileNotFoundException {
		// TODO Implement this method
		if (in == null || accelerator == null || translate == null || shader == null || translate.isInfinity()
				|| Float.isNaN(scale)) {
			throw new IllegalArgumentException();
		}

		Primitive triangle;
		Scanner line = new Scanner(in);
		line.useLocale(Locale.ENGLISH);

		ArrayList<Point> vertex = new ArrayList<Point>();
		ArrayList<Integer> face = new ArrayList<Integer>();
		String s;
		char ch;
		while (line.hasNext()) {
			s = line.next();
			ch = s.charAt(0);

			switch (ch) {
			case 'v': {
				vertex.add(new Point(Float.parseFloat(String.valueOf(line.next())),
						Float.parseFloat(String.valueOf(line.next())), Float.parseFloat(String.valueOf(line.next())))
								.add(translate.scale(scale)));
				break;
			}
			case 'f': {
				face.add(Integer.parseInt(String.valueOf(line.next())));
				face.add(Integer.parseInt(String.valueOf(line.next())));
				face.add(Integer.parseInt(String.valueOf(line.next())));
				break;
			}
			default:

			}
		}

		line.close();

		Point v1, v2, v3;
		for (int i = 0; i < face.size(); i += 3) {

			v1 = vertex.get(face.get(i) - 1);
			v2 = vertex.get(face.get(i + 1) - 1);
			v3 = vertex.get(face.get(i + 2) - 1);

			triangle = GeomFactory.createTriangle(v1, v2, v3);
			accelerator.add(new StandardObj(triangle, shader));
		}

	}
}
