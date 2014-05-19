import java.util.Random;

public abstract class Character extends Thread{
    protected int number;
    protected PereNoel pere;
    public boolean enAttente = false;
    protected final Object monitor = new Object();
    protected String charName;
    protected String action;
    protected static final  int multiplier = 1;

    public int getNumber(){
        return this.number;
    }

    public synchronized void leave(){
        enAttente = false;
        synchronized (monitor){
            monitor.notify();
        }
    }

    public Character(int number, PereNoel p, String name, String act){
        this.pere = p;
        this.number = number;
        this.charName = name;
        this.action = act;
    }

    public String getCharName(){
        return charName;
    };

    @Override
    public void run(){
        while (true){
            if (enAttente){
                try {
                    synchronized (monitor){
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //Random rand = new Random();
                System.out.println(charName+" №"+this.number+action);
                try {
                    sleep(multiplier);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                enAttente = true;
                try {
                    pere.charRequest(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
