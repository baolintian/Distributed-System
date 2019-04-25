import java.net.*;
import java.io.*;

public class UDPClient{
public static void main(String args[]){
// args give message contents and server hostname
    DatagramSocket aSocket = null;
	try {
		aSocket = new DatagramSocket();
		byte[] m = args[0].getBytes();
		InetAddress aHost = InetAddress.getByName("127.0.0.1");
		int serverPort = 1234;
		//DatagramPacket function means it's a UDP protocol.
		DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
		aSocket.send(request);
		byte[] buffer = new byte[1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		aSocket.receive(reply);
		//cut some unnecessary characters.
		String put  = new String(reply.getData());
		put = put.substring(0, reply.getLength());
		System.out.println("Reply: " + put);
	} catch (SocketException e){
		System.out.println("Socket: " + e.getMessage());
    } catch (IOException e){
		System.out.println("IO: " + e.getMessage());
    } finally { 
	    if(aSocket != null) aSocket.close();
	}
}
}