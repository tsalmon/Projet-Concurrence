import java.util.LinkedList;

public class PereNoel extends Thread{
    LinkedList<Character> reneRequests = new LinkedList<Character>();
    LinkedList<Character> elfeRequests = new LinkedList<Character>();
    static final int nbElfesReveillent= 3;
    static final int nbRenesReveillent = 9;

    protected static final Object monitor = new Object();
    private void showChars(LinkedList<Character> lst){
        for (Character r :lst){
            System.out.print(r.getNumber()+" ");
        }
        System.out.println("");
    }

    public synchronized void charRequest(Character c){
        if (c instanceof Rene || c instanceof  ReneReseau){
            reneRequests.add(c);
            System.out.println("Rêne №"+c.getNumber()+" est revenu de vacance. "+this.reneRequests.size()+" rêne(s) en attente de Pere Noel");
            //showChars(reneRequests);
            synchronized (monitor){
                if(reneRequests.size() == nbRenesReveillent){
                    monitor.notify();
                }
            }
        } else
        if (c instanceof Elfe || c instanceof  ElfeReseau){
            elfeRequests.add(c);
            System.out.println("Tache d'elfe №"+c.getNumber()+" ajoutée. "+this.elfeRequests.size()+" elfe(s) en attente de Pere Noel");
            //showChars(elfeRequests);
            synchronized (monitor){
                if(elfeRequests.size() >= nbElfesReveillent){
                    monitor.notify();
                }
            }

        }
    }

    private synchronized void helpElfes(){
        System.out.println("Pere Noël est reveillé par elfes");
        int processedElfes[] = new int[nbElfesReveillent];
        for (int i = 0; i<nbElfesReveillent; ++i){
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
            while (elfeRequests.size() < nbElfesReveillent  && reneRequests.size() < nbRenesReveillent){
                System.out.println("Pere Noël dort");
                synchronized (monitor){
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Avant on regarde reneRequests, vu que renes sont prioritaires
            if (reneRequests.size() == nbRenesReveillent){
                helpRenes();
            }

            if (elfeRequests.size() >= nbElfesReveillent){
                helpElfes();
            }


        }
    }
}
