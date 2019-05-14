package wsserver;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
//Service Endpoint Interface
@WebService(targetNamespace="http://localhost:9999/ws/hello")
@SOAPBinding(style = Style.RPC)
public interface HelloWorld{
 
	//@WebMethod 
	//String getHelloWorld(String name);
	
	//@WebMethod 
	//int add(int a, int b);
	@WebMethod 
	boolean add(String id, String name);
	@WebMethod 
	String queryByID(String id);
	@WebMethod 
	String queryByName(String name);
	@WebMethod 
	boolean delete(String bookid);
	@WebMethod 
	String show();
	@WebMethod 
	void update();
	
	class Book{
		String id, name;
		public Book(String id, String name){
			this.id = id;
			this.name = name;
		}
	
	}
	class BookList{
		public Book[] book = new Book[100];
		public int cnt = 0;
		public BookList(){
			File file = new File("book.txt");
			try{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String s = null;
				while((s = br.readLine())!=null){
					String[] ss = s.split("\\ ");
					//System.out.println(ss[0]+" "+ss[1]);
					Book temp = new Book(ss[0], ss[1]);
					this.book[this.cnt] = temp;
					this.cnt+=1;
				}
				//System.out.println("end");
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}