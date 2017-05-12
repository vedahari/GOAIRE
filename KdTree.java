package OnAireServlets;
import java.util.ArrayList;


/*
 * Kd-Tree to find the nearest neighbors.
 */

public class KdTree {

	//Direction of the kd tree.
	private enum Direction{
		LeftRight, TopBottom;
		public Direction toggle(){
			if (this.equals(Direction.LeftRight)){
				return Direction.TopBottom;
			}
			else{
				return Direction.LeftRight;
			}
		}
	}

	private class KdNode{
		private Point pt;
		private KdNode rightTop;
		private KdNode leftBottom;
		private BoundBox box;
		public KdNode(Point p){
			this.pt = p;
			this.rightTop = null;
			this.leftBottom = null;
			this.box = null;
		}		
	}

	private KdNode root;
	private int size;

	public KdTree(){
		this.root=null;
		this.size = 0;
	}
	public boolean isEmpty(){
		return root==null;
	}
	public int size() {
		return size;
	}

	/*
	 * add the point p to the set (if it is not already in the set)
	 */
	public void insert(Point p) {
		if (isEmpty()) {
			root = new KdNode(p);
			root.box = new BoundBox(Math.floor(p.x()), Math.floor(p.y()), Math.ceil(p.x()), Math.ceil(p.y()));
			this.size++;
			return;
		}
		root = put(root, p, Direction.LeftRight);
	}

	private KdNode put(KdNode x, Point p, Direction direction) {
		if (x == null) {
			this.size++;
			return new KdNode(p);
		}
		if (x.pt.equals(p)) {
			return x;
		}
		int cmp = compare(p, x.pt, direction);
		Direction nextDirection = direction.toggle();
		if (cmp < 0) {
			x.leftBottom = put(x.leftBottom, p, nextDirection);
			if (x.leftBottom.box == null) {
				if (direction == Direction.LeftRight) {
					x.leftBottom.box = new BoundBox(x.box.xmin(), x.box.ymin(),
							x.pt.x(), x.box.ymax());
				} else {
					x.leftBottom.box = new BoundBox(x.box.xmin(), x.box.ymin(),
							x.box.xmax(), x.pt.y());
				}
			}
		} else {
			x.rightTop = put(x.rightTop, p, nextDirection);
			if (x.rightTop.box == null) {
				if (direction == Direction.LeftRight) {
					x.rightTop.box = new BoundBox(x.pt.x(), x.box.ymin(),
							x.box.xmax(), x.box.ymax());
				} else {
					x.rightTop.box = new BoundBox(x.box.xmin(), x.pt.y(),
							x.box.xmax(), x.box.ymax());
				}
			}
		}
		return x;
	}

	private int compare(Point p, Point q, Direction direction) {
		if (direction == Direction.LeftRight) {
			return Double.compare(p.x(), q.x());
		} else {
			return Double.compare(p.y(), q.y());
		}
	}

	/*
	 * does the set contain the point p?
	 */
	public boolean contains(Point p) {
		return contains(root, p, Direction.LeftRight);
	}

	private boolean contains(KdNode x, Point p, Direction Direction) {
		if (x == null) {
			return false;
		}
		if (x.pt.equals(p)) {
			return true;
		}
		int cmp = compare(p, x.pt, Direction);
		Direction nextDirection = Direction.toggle();
		if (cmp < 0) {
			return contains(x.leftBottom, p, nextDirection);
		} else {
			return contains(x.rightTop, p, nextDirection);
		}
	}  

	/*
	 * return all points in the set that are inside the rectangle
	 */
	public Iterable<Point> range_points(BoundBox box) {
		ArrayList<Point> stopPoints = new ArrayList<Point>();

		if (!isEmpty()) {
			findPoints(stopPoints, box, root);
		}
		return stopPoints;
	}


	private void findPoints(ArrayList<Point> stopPoints, BoundBox box, KdNode x) {
		if (!box.intersects(x.box)) {
			return;
		}
		if (box.contains(x.pt)) {
			stopPoints.add(x.pt);
		}
		if (x.leftBottom != null) {
			findPoints(stopPoints, box, x.leftBottom);
		}
		if (x.rightTop != null) {
			findPoints(stopPoints, box, x.rightTop);
		}
	}

	public Iterable<Integer> range(BoundBox box){
		ArrayList<Integer>stopTimeList = new ArrayList<Integer>();
		if (!isEmpty()){
			findStopPoints(stopTimeList,box,root);
		}
		return stopTimeList;
	}

	private void findStopPoints(ArrayList<Integer> stopTimeList, BoundBox box, KdNode x) {
		if (!box.intersects(x.box)) {
			return;
		}
		if (box.contains(x.pt)) {
			stopTimeList.add(x.pt.getStopTime());
		}
		if (x.leftBottom != null) {
			findStopPoints(stopTimeList, box, x.leftBottom);
		}
		if (x.rightTop != null) {
			findStopPoints(stopTimeList, box, x.rightTop);
		}

	}

}   
	

