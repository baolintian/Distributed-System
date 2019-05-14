package wsserver;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class HelloWorldPublisher{
	
	public static void main(String[] args) {
	   HelloWorldImpl h = new HelloWorldImpl();
	   Endpoint.publish("http://localhost:9999/ws/hello", h);
	   System.out.println("Web service is online.");
    }

}