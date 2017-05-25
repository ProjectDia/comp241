//Nicholas Totman
// ID 1316454
public class LineList{
	private Node head; // the node to end all nodes
	

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
	public string WhatsOn(){
		Node temp = new Node();
		temp = head; 
		string result;
		while( temp != null){
			if (temp.key.equals("On") && temp.next.key.equals("Now")){
				if (!(temp.key.equals("More")) && !(temp.next.key.equals("info"))){
					result += temp.key;
					temp = temp.next;
					}
					temp = temp.next;
			
			}
			return result;
		
		
		}
		
	
	
	
	}
	
	
	
	
	
	
	
	private class Node{ // setting up node 
		String data;
		Node next;
		
		public Node(String setdata){
			data = setdata; // contains the data in a node
		}
		
	}
}
