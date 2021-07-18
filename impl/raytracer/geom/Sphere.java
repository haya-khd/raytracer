package raytracer.geom;

import raytracer.core.*;
import raytracer.core.def.LazyHitTest;
import raytracer.math.*;

public class Sphere extends BBoxedPrimitive {

	private Point center;
	private float radius;
	private Vec3 normal;

	/**
	 * Generates a sphere from given center point and radius.
	 *
	 * @param a Center point of the sphere
	 * @param r Radius
	 */

	public Sphere(Point center, float radius) {
		// TODO Auto-generated constructor stub
		super(BBox.create(new Point(center.x() + radius, center.y() + radius, center.z() + radius),
				new Point(center.x() - radius, center.y() - radius, center.z() - radius)));
		this.center = center;
		this.radius = radius;
		
	}

	@Override
	public Hit hitTest(Ray ray, Obj obj, float tmin, float tmax) {
		// TODO Auto-generated method stub
		return new LazyHitTest(obj) {
			private Point point=null;
			private float parameter;

			@Override
			public Vec2 getUV() {
				// TODO Auto-generated method stub
				return Util.computeSphereUV(getNormal());
			}

			@Override
			public Point getPoint() {
				// TODO Auto-generated method stub
				if (point == null)
					point = ray.eval(getParameter()).add(getNormal().scale(0.0001f));
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
				normal = ray.base().sub(center).normalized();
				return normal;
			}

			@Override
			protected boolean calculateHit() {
				// TODO Auto-generated method stub

				Vec3 cal = ray.base().sub(center);
				float c1 = cal.dot(cal);

				float b = 2 * (ray.dir().dot(cal));

				float c = c1 - radius * radius;

				float Diskriminante = b * b - 4 * c;
				if (Diskriminante < 0)
					return false;

				float lambda1 = (float) (0.5 * (-b - Math.sqrt(Diskriminante)));
				float lambda2 = (float) (0.5 * (-b + Math.sqrt(Diskriminante)));

				if (lambda1 < 0.0 || lambda2 < 0.0) {

					return false;
				}

				if (lambda1 > lambda2) {
					if (lambda2 < tmin || lambda2 > tmax) {
						return false;
					}
					parameter = lambda2;
					return true;
				} else {
					if (lambda1 < tmin || lambda1 > tmax) {
						return false;
					}
					parameter = lambda1;
					return true;
				}

			}

		};
	}

	@Override
	public boolean equals(final Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Sphere) {
			final Sphere sobj = (Sphere) obj;
			return sobj.center.equals(center) && sobj.radius == radius;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return center.hashCode();
	}

}
