import java.util.LinkedList;

public class PereNoel extends Thread{
    LinkedList<Character> reneRequests = new LinkedList<Character>();
    LinkedList<Character> elfeRequests = new LinkedList<Character>();
    static final int nbElfesReveillent = 3;
    static final int nbRenesReveillent = 9;
    static int timeout = Integer.MAX_VALUE;

    protected static final Object monitor = new Object();
    private void showChars(LinkedList<Character> lst){
        for (Character r :lst){
            System.out.print(r.getNumber()+" ");
        }
        System.out.println("");
    }

    public synchronized void charRequest(Character c) throws InterruptedException {
        timeout = Character.multiplier;
        if (c instanceof Rene || c instanceof  ReneReseau){
            reneRequests.add(c);
            System.out.println("Rene n"+c.getNumber()+" est revenu de vacance. "+this.reneRequests.size()+" rene(s) en attente de Pere Noel");
            //showChars(reneRequests);
            synchronized (monitor){
                if(reneRequests.size() >= nbRenesReveillent){
                    monitor.notify();
                }
            }
        } else
        if (c instanceof Elfe || c instanceof  ElfeReseau){
            elfeRequests.add(c);
            System.out.println("Tache d'elfe n" + c.getNumber() + " ajoutee. " + this.elfeRequests.size() + " elfe(s) en attente de Pere Noel");
            //showChars(elfeRequests);
            synchronized (monitor){
                if(elfeRequests.size() >= nbElfesReveillent){
                    monitor.notify();
                }
            }
        }
    }

    private synchronized void helpElfes() throws InterruptedException {
        System.out.println("Pere Noel est reveille par elfes");
        int processedElfes[] = new int[nbElfesReveillent];
        String elfes_aides="";
        for (int i = 0; i<nbElfesReveillent; ++i){
            processedElfes[i] = elfeRequests.getFirst().getNumber();
            elfeRequests.getFirst().leave();
            elfeRequests.removeFirst();
            elfes_aides+="n" + processedElfes[i] + " ";
        }
        System.out.println("Pere Noel a aidé les elfes " + elfes_aides);
    }

    private synchronized void helpRenes() throws InterruptedException {
        System.out.println("Pere Noel est reveille par les renes");
        int i = 0;
        for (i = 0; i<nbRenesReveillent; ++i){
            reneRequests.getFirst().leave();
            reneRequests.removeFirst();
        }
        System.out.println("Pere Noel a envoyé les " + i + " renes en vacances");
    }

    public void run(){
        while (true){
            while (elfeRequests.size() < nbElfesReveillent  && reneRequests.size() < nbRenesReveillent){

                System.out.println("Pere Noel dort");
                synchronized (monitor){
                    try {
                        monitor.wait(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            // Avant on verifie reneRequests, vu que des renes sont prioritaires
            if (reneRequests.size() == nbRenesReveillent){
                try {
                    helpRenes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (elfeRequests.size() >= nbElfesReveillent){
                try {
                    helpElfes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}