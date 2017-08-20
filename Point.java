
public class Point {
	
	int x;
	int y;
	Rectangle rect;
	
	// constructor with rectangle 
	public Point(int x, int y, Rectangle rect) {
		
		this.x = x;
		this.y = y;
		this.rect = rect;
	}
	
	public Point(int x, int y) {
		
		this.x = x;
		this.y = y;
	}

	public int x() { // return the x-coordinate
		return x;
	}

	public int y() { // return the y-coordinate
		return y;
	}
	
	public double distanceTo(Point z){ // Euclidean distance between two points
		
		return Math.sqrt((z.x-x)^2 + (z.y-y)^2);
	}

	public int squareDistanceTo(Point z){ // square of the Euclidean distance between two points
		
		return (int)Math.sqrt((z.x-x)^2 + (z.y-y)^2)^2;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
