package fr.efrei.residence;

public class OptionLaverie extends DecorateurLoyer {

    private static final double PRIX = 12;

    public OptionLaverie(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() + PRIX;
    }
}
