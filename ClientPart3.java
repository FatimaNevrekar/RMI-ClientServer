import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientPart3 {
	
	 public ClientPart3() {
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
	      try {
	    	  
	    	  Registry registry = LocateRegistry.getRegistry(1888);

		      RmiInterface rmiInterface = (RmiInterface) registry.lookup ("Rmi://localhost:9001/filedataservice");
	    	
		      ExecutorService es = Executors.newCachedThreadPool();
	    	  
	    	  Scanner in = new Scanner(System.in);
	    	  System.out.println(" Please Enter : 1 for ASynchronus Add Operation ; 2 for ASynchronus Sort Operation ;3 for Syn Add; 4 Syn Sort;");
	    	  String userData = in.nextLine();
	    	  int functionType = Integer.valueOf(userData);
	    	  
	    	  switch (functionType) {
	    	  case 1:
		    	  System.out.println(" Please Enter value for 1st Number:");
		    	  String firstnum = in.nextLine();
		    	  int i = Integer.valueOf(firstnum);
		    	  System.out.println(" Please Enter value for 2nd Number:");
		    	  String secondnum = in.nextLine();
		    	  int j = Integer.valueOf(secondnum);
	              Future futureTask1 = es.submit(() -> rmiInterface.add(i, j));
	              while(!futureTask1.isDone()) {
	            	  System.out.println("Some different work is going on , Please Wait");
	            	  Thread.sleep(5000);
	              }
                  System.out.println("Result of Addition is : " + futureTask1.get());   

	        	    break;
	    	  case 2:
	              int[] arr = { 15, 6, 4, 35, 24, 8, 106, 101 };
	             
	             Future  futureTask2 = es.submit(() -> rmiInterface.sort(arr));

	              while(!futureTask2.isDone()) {
	            	  System.out.println("Some different work is going on , Please Wait");
	            	  Thread.sleep(5000);
	              }
	              
	              System.out.println("Sorted Array: " +Arrays.toString(rmiInterface.sort(arr)));    

	        	    break;
	    	  case 3:
		    	  System.out.println(" Please Enter value for 1st Number:");
		    	   firstnum = in.nextLine();
		    	  i = Integer.valueOf(firstnum);
		    	  System.out.println(" Please Enter value for 2nd Number:");
		    	  secondnum = in.nextLine();
		    	 j = Integer.valueOf(secondnum);
		    	int k= rmiInterface.add(i, j); 
                 System.out.println("Result of Addition is : " + k);   

	        	    break;
	    	  case 4:
	              int[] arr2 = { 15, 6, 4, 35, 24, 8, 106, 101 };
	              rmiInterface.sort(arr2);
	              System.out.println("Sorted Array:"+Arrays.toString( rmiInterface.sort(arr2)));
	        	    break;
	    	  default:
	    		    System.out.println("Invalid Input...");
	    	  
	    	  }
	    	
	    	  
	      } catch (Exception e) {
	         System.err.println("Client side exception: " + e.toString());
	         e.printStackTrace();
	      }
	   }
	   
}
