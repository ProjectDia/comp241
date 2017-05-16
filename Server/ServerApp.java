import java.io.*;
import java.net.*;
class ServerApp{
	public static void main(String[] args) throws IOException{
		int portNumber = Integer.parseInt(args[0]);
		ServerSocket serverSock = null;	//creates server socket
		try {
		    serverSock = new ServerSocket(portNumber);
		} catch (IOException e){
			System.err.println("could not open on port: " + portNumber);
			System.exit(0);
		}
		System.out.println("Server started");
		while(true){
			Socket clientSock = null; 	//creates a socket for a client
			try {
		    	clientSock = serverSock.accept();
			} catch (IOException e){
				System.err.println("Cannot accept this sock");
				System.exit(0);
			}
			InetAddress i = clientSock.getInetAddress(); // get address of socket

			PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

			String fromClient;
			while((fromClient = in.readLine()) == null){} //continue until the server receives input 
			System.out.println("A client asked for data about channel: " + fromClient);
			out.println("You have asked for channel data for channel: " + fromClient); 

			out.close();      
    		in.close();                   // disconnect the client
    		clientSock.close();
		}
		
	}
}