package code;

/**
 * Trame.java
 * Représente une trame transmise sur le canal.
 * Contient les informations nécessaires à la simulation :
 * - numéro de séquence
 * - données utiles
 * - indicateur d’ACK
 * - CRC (facultatif pour la suite)
 */
public class Trame {
    private int numero;       // Numéro de séquence
    private String donnees;   // Données utiles
    private boolean ack;      // True si c’est une trame d’acquittement
    private String crc;       // CRC

    public Trame(int numero, String donnees, boolean ack) {
        this.numero = numero;
        this.donnees = donnees;
        this.ack = ack;
        this.crc = "";
    }

    public int getNumero() {
        return numero;
    }

    public String getDonnees() {
        return donnees;
    }

    public boolean isAck() {
        return ack;
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getCrc() {
        return crc;
    }

    @Override
    public String toString() {
        String type = ack ? "ACK" : "DATA";
        return "[" + type + " #" + numero + "] " + donnees;
    }
}
