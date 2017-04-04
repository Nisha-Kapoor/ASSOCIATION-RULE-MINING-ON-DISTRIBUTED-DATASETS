public interface updown extends java.rmi.Remote
{
	public void upload(String fname, byte[] b) throws java.rmi.RemoteException;
	public byte[] download(String fname) throws java.rmi.RemoteException;
}