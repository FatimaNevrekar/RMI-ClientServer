import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

public class RmiImplementation implements RmiInterface {

public int add(int i, int j) {
int k=0;
k=i+j;
System.out.println("Result:"+k);
return k;

}
public int[] sort(int[] arr) {
for (int i = 0; i < arr.length; i++) {
 for (int j = i + 1; j < arr.length; j++) {
int storage = 0;
if (arr[j] < arr[i]) {
storage = arr[i];
arr[i] = arr[j];
 arr[j] = storage;
 }
}
    System.out.print(arr[i] + " ");
}
return arr;
}

//public int[] inputArray(int size,int arr[]) {
//	
//	
//	return arr;
//	
//}


public void uploadFile(String filename, byte[] filecontent) throws RemoteException{
File file = new File (filename);
try {
if (!file.exists ()) {
	System.out.println("Create New File..." );

file.createNewFile();
}

FileOutputStream os = new FileOutputStream(file);
os.write (filecontent);
os.flush();
os.close();
System.out.println("File Uploaded Successfully.." );

}
catch (IOException e) {
	   e.printStackTrace ();
}
}

public byte[] downloadFileFromServer(String serverpath) throws RemoteException {
	
	byte [] myFiledata;	
	
		File serverpathfile = new File(serverpath);			
		myFiledata=new byte[(int) serverpathfile.length()];
		FileInputStream in;
		try {
			in = new FileInputStream(serverpathfile);
			try {
				in.read(myFiledata, 0, myFiledata.length);
			} catch (IOException e) {
				
				e.printStackTrace();
			}						
			try {
				in.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}		
		System.out.println("File Downloaded Succesfully...");

		return myFiledata;
			 
}

public boolean removeFile(String serverpath) throws RemoteException {
	File myServerpathdir = new File(serverpath);
	boolean isdeleted=myServerpathdir.delete();

	   if (isdeleted) {
	         System.out.println("File Deleted Succesfully...");
	     }
	     else {
	         System.out.println("Error in Deleting the file ");
	     }
	   return isdeleted;

}

@Override
public boolean remnameFile(String oldFileName,String newFileName) throws RemoteException {
	 File file = new File(oldFileName);
	 
     File newFile = new File(newFileName);
     boolean isfileRenamed = file.renameTo(newFile);
          
     if (isfileRenamed) {
         System.out.println("File renamed to " + newFile.getPath());
     }
     else {
         System.out.println("Error renaming file " + file.getPath());
     }
     
     return isfileRenamed;
}

}
