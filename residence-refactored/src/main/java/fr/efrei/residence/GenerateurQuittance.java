package fr.efrei.residence;

import java.util.stream.Collectors;

/**
 * Met en forme la quittance d'un mois de loyer.
 */
public class GenerateurQuittance {

    /**
     * @param mois format AAAA-MM
     */
    public String generer(Bail bail, String mois) {
        Paiement p = null;
        for (Paiement x : bail.paiements) {
            if (x.mois.equals(mois)) p = x; // le dernier paiement du mois l'emporte
        }
        if (p == null) {
            return "Aucun paiement pour " + mois;
        }
        Etudiant etudiant = bail.getEtudiant();
        Logement logement = bail.getLogement();
        String q = "";
        q += "==========================================\n";
        q += "   RÉSIDENCE CAMPUS VERT - QUITTANCE\n";
        q += "==========================================\n";
        q += "Bail      : " + bail.getId() + "\n";
        q += "Locataire : " + etudiant.getNomComplet() + " (" + etudiant.getProfil() + ")\n";
        q += "Logement  : " + logement.getNumero() + " - " + logement.getType() + " " + logement.getSurface() + " m²";
        if (logement.isMeuble()) q += " meublé";
        q += "\n";
        q += "Période   : " + mois + "\n";
        q += "Options   : " + listerOptions(bail) + "\n";
        q += "------------------------------------------\n";
        q += "Loyer HC  : " + Utils.euros(bail.getLoyerHorsCharges()) + "\n";
        q += "Charges   : " + Utils.euros(bail.getCharges()) + "\n";
        if (p.penalite > 0) {
            q += "Pénalité  : " + Utils.euros(p.penalite) + "\n";
        }
        q += "Total dû  : " + Utils.euros(p.du) + "\n";
        q += "Payé le   : " + Utils.date(p.date) + " : " + Utils.euros(p.montant) + "\n";
        if (p.montant < p.du) {
            q += "RESTE DÛ  : " + Utils.euros(Utils.arrondir(p.du - p.montant)) + "\n";
        }
        q += "==========================================";
        return q;
    }

    private String listerOptions(Bail bail) {
        if (bail.getOptions().isEmpty()) return "aucune";
        return bail.getOptions().stream().map(Option::getLibelle).collect(Collectors.joining(", "));
    }
}
