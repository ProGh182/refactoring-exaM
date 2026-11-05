package fr.efrei.residence;

/**
 * Studio : 1 occupant, pas de balcon.
 */
public class Studio extends Logement {

    public Studio(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.STUDIO, new TarifStudio(), surface, etage, meuble, 1);
    }
}
