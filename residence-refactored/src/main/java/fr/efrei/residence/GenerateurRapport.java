package fr.efrei.residence;

import java.util.EnumMap;
import java.util.Map;

/**
 * Produit le rapport d'activité de la résidence.
 */
public class GenerateurRapport {

    private final Residence residence;
    private final BailRepository baux;
    private final JournalNotifications journal;

    public GenerateurRapport(Residence residence, BailRepository baux, JournalNotifications journal) {
        this.residence = residence;
        this.baux = baux;
        this.journal = journal;
    }

    public String generer() {
        String r = "";
        r += "=============== RAPPORT " + Residence.NOM + " ===============" + "\n";
        Map<TypeLogement, Integer> placesParType = new EnumMap<>(TypeLogement.class);
        Map<TypeLogement, Integer> occupantsParType = new EnumMap<>(TypeLogement.class);
        int total = 0;
        int occupes = 0;
        double surfaceBalcons = 0;
        for (Logement l : residence.logements()) {
            placesParType.merge(l.getType(), l.getNombrePlaces(), Integer::sum);
            occupantsParType.merge(l.getType(), l.nombreOccupants(), Integer::sum);
            total += l.getNombrePlaces();
            occupes += l.nombreOccupants();
            surfaceBalcons += l.getSurfaceBalcon();
        }
        for (TypeLogement type : TypeLogement.values()) {
            if (!placesParType.containsKey(type)) continue; // un type absent du parc n'a pas de ligne
            r += type.getLibelleRapport() + occupantsParType.getOrDefault(type, 0) + "/"
                    + placesParType.getOrDefault(type, 0) + type.getUniteRapport() + "\n";
        }
        r += "Occupation   : " + Math.round(occupes * 100.0 / total) + " %" + "\n";
        r += "Balcons      : " + surfaceBalcons + " m²" + "\n";

        double loyersMensuels = 0;
        double impayes = 0;
        double cautions = 0;
        for (Bail b : baux.tousLesBaux()) {
            if (b.getStatut() == StatutBail.ACTIF || b.getStatut() == StatutBail.PREAVIS) {
                loyersMensuels += b.getLoyerChargesComprises();
                cautions += b.getCaution();
            }
            if (b.soldeDu > 0) impayes += b.soldeDu;
        }
        r += "Loyers CC    : " + Utils.euros(Utils.arrondir(loyersMensuels)) + " / mois" + "\n";
        r += "Cautions     : " + Utils.euros(Utils.arrondir(cautions)) + "\n";
        r += "Impayés      : " + Utils.euros(Utils.arrondir(impayes)) + "\n";
        r += "SMS envoyés  : " + journal.nombre(Canal.SMS) + "\n";
        r += "Timbres      : " + Utils.euros(Utils.arrondir(journal.coutTotal())) + "\n";
        r += "============================================================";
        return r;
    }
}
