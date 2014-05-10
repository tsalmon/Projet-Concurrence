import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: CoolerMaster
 * Date: 15/04/2014
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static final int nbElfes = 9;
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
