package fr.efrei.residence;

public class OptionMenage extends DecorateurLoyer {

    private final double taux;

    public OptionMenage(Loyer interieur, double taux) {
        super(interieur);
        this.taux = taux;
    }

    @Override
    public double montant() {
        return interieur.montant() * taux;
    }
}
