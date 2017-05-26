//Nicholas Totman
// ID 1316454
public class LineList{
	private Node head; // the node to end all nodes
	
	public void add(String line){ // called in filesort  the svanned line is passed 
		Node temp = new Node(line); // temp node containg the line 
		temp.next = head; 
		head = temp; // temp node becomes the head 
	}
	public String whatsOn(){
	String show;
	show = head.getWhatsOn();
	return show;
	}
	
		
	private class Node{ // setting up node 
		String data;
		Node next;
		
		public Node(String setdata){
			data = setdata; // contains the data in a node
		}
		
		public String getWhatsOn(){
		//Node temp = new Node();
		Node temp = head; 
		Boolean store= false;
		String result = null;
		while((!(temp.data.equals("more")) && !(temp.next.data.equals("info")))){
			if ((temp.data.equals("on")) && (temp.next.data.equals("now"))){
				store = true;
				System.out.println("true");
			}
			if (store == true){
			result += temp.data;
			}
			temp = temp.next;
		
		
		}
		
	return result;
	
	
	}
		
	}
}

