import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;


public abstract class CharacterReseau extends Character {
    BufferedReader in;
    PrintWriter out;
    String serverMessage;

    public synchronized void leave(){
        out.println("leaved");
    }

    public CharacterReseau(int n, PereNoel p, String name, String act,  PrintWriter out){
        super(n, p, name, act);
        this.out = out;
    }

    public static CharacterReseau getCharacter(String name, PereNoel p, PrintWriter out) throws Exception {
        String charType = name.substring(0,4);
        int number =  Integer.parseInt(name.substring(4,5));

        if (charType.equalsIgnoreCase("elfe")){
            return new ElfeReseau(number, p, out);
        } else
        if (charType.equalsIgnoreCase("rene")){
            return new ReneReseau(number, p, out);
        } else {
            throw new Exception("Unknown character!");
        }
    }

    @Override
    public void run(){
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("127.0.0.1", 4542);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert clientSocket != null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){

            Random rand = new Random();
            try {
                sleep(multiplier*(rand.nextInt(9)+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(charName+number+" demande Pere Noel");
            out.println(charName+number);
            try {
                serverMessage = in.readLine();
                System.out.println(charName+number+" : " + serverMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
