package fr.efrei.residence;

public class EmailService implements CanalNotification {

    public static final String SMTP_HOST = "smtp.campusvert.fr";
    public static final String EXPEDITEUR = "gestion@campusvert.fr";

    private final JournalNotifications journal;

    public EmailService(JournalNotifications journal) {
        this.journal = journal;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerEmail(etudiant.getEmail(), sujet, message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double solde) {
        envoyerEmail(etudiant.getEmail(), "RELANCE - loyer impayé",
                "Bonjour " + etudiant.getPrenom() + ", il reste " + Utils.euros(solde) + " à régler sur le bail " + idBail + ".");
    }

    /** Une adresse invalide n'envoie rien, sans exception. */
    private void envoyerEmail(String destinataire, String sujet, String corps) {
        if (!Utils.emailValide(destinataire)) {
            return;
        }
        System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.enregistrer(new Envoi(Canal.EMAIL, destinataire, sujet, 0));
    }
}
