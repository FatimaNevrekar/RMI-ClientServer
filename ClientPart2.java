import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientPart2 {
	
	 public ClientPart2() {
	}

	public static void main(String[] args) {
	      try {
	    	  
	    	  Registry registry = LocateRegistry.getRegistry(1888);

		      RmiInterface rmiInterface = (RmiInterface) registry.lookup ("Rmi://localhost:9001/filedataservice");
	    	  
	    	  WatchService ws= FileSystems.getDefault().newWatchService();
	  	      
	    	  Scanner in = new Scanner(System.in);

	    	  System.out.println(" Please Enter the client Path :");
	          	String clientPath = in.nextLine();
	    	  
	          	System.out.println(" Please Enter the Server Path :");
	          	String ServerPathName = in.nextLine();
	          	
	    	  Path path = Paths.get(clientPath);
	    	  System.out.println("The current working directory is " + path.toAbsolutePath());

	        path.register(ws, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

	        WatchKey wk;
	        while ((wk = ws.take()) != null) {
	            for (WatchEvent<?> event : wk.pollEvents()) {
	                System.out.println("Event Name:" + event.kind() + " Affected file Name: " + event.context() + "");
	                String ServerPath=ServerPathName+"/"+event.context();
	                System.out.println("ServerPath:"+ServerPath);

	                String ClientPath=path.toAbsolutePath()+"/"+event.context();
	                System.out.println("ClientPath:"+ClientPath);

	                
	                if(("ENTRY_MODIFY".equalsIgnoreCase(event.kind().name()))) {
	               
	                System.out.println("File Uploding Started...");
	         
	                rmiInterface.uploadFile(ServerPath, new ClientPart2(). Filetobyte (ClientPath));
	  
	                }
	                if(("ENTRY_CREATE".equalsIgnoreCase(event.kind().name()))) {
		                System.out.println("File Uploding Started...");
		   	         
		                rmiInterface.uploadFile(ServerPath, new ClientPart2(). Filetobyte (ClientPath));
		           
	                }
	                if(("ENTRY_DELETE".equalsIgnoreCase(event.kind().name()))) {
			  	    	boolean bool = rmiInterface.removeFile(ServerPath);
			 			 System.out.println("File deleted :" + bool);
		           
	                }
	                
	            }
	            wk.reset();
	            }
 
	      } catch (Exception e) {
	         System.err.println("Client Side exception: " + e.toString());
	         e.printStackTrace();
	      }
	   }
	   
	    byte[] Filetobyte (String filename) {
		   byte[] byt = null;
		   try {
		   File file = new File (filename);
		   byt = new byte[(int) file.length ()];
		   BufferedInputStream is = new BufferedInputStream (new FileInputStream (file));
		   is.read (byt);
		   }
		   catch (IOException e) {
		   e.printStackTrace ();
		   }
		   return byt;
		   }
}
