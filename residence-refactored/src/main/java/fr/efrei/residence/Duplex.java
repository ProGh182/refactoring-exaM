package fr.efrei.residence;

/**
 * Duplex : 1 occupant, pas de balcon.
 */
public class Duplex extends Logement {

    public Duplex(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.DUPLEX, new TarifDuplex(), surface, etage, meuble, 1);
    }
}
