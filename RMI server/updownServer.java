import java.rmi.*;
import java.io.*;
import java.util.*;

class updownServer 
{
	public updownServer()
	{
		try
		{
			updown c=new updownImpl();
			Naming.rebind("rmi://localhost:1099//updownService",c);
		}
		catch(Exception e)
		{
			//System.out.println("Exception occured: "+e);
		}
	}
	public static void main(String args[]){
		new updownServer();

						


	}
}
