package ex;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	public static final int port = 4542;
	public static void main(String [] args){
		try{
			ServerSocket sattente = new ServerSocket(port);
			do{
				Socket service = sattente.accept(); //attente de connexion
				Traitement t = new Traitement(service);
				t.start();
			}while(true);
		} catch(Exception e){
			
		}
	}
}
