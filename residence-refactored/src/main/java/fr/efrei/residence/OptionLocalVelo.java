package fr.efrei.residence;

public class OptionLocalVelo extends DecorateurLoyer {

    private static final double PRIX = 6;

    public OptionLocalVelo(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() + PRIX;
    }
}
