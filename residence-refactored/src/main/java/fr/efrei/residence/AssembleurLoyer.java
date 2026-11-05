package fr.efrei.residence;

import java.util.Set;

/**
 * Seul endroit qui construit la chaîne du loyer. L'ordre compte : les pourcentages passent avant les prix fixes.
 */
public class AssembleurLoyer {

    private static final int ETAGE_REZ_DE_CHAUSSEE = 0;

    public Loyer assembler(Logement logement, Set<Option> options) {
        TarifLogement tarif = logement.getTarif();
        Loyer loyer = new LoyerDeBase(logement);

        if (logement.isMeuble()) loyer = new MajorationMeuble(loyer);
        if (logement.getEtage() == ETAGE_REZ_DE_CHAUSSEE) loyer = new MinorationRezDeChaussee(loyer);

        if (options.contains(Option.MENAGE)) loyer = new OptionMenage(loyer, tarif.tauxMenage());
        if (options.contains(Option.PARKING)) loyer = new OptionParking(loyer);
        if (options.contains(Option.LAVERIE)) loyer = new OptionLaverie(loyer);
        if (options.contains(Option.FIBRE)) loyer = new OptionFibre(loyer, tarif.prixFibre());
        if (options.contains(Option.ASSURANCE)) loyer = new OptionAssurance(loyer);
        if (options.contains(Option.VELO)) loyer = new OptionLocalVelo(loyer);

        return loyer;
    }
}
