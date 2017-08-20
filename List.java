/**
 * Generic version of a single-link List . Uses {@link ListNode} for list nodes.
 */
public class List<Point> {
	private ListNode<Point> firstNode;
	private ListNode<Point> lastNode;
	private String name; // string like "list" used in printing

	/**
	 * constructor creates empty List with "list" as the name
	 */
	public List() {
		this("list");
	}

	/**
	 * constructor creates an empty List with a name.
	 * @param listName the list name
	 */
	public List(String listName) {
		name = listName;
		firstNode = lastNode = null;
	}

	/**
	 * Inserts an element at the front of the list
	 * @param insertItem the inserted data
	 */
	public void insertAtFront(Point insertItem) {
		ListNode<Point> node = new ListNode<Point>(insertItem);
		
		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = node;
		else { // firstNode refers to new node
			node.nextNode = firstNode;
			firstNode = node;
			// you can replace the two previous lines with this line: firstNode
			// = new ListNode( insertItem, firstNode );
		}
	} // end method insertAtFront

	
	/**
	 * Inserts an element at the end of the list
	 * @param insertItem the inserted item
	 */
	public void insertAtBack(Point insertItem) {
		ListNode<Point> node = new ListNode<Point>(insertItem);
		
		if (isEmpty()) // firstNode and lastNode refer to same Object
			firstNode = lastNode = node;
		else { // lastNode's nextNode refers to new node
			lastNode.nextNode = node;
			lastNode = node;
			// you can replace the two previous lines with this line: lastNode =
			// lastNode.nextNode = new ListNode( insertItem );
		}
	} // end method insertAtBack

	/**
	 * Returns and removes the data from the list head
	 * @return the data contained in the list head
	 * @throws EmptyListException if the list is empty
	 */
	public Point removeFromFront() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		Point removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;

		return removedItem; // return removed node data
	} // end method removeFromFront

	/**
	 * Returns and removes the data from the list tail
	 * @return the data contained in the list tail
	 * @throws EmptyListException if the list is empty
	 */
	public Point removeFromBack() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		Point removedItem = lastNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else // locate new last node
		{
			ListNode<Point> current = firstNode;

			// loop while current node does not refer to lastNode
			while (current.nextNode != lastNode)
				current = current.nextNode;

			lastNode = current; // current is new lastNode
			current.nextNode = null;
		} // end else

		return removedItem; // return removed node data
	} // end method removeFromBack

	/**
	 * Determine whether list is empty
	 * @return true if list is empty
	 */
	public boolean isEmpty() {
		return firstNode == null; // return true if List is empty
	} // end method isEmpty

	/**
	 * Prints the list's contents to System.out
	 */
	public void print() {
		if (isEmpty()) {
			System.out.println("No points found in given rectangle");
			return;
		} // end if

		System.out.println("Points found in given rectangle: ");
		ListNode<Point> current = firstNode;

		// while not at end of list, output current node's data
		while (current != null) {
			System.out.printf("%s ", current.data);
			current = current.nextNode;
		} // end while

		System.out.println("\n");
	} // end method print
	
} // end class List
