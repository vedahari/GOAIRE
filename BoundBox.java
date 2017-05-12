package OnAireServlets;

public class BoundBox {
    private final Double xmin, ymin;   // minimum x- and y-coordinates
    private final Double xmax, ymax;   // maximum x- and y-coordinates

    // construct the bounding box [xmin, xmax] x [ymin, ymax]
    public BoundBox(Double xmin, Double ymin, Double xmax, Double ymax) {
        if (xmax < xmin || ymax < ymin) {
        	//System.err.println("("+xmin+","+ymin+","+xmax+","+ymax);
            //throw new IllegalArgumentException("Invalid box");
        	if (xmax<xmin){
        		Double temp = 0.0;
        		temp = xmax;
        		xmax = xmin;
        		xmin = temp;
        	}
        	if (ymax<ymin){
        		Double temp = 0.0;
        		temp = ymax;
        		ymax = ymin;
        		ymin = temp;
        	}
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }
    // accessor methods for 4 coordinates
    public Double xmin() { return xmin; }
    public Double ymin() { return ymin; }
    public Double xmax() { return xmax; }
    public Double ymax() { return ymax; }

    // width and height of rectangle
    public Double width()  { return xmax - xmin; }
    public Double height() { return ymax - ymin; }

    // does this bounding box intersect that one?
    public boolean intersects(BoundBox that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
            && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    // distance from p to closest point on this bounding box   
    public Double distanceTo(Point p) {
    	//If p is in box, then distance is zero.
    	if (p.x()>=xmin && p.x()<=xmax && p.y()>=ymin && p.y()<=ymax){
    		return 0.0;
    	} 	
    	Double lat1, lon1;
    	if (Math.abs(p.x()-xmax) <= Math.abs(p.x()-xmin)){
    		lat1 = xmax;
    	}
    	else lat1 = xmin;
    	
    	if (Math.abs(p.y()-ymax) <= Math.abs(p.y()-ymin)){
    		lon1 = ymax;
    	}
    	else lon1 = ymin; 	
    	
    	return DistanceCalculator.computeDistance(lat1, lon1, p.x(), p.y());
        //return Math.sqrt(this.distanceSquaredTo(p));
    }    

    // distance squared from p to closest point on this bounding box
    public Double distanceSquaredTo(Point p) {
        /*Double dx = 0.0, dy = 0.0;
        if      (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if      (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx*dx + dy*dy;*/
    	Double dist = distanceTo(p);    	
    	return dist*dist; 
    }    

    // does this bounding box contain p?
    public boolean contains(Point p) {
        return (p.x() >= xmin) && (p.x() <= xmax)
            && (p.y() >= ymin) && (p.y() <= ymax);
    }

    // are the two bounding boxes equal?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        BoundBox that = (BoundBox) y;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    // return a string representation of this bounding box
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
}


