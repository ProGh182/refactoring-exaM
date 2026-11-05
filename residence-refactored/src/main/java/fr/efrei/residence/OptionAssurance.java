package fr.efrei.residence;

public class OptionAssurance extends DecorateurLoyer {

    private static final double PRIX = 9;

    public OptionAssurance(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() + PRIX;
    }
}
