import java.util.Random;

public abstract class Character extends Thread{
    protected int number;
    protected PereNoel pere;
    protected boolean enAttente = false;
    protected static final Object monitor = new Object();
    protected static String charName;
    protected static String action;


    public int getNumber(){
        return this.number;
    }

    public synchronized void leave(){
        enAttente = false;
        synchronized (monitor){
            monitor.notify();
        }
    }

    public Character(){
    }

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
                Random rand = new Random();
                System.out.println(charName+" №"+this.number+action);
                try {
                    sleep(1000*(rand.nextInt(9)+1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pere.charRequest(this);
                enAttente = true;
            }
        }
    }

}
