import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Traitement extends Thread{
	private Socket client;
    private String input, output;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private PereNoel pereNoel;

	public Traitement(Socket s, PereNoel p){
		this.client = s;
        this.pereNoel = p;
	}

	public void run(){
		try {
            System.out.println("Got client");

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),true);

            while ((input = in.readLine()) != null) {
                Character ch = CharacterReseau.getCharacter(input, pereNoel, out);
                pereNoel.charRequest(ch);
            }
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
