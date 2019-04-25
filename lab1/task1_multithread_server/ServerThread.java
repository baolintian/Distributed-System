import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
    
    DatagramPacket request = null;
	
    public ServerThread(DatagramPacket request) {
        this.request = request;
    }

    public void run(){
		
		String put = new String(request.getData());
		put = put.substring(0, request.getLength());
		System.out.println("Now we are in thread and the content is "+put);
			
		
    }
}