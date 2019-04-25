import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        
        ComputingService computingServant = new ComputingServiceImpl();
        
        Naming.bind("rmi://localhost:8889/ComputingService",computingServant);
        System.out.println("ComputingService is online.");
    }
}
