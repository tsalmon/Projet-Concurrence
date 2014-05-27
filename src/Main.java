import java.io.*;
import java.util.ArrayList;

public class Main {
    static final int nbElfes = 3;
    static final int nbRenes = 9;

    public static void main(String [ ] args) throws IOException {
        ArrayList<Elfe> elfes = new ArrayList<Elfe>();
        ArrayList<Rene> renes = new ArrayList<Rene>();

        PereNoel pere = new PereNoel();
        pere.start();

        for(int i = 1; i<nbElfes+1; ++i){
            Elfe e = new Elfe(i, pere);
            elfes.add(e);
            e.start();
        }

        for (int i = 1; i<nbRenes+1; ++i){
            Rene r = new Rene(i, pere);
            renes.add(r);
            r.start();
        }
    }
}
