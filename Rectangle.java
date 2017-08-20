
public class Rectangle {
	
	int xmin, xmax, ymin, ymax;
	
	public Rectangle(int xmin, int ymin, int xmax, int ymax){ // construct the rectangle [xmin, ymin] x [xmax, ymax]
		
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	
	public void setXmin(int xmin) {
		this.xmin = xmin;
	}

	public void setXmax(int xmax) {
		this.xmax = xmax;
	}

	public void setYmin(int ymin) {
		this.ymin = ymin;
	}

	public void setYmax(int ymax) {
		this.ymax = ymax;
	}

	public int xmin(){ // minimum x-coordinate of rectangle
		
		return xmin;
	}
	
	public int ymin(){ // minimum y-coordinate of rectangle
		
		return ymin;
	}
	
	public int xmax(){ // maximum x-coordinate of rectangle
		
		return xmax;
	}
	
	public int ymax(){ // maximum y-coordinate of rectangle
		
		return ymax;
	}
	
	public boolean contains(Point p){ //does p belong to the rectangle (συμπεριλαμβανομένων και των πλευρών)?
		
		if (p.x <= xmax && p.x >= xmin){
			if (p.y <= ymax && p.y >= ymin){
				return true;	
			}
			else return false;
			
		}
		else return false;	
		
	}
	
	public boolean intersects(Rectangle that){ // do the two rectangles intersect?
		
		if (that.xmax >= xmax){
			if (that.xmin <= xmax){
				if (that.ymax >= ymax){
					if (that.ymin <= ymax){
						return true; //περίπτωση 1
					} 
					else return false;
				}
				else if (that.ymax >= ymin) {
					return true; //περίπτωση 2
				}
				else return false;
			}
			else return false;
		}
		else if (that.xmax >= xmin){
			if (that.ymax >= ymax){
				if (that.ymin <= ymax){
					return true; //περίπτωση 3
				}
				else return false;
			}
			else if (that.ymax >= ymin) {
				return true; //περίπτωση 4
			}
			else return false;
		}
		else return false;
	
	}
	
	public double distanceTo(Point p){ // Euclidean distance from p to closest point in rectangle

		double mindist=100.0;
		if (Math.sqrt((p.x-xmax)^2 + (p.y-ymax)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymax)^2);
		}
		else if (Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2);
		}
		else if (Math.sqrt((p.x-xmin)^2 + (p.y-ymax)^2)<mindist){
			mindist = Math.sqrt((p.x-xmin)^2 + (p.y-ymax)^2);
		}
		else if (Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2);
		}
		if (p.y>ymin && p.y<ymax){
			if (p.x<xmin){
				return xmin-p.x; 
			}
			else{
				return p.x-xmax;
			}
			
		}
		else if (p.x>xmin && p.x<xmax){
			if (p.y<ymin){
				return ymin-p.y;
			}
			else{
				return p.y-ymax;
			}
			
		}
		else{
			return mindist;
		}
	}
	
	public double squareDistanceTo(Point p){ // square of Euclidean distance from p to closest point in rectangle
		
		double mindist=100;
		if (Math.sqrt((p.x-xmax)^2 + (p.y-ymax)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymax)^2);
		}
		else if (Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2);
		}
		else if (Math.sqrt((p.x-xmin)^2 + (p.y-ymax)^2)<mindist){
			mindist = Math.sqrt((p.x-xmin)^2 + (p.y-ymax)^2);
		}
		else if (Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2)<mindist){
			mindist = Math.sqrt((p.x-xmax)^2 + (p.y-ymin)^2);
		}
		if (p.y>ymin && p.y<ymax){
			if (p.x<xmin){
				return (xmin-p.x)^2; 
			}
			else{
				return (p.x-xmax)^2;
			}
			
		}
		else if (p.x>xmin && p.x<xmax){
			if (p.y<ymin){
				return (ymin-p.y)^2;
			}
			else{
				return (p.y-ymax)^2;
			}
			
		}
		else{
			
			return mindist*mindist;
		}
	
	}
	
	public String toString(){ // string representation: [xmin, xmax] x [ymin, ymax]
		
		return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";	
	}

}
