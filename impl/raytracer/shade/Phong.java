package raytracer.shade;

import java.util.Iterator;

import raytracer.core.Hit;
import raytracer.core.LightSource;
import raytracer.core.Shader;
import raytracer.core.Trace;
import raytracer.math.Color;
import raytracer.math.*;

public class Phong implements Shader {

	private final Shader inner;
	private final Color ambient;
	private final float diffuse;
	private final float specular;
	private final float shininess;

	/**
	 * Generates a Phong (http://en.wikipedia.org/wiki/Phong_shading) shader.
	 *
	 * @param inner     The base shader of this Phong shader
	 * @param ambient   Color of the ambient light emitted to the scene
	 * @param diffuse   The ratio of reflection of the diffuse term of incoming
	 *                  light
	 * @param specular  The ratio of reflection of the specular term of incoming
	 *                  light
	 * @param shininess A shininess constant defining this material. The larger the
	 *                  value the more "mirror-like" is the structure
	 * @throws IllegalArgumentException If the diffuse, specular or shininess parts
	 *                                  are not valid numbers (must be positive,
	 *                                  representing a number, not infinity). In
	 *                                  addition to that the shader and the ambient
	 *                                  color must be instance values (not null).
	 */

	public Phong(final Shader inner, final Color ambient, final float diffuse, final float specular,
			final float shininess) {
		// TODO Auto-generated constructor stub
		if (inner == null || ambient == null || diffuse < 0 || specular < 0 || shininess < 0
				|| Float.isInfinite(diffuse) || Float.isInfinite(specular) || Float.isInfinite(shininess)
				|| Float.isNaN(diffuse) || Float.isNaN(specular) || Float.isNaN(shininess)) {
			throw new IllegalArgumentException();
		}

		this.inner = inner;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
	}

	@Override
	public Color shade(Hit hit, Trace trace) {
//		// TODO Auto-generated method stub

		Vec3 v;
		LightSource c_licht;

		// Diffuse

		Color I_diffuse;
		Color I_d = null;

		float v_max;
		Color C_sub_diffuse = inner.shade(hit, trace);
		Vec3 n_diffuse = hit.getNormal();

		// Specular
		Color I_specular;
		Color I_s = null;
		float rv_max;
		Vec3 r_specular = trace.getRay().reflect(hit.getPoint(), hit.getNormal()).dir();

		for (Iterator<LightSource> iterator = trace.getScene().getLightSources().iterator(); iterator.hasNext();) {
			c_licht = iterator.next();
			v = (c_licht.getLocation()).sub(hit.getPoint()).normalized();
			v_max = Float.max(v.dot(n_diffuse), (float) 0.0);
			rv_max = (float) Math.pow(Float.max(v.dot(r_specular), (float) 0.0), shininess);
			I_diffuse = (C_sub_diffuse.mul(c_licht.getColor()).scale(diffuse * v_max));
			I_specular = ((c_licht.getColor()).scale(specular * rv_max));

			if (I_d == null && I_s == null) {
				I_d = I_diffuse;
				I_s = I_specular;
			} else {
				I_d = I_d.add(I_diffuse);
				I_s = I_s.add(I_specular);
			}

		}

		if (I_s == null && I_d != null) {
			return ambient.add(I_d);
		}

		if (I_s != null && I_d == null) {
			return ambient.add(I_s);
		}
		if (I_s == null && I_d == null) {
			return Color.BLACK;

		}
		return ambient.add(I_s.add(I_d));

	}
}
