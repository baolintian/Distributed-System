import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.math.*;
import java.util.*;
import java.io.*;


public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, IOException {
        
        ComputingService computingObj =(ComputingService)Naming.lookup("rmi://localhost:8889/ComputingService");
		System.out.println("Now you have 5 options: number indicate the choice");
		System.out.println("1. Add a book");
		System.out.println("2. Query a book by id");
		System.out.println("3. Query a book by name");
		System.out.println("4. Delete a book by id");
		System.out.println("5. Show all the book");
		System.out.println("6. Exit(e)");
		Scanner cin=new Scanner(System.in);
		String inn;
		
		while(!((inn = cin.next()).equals("6"))){
			if(inn.equals("1")){
				System.out.println("Please input the id and name");
				String id = cin.next();
				id = id.substring(0, id.length());
				String name = cin.next();
				boolean flag = computingObj.add(id, name);
				if(flag) System.out.println("add successful");
				else System.out.println("add fails, the book has existed.");
			}
			else if(inn.equals("2")){
				System.out.println("Please input the book id");
				String id = cin.next();
				
				String book = computingObj.queryByID(id);
				if(book!=null){
					System.out.println("Your wanted book has been found.");
					System.out.println(book);
				}
				else {
					System.out.println("You wanted book hasn't been found");
				}
			}
			else if(inn.equals("3")){
				System.out.println("please input the name");
				String name = cin.next();
				String temp = computingObj.queryByName(name);
				if(temp!=null){
					System.out.println("query successful");
					System.out.println(temp);
				}
				else{
					System.out.println("failure");
				}
			}
			else if(inn.equals("4")){
				System.out.println("Please input the id you want to delete");
				String id = cin.next();
				boolean flag = computingObj.delete(id);
				if(flag) System.out.println("delete successful");
				else System.out.println("delete failure");
			}
			else if(inn.equals("5")){
				String info = computingObj.show();
				System.out.println("All the infomation is listed below");
				System.out.println(info);
			}
			else System.out.println("invalid input");
			System.out.println("Now you have 5 options: number indicate the choice");
			System.out.println("1. Add a book");
			System.out.println("2. Query a book by id");
			System.out.println("3. Query a book by name");
			System.out.println("4. Delete a book by id");
			System.out.println("5. Show all the book");
			System.out.println("6. Exit");
		}
		computingObj.update();
    }
}
