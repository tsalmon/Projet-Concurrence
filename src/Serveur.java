import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	public static final int port = 4542;
    public static PereNoel pereNoel;


	public static void main(String [] args) throws IOException{
        System.out.println("Welcome to the server side");
        BufferedReader in = null;
        PrintWriter out = null;

        ServerSocket server = null;
        Socket fromClient = null;
        pereNoel = new PereNoel();
        pereNoel.start();
		try{
			ServerSocket sattente = new ServerSocket(port);
			do{
				Socket client = sattente.accept();
                Traitement t = new Traitement(client, pereNoel);
				t.start();
			} while(true);
		} catch(Exception e){
			System.out.println("Couldn't listen to port "+port);
            System.exit(-1);
		}
	}
}
