package fr.efrei.residence;

public class OptionParking extends DecorateurLoyer {

    private static final double PRIX = 45;

    public OptionParking(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() + PRIX;
    }
}
