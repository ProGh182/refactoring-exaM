package fr.efrei.residence;

/**
 * Couche qui enveloppe un loyer pour en modifier le montant.
 */
public abstract class DecorateurLoyer implements Loyer {

    protected final Loyer interieur;

    protected DecorateurLoyer(Loyer interieur) {
        this.interieur = interieur;
    }
}
