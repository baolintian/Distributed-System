import java.rmi.RemoteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public interface ComputingService extends java.rmi.Remote {

    //int add(int a, int b) throws RemoteException;
	boolean add(String id, String name) throws RemoteException;
	String queryByID(String id) throws RemoteException;
	String queryByName(String name) throws RemoteException;
	boolean delete(String bookid) throws RemoteException;
	String show() throws RemoteException;
	void update() throws RemoteException;
	
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
