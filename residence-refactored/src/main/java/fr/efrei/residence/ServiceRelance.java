package fr.efrei.residence;

/**
 * Relance les étudiants qui ont un solde impayé.
 */
public class ServiceRelance {

    private final BailRepository baux;
    private final ServiceNotification notification;

    public ServiceRelance(BailRepository baux, ServiceNotification notification) {
        this.baux = baux;
        this.notification = notification;
    }

    public void relancerImpayes() {
        System.out.println("--- Relance des impayés");
        for (Bail b : baux.tousLesBaux()) {
            if (b.soldeDu <= 0) continue;
            if (b.getStatut() == StatutBail.ANNULE) continue;
            notification.relancer(b.getEtudiant(), b.getId(), b.soldeDu);
        }
    }
}
