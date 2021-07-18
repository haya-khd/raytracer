package raytracer.geom;

import java.util.Objects;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.core.def.LazyHitTest;
import raytracer.math.*;

public class Plane extends BBoxedPrimitive {

	private final Vec3 n;
	private final Point supp;

	/**
	 * Generates a plane from a given point (that is located on the plane) and a
	 * normal vector of this plane.
	 *
	 * @param n    The normal vector defining this plane
	 * @param supp An arbitrary point that lies on the plane
	 * @return The new plane
	 */

	public Plane(final Vec3 n, final Point supp) {
		// TODO Auto-generated constructor stub
		super();
		this.n = n.normalized();
		this.supp = supp;

	}

	public Plane(final Point a, final Point b, final Point c) {
		// TODO Auto-generated constructor stub
		super();
		this.supp = a;
		Vec3 u = b.sub(a);
		Vec3 v = c.sub(a);
		this.n = u.cross(v).normalized();

	}

	@Override
	public Hit hitTest(Ray ray, Obj obj, float tmin, float tmax) {
		// TODO Auto-generated method stub
		return new LazyHitTest(obj) {
			private Point point = null;
			private float parameter;

			@Override
			public Vec2 getUV() {
				// TODO Auto-generated method stub
				return Util.computePlaneUV(n, supp, getPoint());

			}

			@Override
			public Point getPoint() {
				// TODO Auto-generated method stub
				if (Objects.isNull(point))
					point = ray.eval(parameter).add(n.scale(0.0001f));

				return point;

			}

			@Override
			public float getParameter() {
				// TODO Auto-generated method stub
				return parameter;
			}

			@Override
			public Vec3 getNormal() {
				// TODO Auto-generated method stub
				return n;
			}

			@Override
			protected boolean calculateHit() {
				// TODO Auto-generated method stub
				Vec3 Vs = ray.dir();
				Point Ps = ray.base();

				float d = supp.dot(n);
				float Vs_Ne_2 = Vs.dot(n);
				if (Vs_Ne_2 == 0.0)
					return false;

				float Ps_Ne_2 = Ps.dot(n);
				float Lambda2 = (d - Ps_Ne_2) / Vs_Ne_2;
				if (Lambda2 < 0.0 || Lambda2 < tmin || Lambda2 > tmax) {
					return false;
				}
				parameter = Lambda2;

				return true;
			}
		};
	}

	@Override
	public boolean equals(final Object obj) {
		// TODO Auto-generated method stub

		if (obj instanceof Plane) {
			final Plane pobj = (Plane) obj;
			return pobj.supp.equals(supp) && pobj.n.equals(n);

		}
		return false;

	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return n.hashCode() ^ supp.hashCode();
	}

}
