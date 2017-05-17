import java.net.*;
import java.io.*;

public class ClientApp{
	public static void main(String[] args) throws IOException{
		Socket socc = null;
		PrintWriter out = null;
		BufferedReader in = null;
		//if(args.length != 2){
			//System.out.println("please specify a port followed by your channel ");
			//return;
		//}
		int portNumber = Integer.parseInt(args[0]);

		try{
			socc = new Socket("f.l0.nz",241);
			out = new PrintWriter(socc.getOutputStream(), true); // printwriter for output
			in = new BufferedReader(new InputStreamReader(socc.getInputStream())); // stream for reading from server
		} catch (UnknownHostException e) {
            System.err.println("Unknown host: " + args[0]); // no server
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO Exception for host " + args[0]);
            System.exit(1);
        }

        String fromServer;
        String fromUser;
        out.println(args[1]);
        while((fromServer = in.readLine()) != null){ //while there is input from the server
        	System.out.println(fromServer);
        }

        out.close();
        in.close();
        socc.close();
	}
}
