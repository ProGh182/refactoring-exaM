package fr.efrei.residence;

public class SmsService implements CanalNotification {

    private static final int LONGUEUR_MAX = 160;
    private static final int LONGUEUR_MIN_NUMERO = 10;
    private static final String SUITE = "...";

    private final JournalNotifications journal;

    public SmsService(JournalNotifications journal) {
        this.journal = journal;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerSms(etudiant.getTelephone(), sujet + " - " + message);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double solde) {
        envoyerSms(etudiant.getTelephone(), "Campus Vert : " + Utils.euros(solde) + " impayés sur " + idBail);
    }

    private void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_MIN_NUMERO) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MAX) {
            message = message.substring(0, LONGUEUR_MAX - SUITE.length()) + SUITE;
        }
        journal.enregistrer(new Envoi(Canal.SMS, numero, message, 0));
        System.out.println("[SMS] -> " + numero + " : " + message);
    }
}
