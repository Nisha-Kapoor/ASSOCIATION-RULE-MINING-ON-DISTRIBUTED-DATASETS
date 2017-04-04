import java.io.*;
import java.util.*;
class updownImpl extends java.rmi.server.UnicastRemoteObject implements updown
{
	public updownImpl() throws java.rmi.RemoteException
	{
		super();
	}
	public void upload(String fname, byte[] b) throws java.rmi.RemoteException
	{
		try
		{
			FileOutputStream fout=new FileOutputStream(fname);
			
			fout.write(b);
			
			System.out.println("File uploaded");
						
		}
		catch(Exception e)
		{
			//System.out.println("Exception occured: "+e);
		}
	}
	public byte[] download(String fname) throws java.rmi.RemoteException
	{
		byte[] buffer=new byte[0];
		try
		{
			FileInputStream fin=new FileInputStream(fname);
			int size=(int)fin.getChannel().size();
			buffer=new byte[size];
			fin.read(buffer,0,size);
			System.out.println("File downloaded");
		}
		catch(Exception e)
		{
			//System.out.println("Exception occured: "+e);
		}
		return buffer;
	}
}