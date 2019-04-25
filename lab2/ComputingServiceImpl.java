import java.rmi.RemoteException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class ComputingServiceImpl extends java.rmi.server.UnicastRemoteObject implements ComputingService {
	BookList booklist = null;
	public ComputingServiceImpl() throws RemoteException {
		super();
		booklist = new BookList();
    }
	public boolean add(String id, String name){
		for(int i=0; i<booklist.cnt; i++){
			if(booklist.book[i].id.equals(id)){
				return false;
			}
		}
		Book temp = new Book(id, name);
		booklist.book[booklist.cnt++] = temp;
		return true;
	}
	public String queryByID(String id){
		//System.out.println("in "+id);
		for(int i=0; i<booklist.cnt; i++){
			if(booklist.book[i].id.equals(id)){
				return new String(booklist.book[i].id+ " " + booklist.book[i].name);
			}
		}
		return null;
	}
	public String queryByName(String name){
		StringBuilder result = new StringBuilder();
		for(int i=0; i<booklist.cnt; i++){
			if(booklist.book[i].name.indexOf(name)>-1){
				 result.append(System.lineSeparator()+booklist.book[i].id+" "+booklist.book[i].name);
			}
		}
		//System.out.println(result.toString());
		return result.toString();
	}
	
	public boolean delete(String bookid){
		Book[] temp = new Book[booklist.cnt-1];
		int i;
		for(i=0; i<booklist.cnt; i++){
			if(booklist.book[i].id.equals(bookid)){
				break;
			}
		}
		int tot = 0;
		if(i<booklist.cnt){
			for(int j=0; j<booklist.cnt; j++){
				if(j!=i){
					temp[tot++] = booklist.book[j];
				}
			}
			for(int j=0; j<tot; j++){
				booklist.book[j] = temp[j];
			}
			booklist.cnt = tot;
			return true;
		}
		return false;
	}
	public String show(){
		StringBuilder result = new StringBuilder();
		for(int i=0; i<booklist.cnt; i++){
			result.append(System.lineSeparator()+booklist.book[i].id+" "+booklist.book[i].name);
		}
		//System.out.println(result.toString());
		return result.toString();
	}
	public void update(){
		File file = null;
        FileWriter fw = null;
        file = new File("book.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
			
            for(int i = 0;i <booklist.cnt;i++){
				//System.out.println("1");
				String tot = booklist.book[i].id+" "+booklist.book[i].name+"\r\n";
				//System.out.println("2");
				//System.out.println(tot);
				fw.write(tot);
				fw.flush();
            }
            System.out.println("write data success ful");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
	}
	
}



