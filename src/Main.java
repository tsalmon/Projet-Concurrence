import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: CoolerMaster
 * Date: 15/04/2014
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String [ ] args){
        ArrayList<Elfe> elfes = new ArrayList<Elfe>();
        ArrayList<Rene> renes = new ArrayList<Rene>();

        PereNoel pere = new PereNoel();
        pere.start();

        for(int i = 1; i<10; ++i){
            Elfe e = new Elfe(i, pere);
            elfes.add(e);
            e.start();
        }

        for (int i = 1; i<10; ++i){
            Rene r = new Rene(i, pere);
            renes.add(r);
            r.start();
        }


    }
}
