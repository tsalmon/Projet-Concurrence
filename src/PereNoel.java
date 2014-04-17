import java.util.LinkedList;

public class PereNoel extends Thread{
    LinkedList<Character> reneRequests = new LinkedList<Character>();
    LinkedList<Character> elfeRequests = new LinkedList<Character>();

    private static final Object monitor = new Object();

    public synchronized void charRequest(Character c){
        if (c instanceof Rene){
            reneRequests.add(c);
            System.out.println("Rêne №"+c.getNumber()+" est revenu de vacance. "+this.reneRequests.size()+" rêne(s) en attente de Pere Noel");
            synchronized (monitor){
                if(reneRequests.size() == 9){
                    monitor.notify();
                }
            }
        } else
        if (c instanceof Elfe){
            elfeRequests.add(c);
            System.out.println("Tache d'elfe №"+c.getNumber()+" ajoutée. "+this.elfeRequests.size()+" elfe(s) en attente de Pere Noel");
            synchronized (monitor){
                if(elfeRequests.size() >= 3){
                    monitor.notify();
                }
            }
        }
    }

    private synchronized void helpElfes(){
        int processedElfes[] = new int[3];
        for (int i = 0; i<3; ++i){
            processedElfes[i] = elfeRequests.getFirst().getNumber();
            elfeRequests.getFirst().leave();
            elfeRequests.removeFirst();
        }
        System.out.println("Pere Noël a aidé elfes №"+processedElfes[0]+", №"+processedElfes[1]+", №"+processedElfes[2]);
    }

    private synchronized void helpRenes(){
        System.out.println("Pere Noël est reveillé par rênes");
        for (int i = 0; i<9; ++i){
            reneRequests.getFirst().leave();
            reneRequests.removeFirst();
        }
        System.out.println("Pere Noël a envoyé les rênes à vacances");
    }

    public void run(){
        while (true){
            while (elfeRequests.size() < 3 && reneRequests.size() < 9){
                System.out.println("Pere Noël dort");
                synchronized (monitor){
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (reneRequests.size() == 9){
                helpRenes();
            }

            if (elfeRequests.size() >= 3){
                helpElfes();
            }


        }
    }
}
