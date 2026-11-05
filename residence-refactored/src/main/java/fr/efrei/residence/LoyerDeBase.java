package fr.efrei.residence;

/**
 * Centre de la chaîne : le loyer de base du logement.
 */
public class LoyerDeBase implements Loyer {

    private final Logement logement;

    public LoyerDeBase(Logement logement) {
        this.logement = logement;
    }

    @Override
    public double montant() {
        return logement.getTarif().loyerBase(logement.getSurface(), logement.getNombrePlaces());
    }
}
