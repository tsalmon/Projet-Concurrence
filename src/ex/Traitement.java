package ex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class Traitement extends Thread{
	private Socket service;

	public Traitement(Socket s){
		this.service = s;
	}

	public void run(){
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(service.getInputStream()));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(service.getOutputStream()));
			rd.readLine(); // println
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
