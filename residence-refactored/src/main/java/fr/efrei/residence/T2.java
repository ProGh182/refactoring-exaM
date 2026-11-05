package fr.efrei.residence;

/**
 * T2 : 1 occupant, balcon éventuel.
 */
public class T2 extends Logement {

    private final double surfaceBalcon;

    public T2(String numero, double surface, int etage, boolean meuble, double surfaceBalcon) {
        super(numero, TypeLogement.T2, new TarifT2(), surface, etage, meuble, 1);
        this.surfaceBalcon = surfaceBalcon;
    }

    @Override
    public double getSurfaceBalcon() {
        return surfaceBalcon;
    }
}
