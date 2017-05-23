//Nicholas Totman
// ID 1316454
public class LineList{
	private Node head; // the node to end all nodes
	private int count = 0; // comparison counter

	public int getCount(){ // called in filesort returns the number of comparisons 
		return count;
	}

	public void add(String line){ // called in filesort  the svanned line is passed 
		Node temp = new Node(line); // temp node containg the line 
		temp.next = head; 
		head = temp; // temp node becomes the head 
	}
	
	public void addNode(Node toAdd){ 
		toAdd.next = head;
		head = toAdd; 
	}
	
	
	
	
	
	
	
	private class Node{ // setting up node 
		String data;
		Node next;
		
		public Node(String setdata){
			data = setdata; // contains the data in a node
		}
	}
}
