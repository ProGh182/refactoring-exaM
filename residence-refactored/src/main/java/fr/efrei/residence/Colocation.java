package fr.efrei.residence;

/**
 * Colocation : plusieurs places, parties communes nettoyées au départ d'un colocataire.
 */
public class Colocation extends Logement {

    public Colocation(String numero, double surface, int etage, boolean meuble, int places) {
        super(numero, TypeLogement.COLOCATION, new TarifColocation(), surface, etage, meuble, places);
    }

    @Override
    public void apresDepart() {
        System.out.println("[ENTRETIEN] Nettoyage des parties communes de la colocation " + numero);
    }
}
