import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class Client {
   private Client() {}
  
	
   public static void main(String[] args) {
      try {
     	 Registry registry = LocateRegistry.getRegistry(1888);
    	  
    	  RmiInterface Filedataservice = (RmiInterface) registry.lookup ("Rmi://localhost:9001/filedataservice");
    	 
    	  Scanner in = new Scanner(System.in);
    	  System.out.println(" Please Enter : 1 for Upload; 2 for Download ;3 for Remove a file; 4 for Rename ");
    	  String userData = in.nextLine();
    	  int functionType = Integer.valueOf(userData);
    	  
    	  switch (functionType) {
    	  case 1:
    		  System.out.println(" Please Enter the client Path From where you wish to upload: ");
          	String clientPath = in.nextLine();
          	File f = new File(clientPath);
          	String fileName=f.getName();
    		  System.out.println(" Please Enter your server Path: ");
        	String serverPath = in.nextLine();
        	 
    	  System.out.println("File Uploding Started...");
      	  Filedataservice.uploadFile(serverPath+"/"+fileName, new Client(). Filetobyte (clientPath));

    	    break;
    	  case 2:
    		  System.out.println(" Please Enter the server Path From where you want to download the file: ");
    		  serverPath = in.nextLine();
            	f = new File(serverPath);
            	fileName=f.getName();
      		  System.out.println(" Please Enter your Client Path where you want to store: ");
          	clientPath = in.nextLine();
          	 
        	  System.out.println("File Downloading Started...");
        	  byte [] mydata = Filedataservice.downloadFileFromServer(serverPath);
  			System.out.println("downloading, Please wait...");
  			File clientpathfile = new File(clientPath+"/"+fileName);
  			FileOutputStream out=new FileOutputStream(clientpathfile);				
    		out.write(mydata);
  			out.flush();
  	    	out.close();
    	    break;
    	    
    	  case 3:
    		  System.out.println(" Please Enter the server Path From where you want to delete the file: ");
    		  serverPath = in.nextLine();
  	    	boolean bool = Filedataservice.removeFile(serverPath);
 			 System.out.println("File deleted :" + bool);
        	    break;
        	    
    	  case 4:
    		  System.out.println(" Please Enter the server Path to rename the file: ");
    		  serverPath = in.nextLine();
    		  Path path = Paths.get(serverPath);
    		  String directory = path.getParent().toString();

    		  System.out.println("directory Path:"+directory);

	  
    		  System.out.println(" Please Enter New File name :");
    		  String newFileName = in.nextLine();
    		  
        	  boolean bolean = Filedataservice.remnameFile(serverPath,directory+"/"+newFileName);

          	    break;
    	  default:
    		    System.out.println("Invalid Input...");
    	  
    	  }
      } catch (Exception e) {
         System.err.println("Client side exception: " + e.toString());
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