package fr.efrei.residence;

public class MajorationMeuble extends DecorateurLoyer {

    private static final double COEFFICIENT = 1.15;

    public MajorationMeuble(Loyer interieur) {
        super(interieur);
    }

    @Override
    public double montant() {
        return interieur.montant() * COEFFICIENT;
    }
}
