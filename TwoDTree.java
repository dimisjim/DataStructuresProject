import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
public class TwoDTree {
	
	protected TreeNode root;
	protected int size=0;
	protected Comparator cmp;
	int xORy = 0;               // μεταβλητή για τον εναλλάξ καθορισμό μονοπατιού αναζήτησης στο δέντρο
	int depth = 0;                  // tree depth
	
	
    static class TreeNode {
        public TreeNode left, parent, right;
        public Point p;
        
        protected TreeNode(Point p) {
            if (p == null) throw new IllegalArgumentException();
            this.p = p;
        }
        
        protected void unlink() {
            p = null;
            parent = left = right = null;
        }
    }
    
	public TwoDTree(){
		this(new DefaultComparator());
	}
	
	public TwoDTree(Comparator cmp){ // construct an empty tree
		
        this.size = 0;
        this.cmp = cmp;
	}
	
	public boolean isEmpty(){ // is the tree empty?
		
		if (size==0) return true;
		else return false;
	}
	
	public int size(){ // number of points in the tree
		
		return size;
	}
	
	public void insert(Point p){ // inserts the point p to the tree
		
        if (p == null) throw new IllegalArgumentException();
        TreeNode n = root;
        TreeNode parent = null;
        int result = 0;
        
        while (n != null) {
            
        	if (xORy%2 == 0){
                result = cmp.compare(p.x, n.p.x);
                parent = n;
                
                if (result<0){
                	n = n.left;
                }
                else{
                	n = n.right;
                }
                
        	}
        	else{
                result = cmp.compare(p.y, n.p.y);
                parent = n;
                
                if (result<0){
                	n = n.left;
                }
                else{
                	n = n.right;
                }
               
        	}

            xORy++;
            depth++;
        }
        
        TreeNode node = new TreeNode(p);
        node.parent = parent;
        //check correctness of tree build up
        //if (parent != null) System.out.println(node.p.toString()+ " " + depth + " " + node.parent.p.toString());
        
        if (result < 0) {
            parent.left = node;
        }
        
        else if (result > 0) {
            parent.right = node;
        }
        
        else {
            root = node;
        }	
        
        depth = 0;
        xORy = 0;
        ++size;
	}
	
	public boolean search(Point p){ // does the tree contain p?
		
        TreeNode n = root;
        while (n != null) {
          
            int result = cmp.compare(p.x, n.p.x) + cmp.compare(p.y, n.p.y);
            if (result == 0) {
                return true;
            }
        	if (xORy%2 == 0){
                result = cmp.compare(p.x, n.p.x);
                
                if (result<0){
                	n = n.left;
                }
                else{
                	n = n.right;
                }
        	}
        	else{
                result = cmp.compare(p.y, n.p.y);
                
                if (result<0){
                	n = n.left;
                }
                else{
                	n = n.right;
                }
        	}

            xORy++;
        }
        xORy=0;
        return false;
	}
	
	public Point nearestNeighbor(Point p){ // point in the tree that is closest to p
		
		Rectangle initialRect = new Rectangle(0,0,100,100);
        TreeNode n = root;
        double mindist = 100;
        while (n != null) {
        	
        	int result;

        	if (xORy%2 == 0){
        		initialRect.xmax = n.p.x;
                result = cmp.compare(mindist, initialRect.distanceTo(n.p));
                
                if (result<0){
                	n = n.left;
                	p = n.p;
                }
                else{
                	n = n.right;
                	p = n.p;
                }
        	}
        	else{
        		initialRect.ymax = n.p.y;
        		result = cmp.compare(mindist, initialRect.distanceTo(n.p));
                
                if (result<0){
                	n = n.left;
                	p = n.p;
                }
                else{
                	n = n.right;
                	p = n.p;
                }
        	}
        	
            xORy++;
        }
        xORy=0;
        return p;
	}
	
	public List<Point> rangeSearch(Rectangle rect){ // Returns a list with the Points that are contained in the rectangle
		
		Rectangle initialRectLeft = new Rectangle(0,0,100,100);
		Rectangle initialRectRight = new Rectangle(0,0,100,100);
		TreeNode n = root;
		List<Point> list = new List<Point>();
		
		if (rect.contains(n.p)){
			list.insertAtFront(n.p);
			//System.out.println("Point inserted:" + n.p.toString());
		}
		
		while (n != null){
			
			if (xORy%2 == 0){
				
				if (n.p.x != 0) { //split axis vertically only if x>0
					initialRectLeft.setXmax(n.p.x); 
					initialRectRight.setXmin(n.p.x);
				}
				//System.out.println("left rect is: " + initialRectLeft.toString() + " loop is inside point:" + n.p.toString());
				//System.out.println("right rect is:" + initialRectRight.toString() + " loop is inside point:" + n.p.toString());
				if (rect.intersects(initialRectLeft)){
					n = n.left;
					if (rect.contains(n.p)){
						list.insertAtFront(n.p);
						//System.out.println("Point inserted:" + n.p.toString());
					}
				}
				else if (rect.intersects(initialRectRight)){
					n = n.right;
					if (rect.contains(n.p)){
						list.insertAtFront(n.p);
						//System.out.println("Point inserted:" + n.p.toString());
					}
				}
				else break;
			}
			else{
				
				if (n.p.y != 0){ //split axis horizontally only if y>0
					initialRectLeft.setYmax(n.p.y);
					initialRectRight.setYmin(n.p.y);
				}
				//System.out.println("left rect is: " + initialRectLeft.toString() + " loop is inside point:" + n.p.toString());
				//System.out.println("right rect is:" + initialRectRight.toString() + " loop is inside point:" + n.p.toString());
				
				if (rect.intersects(initialRectLeft)){
					n = n.left;
					if (rect.contains(n.p)){
						list.insertAtFront(n.p);
						//System.out.println("Point inserted:" + n.p.toString());
					}
				}
				else if (rect.intersects(initialRectRight)){
					n = n.right;
					if (rect.contains(n.p)){
						list.insertAtFront(n.p);
						//System.out.println("Point inserted:" + n.p.toString());
					}
				}
				else break;
			}
			xORy++;
		}
		xORy=0;
		return list;
		
	}
	
	public static void main(String[] args){
		
		//----------------- File Read ---------------------
		
		FileReader fr = null;
		BufferedReader br = null;
		TwoDTree t = new TwoDTree();
		File inFile = new File(args[0]);
		
		try{
			Rectangle rect = new Rectangle(0,0,100,100);
			int i=1;
			int xprev=0, yprev=0;
			String s;
			fr = new FileReader(inFile);
			System.out.println("File cointaining the points: "+args[0]);
			br = new BufferedReader(fr); 
			
			String[] array;
			s = br.readLine();
			array = s.split("\\s");
			int numberofpoints = Integer.parseInt(array[0]);
			System.out.println("Number of Points: "+ numberofpoints);
			s = br.readLine();
			int c = 1;
			
			while (s != null) {
				Point p;
				array = s.split("\\s");
				int x = Integer.parseInt(array[0]);
				int y = Integer.parseInt(array[1]);
				if (x<0 || x>100)  throw new IndexOutOfBoundsException("Error: x out of permissible range [0,100]");
				if (y<0 || y>100)  throw new IndexOutOfBoundsException("Error: y out of permissible range [0,100]");
				
				//incomplete rectangle build up (in which each point is included)
				
				if (i==1){
					p = new Point(x,y,rect);
				}
				if(i%2==0 && i!=1){
					if (x>xprev) rect.xmin = xprev;
					else rect.xmax = xprev;
					p = new Point(x,y,rect);
				}
				else{
					if (y>yprev) rect.ymin = yprev;
					else rect.ymax = yprev;
					p = new Point(x,y,rect);
				}
				
				System.out.print(c++ + ": " + p.toString()+ "\n");
				t.insert(p);
				s = br.readLine();
				i++;
				xprev = x;
				yprev = y;
			}
			br.close();
		}
		catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}

		catch (IOException e) {
	
			e.printStackTrace();
		}
		
		//----------------------- Menu ------------------------
		
		Scanner keyboard = new Scanner(System.in);
		boolean good = false;
		
		while (good != true){

			int ymin, ymax, xmax, xmin, x, y;
			String choice;
			
			System.out.print("\n");
			System.out.println("========== Menu ==========");
			System.out.println("1. For RangeSearch give Rectangle as 'xmin xmax' and 'ymin ymax' in the next line");
			System.out.println("2. For NearestNeigborSearch give Point as 'x y'");
			System.out.println("0. Exit");
			System.out.print("\n");
			System.out.println("Please choose one of the above options (1,2,0):");
			choice = keyboard.nextLine();
			
			if (choice.equals("0")){ //Περίπτωση "exit"
				
				keyboard.close(); //terminate scanner
				System.out.println("Thanks for using TwoDTree!");
				good = true;      //terminate while loop, thus the program
			}
			else if (choice.equals("1")){ //Περίπτωση "Range Search"
				
				System.out.println("Please give Rectangle coordinates:");
				String line1 = keyboard.nextLine();
				String line2 = keyboard.nextLine();
				
				String[] xminxmax = line1.split("\\s");
				xmin = Integer.parseInt(xminxmax[0]);
				xmax = Integer.parseInt(xminxmax[1]);
				if (xmax<xmin)  throw new IndexOutOfBoundsException("Error: xmax < xmin");
				if (xmin<0 || xmin>100 || xmax<0 || xmax>100){
					throw new IndexOutOfBoundsException("Error: xmin or xmax out of permissible range [0,100]");
				}
				
				String[] yminymax = line2.split("\\s");
				ymin = Integer.parseInt(yminymax[0]);
				ymax = Integer.parseInt(yminymax[1]);
				if (ymax<ymin)  throw new IndexOutOfBoundsException("Error: ymax < ymin");
				if (ymin<0 || ymin>100 || ymax<0 || ymax>100){
					throw new IndexOutOfBoundsException("Error: ymin or ymax out of permissible range [0,100]");
				}
				
				Rectangle rect = new Rectangle(xmin, ymin, xmax, ymax);
				System.out.println("Input Rectangle: " + rect.toString());
				List<Point> list = t.rangeSearch(rect);
				list.print();
			}	
			else if (choice.equals("2")){ //Περίπτωση "Nearest Neighbor Search"

				System.out.println("Please give Point coordinates:");
				String line = keyboard.nextLine();
				String[] xy = line.split("\\s");
				x = Integer.parseInt(xy[0]);
				y = Integer.parseInt(xy[1]);
				if (x<0 || x>100)  throw new IndexOutOfBoundsException("Error: x out of permissible range [0,100]");
				if (y<0 || y>100)  throw new IndexOutOfBoundsException("Error: y out of permissible range [0,100]");
				
				Point p = new Point(x,y);
				System.out.println("Input point: "+ p.toString());
				Point z = t.nearestNeighbor(p);
				System.out.println("Nearest neighbor: "+ z.toString());
				System.out.println("Distance: "+ p.distanceTo(z));
			}
			else{ //Περίπτωση Wrong Input 
				
				System.out.println("Wrong menu selection, try again:");
			}
		}	
	}
}