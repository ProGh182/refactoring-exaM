package fr.efrei.residence;

public class OptionFibre extends DecorateurLoyer {

    private final double prix;

    public OptionFibre(Loyer interieur, double prix) {
        super(interieur);
        this.prix = prix;
    }

    @Override
    public double montant() {
        return interieur.montant() + prix;
    }
}
