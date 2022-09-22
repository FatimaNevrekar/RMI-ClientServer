import java.rmi.Remote;
import java.rmi.RemoteException;
public interface RmiInterface extends Remote {
	
   int add(int i,int j) throws RemoteException;
   
   int[] sort(int arr[])throws RemoteException;
   
	public void uploadFile(String filename, byte[] file) throws RemoteException;
	
	public byte[] downloadFileFromServer(String servername) throws RemoteException;

	public boolean removeFile(String serverpath) throws RemoteException;
	
	public boolean remnameFile(String oldFileName,String newFileName) throws RemoteException;

}