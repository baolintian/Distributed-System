import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;


public class ThreadPoolTest {
     public static void main(String[] args) throws Exception {
		 //the init size of the pool is 5, max size is 10, dead time is 200 unit, fourth var is time unit
         ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 200, TimeUnit.MILLISECONDS,
		 new ArrayBlockingQueue<Runnable>(1));
		 
		 Socket socket=null;
		 ServerSocket listenSocket = new ServerSocket(8189);
		 System.out.println("Server listening at 8189");
         int count = 0;
		 while(true){
			 socket=listenSocket.accept();
			 count++;
			 System.out.println("The total number of clients is " + count + ".");
			 ServerThread serverThread = new ServerThread(socket);
			 executor.execute(serverThread);
		 }
         //executor.shutdown();
     }
}
 
