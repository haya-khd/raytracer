package raytracer.shade;

import raytracer.core.Hit;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.math.Color;

public class CheckerBoard implements Shader {

	private final Shader a;
	private final Shader b;
	private final float scale;


	/**
	 * Generates a checkerboard structured shader. Two shaders are "placed"
	 * alternately on the whole structure.
	 *
	 * @param a     The first shader structure (x + y even)
	 * @param b     The second shader structure (x + y odd)
	 * @param scale The size of the tiles
	 * @throws IllegalArgumentException      If the scale value is not a valid
	 *                                       number (must be positive, representing
	 *                                       a number, not infinity). In addition to
	 *                                       that the two shaders must be instance
	 *                                       values (not null).
	 * @throws UnsupportedOperationException If the scale factor is equal to zero
	 *                                       (with respect to epsilon)
	 */

	public CheckerBoard(final Shader a, final Shader b, final float scale) {
		// TODO Auto-generated constructor stub
		if (scale < 0 || Float.isInfinite(scale) || a == null || b == null || Float.isNaN(scale)) {
			throw new IllegalArgumentException();
		}
		if (scale == 0)
			throw new UnsupportedOperationException();

		this.a = a;
		this.b = b;
		this.scale = scale;

	}

	@Override
	public Color shade(Hit hit, Trace trace) {
		// TODO Auto-generated method stub


		float x = (float) (Math.floor((hit.getUV().x() / scale)) + Math.floor(hit.getUV().y() / scale));

		if (x % 2 == 0) {
			return a.shade(hit, trace);

		} else
			return this.b.shade(hit, trace);
	}

}
