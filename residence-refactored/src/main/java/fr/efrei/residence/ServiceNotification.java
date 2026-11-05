package fr.efrei.residence;

/**
 * Transmet les messages à un étudiant sur son canal préféré.
 */
public class ServiceNotification {

    private final FabriqueCanaux canaux;

    public ServiceNotification(FabriqueCanaux canaux) {
        this.canaux = canaux;
    }

    public void notifier(Etudiant e, String sujet, String message) {
        Canal canal = e.getCanalPrefere();
        canaux.pour(canal).envoyer(e, sujet, message);
        if (e.getStrategie().recoitCopieEmail() && canal != Canal.EMAIL) {
            canaux.pour(Canal.EMAIL).envoyer(e, "[EN] " + sujet, message);
        }
    }

    /** Les relances n'envoient pas de copie par email, comme dans le code historique. */
    public void relancer(Etudiant e, String idBail, double solde) {
        canaux.pour(e.getCanalPrefere()).relancer(e, idBail, solde);
    }
}
