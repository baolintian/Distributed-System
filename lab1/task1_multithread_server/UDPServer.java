import java.net.*;
import java.io.*;

public class UDPServer{
	public static void main(String args[]){
		DatagramSocket aSocket = null;
		int serverPort = 1234;
		try{
			aSocket = new DatagramSocket(serverPort);
			byte[] buffer = new byte[1000];
			int cnt = 0;
			System.out.println("Now we are in listening.");
			while(true){
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				cnt++;
				System.out.printf("The %dth thread\n", cnt);
				System.out.printf("Now the client port is %d\n", request.getPort());
				ServerThread serverThread = new ServerThread(request);
				serverThread.start();
				
				DatagramPacket reply = new DatagramPacket(request.getData(),
				request.getLength(), request.getAddress(), request.getPort());		
				aSocket.send(reply);
			}
		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null) aSocket.close();
		}
	}
}