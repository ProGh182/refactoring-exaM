package fr.efrei.residence;

public class CalculateurLoyer {

    private static final double MAJORATION_MEUBLE = 1.15;
    private static final double MINORATION_REZ_DE_CHAUSSEE = 0.95;
    private static final int ETAGE_REZ_DE_CHAUSSEE = 0;
    private static final double PRIX_PARKING = 45;
    private static final double PRIX_LAVERIE = 12;
    private static final double PRIX_ASSURANCE = 9;

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, StrategieProfil profil, boolean parking, boolean menage,
                                boolean laverie, boolean fibre, boolean assurance) {
        TarifLogement tarif = l.getTarif();
        double loyer = tarif.loyerBase(l.getSurface(), l.getNombrePlaces());

        if (l.isMeuble()) loyer = loyer * MAJORATION_MEUBLE;
        if (l.getEtage() == ETAGE_REZ_DE_CHAUSSEE) loyer = loyer * MINORATION_REZ_DE_CHAUSSEE;

        // options
        if (menage) loyer = loyer * tarif.tauxMenage();
        if (parking) loyer = loyer + PRIX_PARKING;
        if (laverie) loyer = loyer + PRIX_LAVERIE;
        if (fibre) loyer = loyer + tarif.prixFibre();
        if (assurance) loyer = loyer + PRIX_ASSURANCE;

        return Utils.arrondir(profil.appliquerRemise(loyer));
    }

    public double calculerCharges(Logement l) {
        return l.getTarif().charges();
    }
}
