package ex;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	public static final int port = 4242;
	public static void main(String [] args){
		String serveurName = "nom";
		try {
			Socket service = new Socket(serveurName, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
