package fr.efrei.residence;

/**
 * Notification par l'application mobile, adressée au compte (email) de l'étudiant.
 */
public class PushService implements CanalNotification {

    private final JournalNotifications journal;

    public PushService(JournalNotifications journal) {
        this.journal = journal;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        pousser(etudiant.getEmail(), sujet);
    }

    @Override
    public void relancer(Etudiant etudiant, String idBail, double solde) {
        pousser(etudiant.getEmail(), "RELANCE - " + Utils.euros(solde) + " impayés sur " + idBail);
    }

    private void pousser(String compte, String sujet) {
        journal.enregistrer(new Envoi(Canal.PUSH, compte, sujet, 0));
        System.out.println("[PUSH] -> " + compte + " : " + sujet);
    }
}
