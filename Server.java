import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
public class Server extends RmiImplementation {
   public Server() {}
   public static void main(String args[]) {
      try {
    	  RmiImplementation rimpObj = new RmiImplementation();
    	  RmiInterface stub = (RmiInterface) UnicastRemoteObject.exportObject(rimpObj, 0);
    	  Registry rgsty = LocateRegistry.createRegistry(1888);
    	  rgsty.rebind("Rmi://localhost:9001/filedataservice", stub);
         System.out.println("Server is now ready...");
      } catch (Exception e) {
         System.out.println("Server exception: " + e.toString());
         e.printStackTrace();
      }
   }
}
