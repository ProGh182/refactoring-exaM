package fr.efrei.residence;

/**
 * T1 : 1 occupant, pas de balcon.
 */
public class T1 extends Logement {

    public T1(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.T1, new TarifT1(), surface, etage, meuble, 1);
    }
}
