package raytracer.core.def;

import java.util.List;

import raytracer.core.Hit;
import raytracer.core.Obj;
import raytracer.geom.BBox;
import raytracer.math.Pair;
import raytracer.math.Point;
import raytracer.math.Ray;
import raytracer.math.Vec3;

/**
 * Represents a bounding volume hierarchy acceleration structure
 */
public class BVH extends BVHBase {
	private BBox bbox;
	
	public BVH() {
		// TODO: implement this
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public BBox bbox() {
		// TODO Implement this method
		//throw new UnsupportedOperationException("This method has not yet been implemented.");
		return this.bbox;
	}

	/**
	 * Adds an object to the acceleration structure
	 *
	 * @param prim
	 *            The object to add
	 */
	@Override
	public void add(final Obj prim) {
		// TODO Implement this method
		//throw new UnsupportedOperationException("This method has not yet been implemented.");

	}

	/**
	 * Builds the actual bounding volume hierarchy
	 */
	@Override
	public void buildBVH() {
		// TODO Implement this method
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public Pair<Point, Point> calculateMinMax() {
		// TODO Implement this method
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public int calculateSplitDimension(final Vec3 size) {
		// TODO Implement this method
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public void distributeObjects(final BVHBase a, final BVHBase b,
			final int splitdim, final float splitpos) {
		// TODO Implement this method
		//throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public Hit hit(final Ray ray, final Obj obj, final float tmin, final float tmax) {
		// TODO Implement this method
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}

	@Override
	public List<Obj> getObjects() {
		// TODO Implement this method
		throw new UnsupportedOperationException("This method has not yet been implemented.");
	}
}
