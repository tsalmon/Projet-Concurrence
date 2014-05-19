import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class PereNoel extends Thread{
    LinkedList<Character> reneRequests = new LinkedList<Character>();
    LinkedList<Character> elfeRequests = new LinkedList<Character>();

    static final int nbElfesReveillent = 3;
    static final int nbRenesReveillent = 3;

    Semaphore elfesSemaphore = new Semaphore(1);
    Semaphore renesSemaphore = new Semaphore(1);

    protected static final Object monitor = new Object();

    private void showChars(LinkedList<Character> lst){
        for (Character r :lst){
            System.out.print(r.getNumber()+" ");
        }
        System.out.println("");
    }

    public synchronized void charRequest(Character c) throws InterruptedException {
        if (c instanceof Rene || c instanceof  ReneReseau){
            renesSemaphore.acquire();
            reneRequests.add(c);
            System.out.println("Rêne №"+c.getNumber()+" est revenu de vacance. "+this.reneRequests.size()+" rêne(s) en attente de Pere Noel");
            synchronized (monitor){
                if(reneRequests.size() >= nbRenesReveillent){
                    monitor.notify();
                }
            }
            renesSemaphore.release();
        } else
        if (c instanceof Elfe || c instanceof  ElfeReseau){
            elfesSemaphore.acquire();
            elfeRequests.add(c);
            System.out.println("Tache d'elfe №"+c.getNumber()+" ajoutée. "+this.elfeRequests.size()+" elfe(s) en attente de Pere Noel");
            synchronized (monitor){
                if(elfeRequests.size() >= nbElfesReveillent){
                    monitor.notify();
                }
            }
            elfesSemaphore.release();
        }
    }

    private synchronized void helpElfes() throws InterruptedException {
        elfesSemaphore.acquire();
        System.out.println("Pere Noël est reveillé par elfes");
        int processedElfes[] = new int[nbElfesReveillent];
        for (int i = 0; i<nbElfesReveillent; ++i){
            processedElfes[i] = elfeRequests.getFirst().getNumber();
            elfeRequests.getFirst().leave();
            elfeRequests.removeFirst();
//            System.out.println("Elfe №"+processedElfes[i]+" envoyé");
        }
        System.out.println("Pere Noël a aidé "+nbElfesReveillent+" elfes");
        elfesSemaphore.release();
    }

    private synchronized void helpRenes() throws InterruptedException {
        renesSemaphore.acquire();
        System.out.println("Pere Noël est reveillé par rênes");
        int processedRenes[] = new int[nbRenesReveillent];
        for (int i = 0; i<nbRenesReveillent; ++i){
            processedRenes[i] = reneRequests.getFirst().getNumber();
            reneRequests.getFirst().leave();
            reneRequests.removeFirst();
//            System.out.println("Rêne №"+processedRenes[i]+" envoyé");
        }
        System.out.println("Pere Noël a envoyé tous les "+nbRenesReveillent+" rênes à vacances");
        renesSemaphore.release();
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


            if (elfeRequests.size() >= nbElfesReveillent){
                try {
                    helpElfes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (reneRequests.size() == nbRenesReveillent){
                try {
                    helpRenes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
