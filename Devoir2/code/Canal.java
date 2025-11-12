package code;
import java.util.Random;


/**
 * Canal.java
 * Simulation d'un canal de transmission non fiable.
 * Chaque trame ou ACK peut être perdue, corrompue ou retardée.
 * Paramètres configurables : probErreur, probPerte, delaiMax.
 */

public class Canal {

    // Paramètres du canal
    private double probErreur = 0.05;
    private double probPerte = 0.10;
    private int delaiMax = 200; // en millisecondes
    private Random rand = new Random();

    public Canal(double probErreur, double probPerte, int delaiMax) {
        this.probErreur = probErreur;
        this.probPerte = probPerte;
        this.delaiMax = delaiMax;
    }

    public Canal() {
        // Constructeur par défaut avec paramètres par défaut
        this(0.05, 0.10, 200);
    }

    // Simule la transmission d'une trame du côté émetteur vers le récepteur
    public void transmettre (Trame trame) {
        double tirage = rand.nextDouble();

        // 1 - Simule la perte de trame
        if (tirage < probPerte) {
            System.out.println("Canal : Trame perdue : " + trame);
            return;
        }

        // 2 - Simule la corruption de trame
        if (tirage < probPerte + probErreur) {
            corrompre(trame);
            System.out.println("Canal : Trame corrompue : " + trame);
        }

        // 3 - Simule le délai de transmission
        int delai = rand.nextInt(delaiMax + 1);
        try {
            // Pause pour simuler le délai
            Thread.sleep(delai);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Livraison de la trame
        System.out.println("Canal: Trame transmise après " + delai + " ms : " + trame);
    }

    // Méthode pour corrompre une trame en modifiant un caractère aléatoire
    private void corrompre(Trame trame) {
        String data = trame.getDonnees();
        if (data.isEmpty()) return;
        char[] chars = data.toCharArray();
        int pos = rand.nextInt(chars.length);
        chars[pos] = (char) (chars[pos] + 1);
        trame.setDonnees(new String(chars));
    }

    // Test
    public static void main(String[] args) {
        // Création d'un canal
        Canal canal = new Canal();
        // Envoi de 100 trames pour test
        for (int i = 0; i < 100; i++) {
            Trame trame = new Trame(i, "Trame " + i, false);
            canal.transmettre(trame);
        }
        System.out.println("Simulation de canal terminée.");
    }



}



    


    

