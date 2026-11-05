package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class EmailService implements CanalNotification {

    public static final String SMTP_HOST = "smtp.campusvert.fr";
    public static final String EXPEDITEUR = "gestion@campusvert.fr";

    // garde une trace des emails envoyés (demandé par l'administration)
    public static List<String[]> journal = new ArrayList<String[]>();

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerEmail(etudiant.getEmail(), sujet, message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double solde) {
        envoyerEmail(etudiant.getEmail(), "RELANCE - loyer impayé",
                "Bonjour " + etudiant.getPrenom() + ", il reste " + Utils.euros(solde) + " à régler sur le bail " + idBail + ".");
    }

    private boolean envoyerEmail(String destinataire, String sujet, String corps) {
        try {
            if (!Utils.emailValide(destinataire)) {
                throw new Exception("Adresse invalide");
            }
            System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
            System.out.println("        " + corps);
            journal.add(new String[]{destinataire, sujet, corps});
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
