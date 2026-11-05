package fr.efrei.residence;

/**
 * Seul endroit qui crée les logements.
 */
public final class FabriqueLogements {

    private static final int PLACES_MIN_COLOCATION = 2;

    private FabriqueLogements() {
    }

    /**
     * @param param nombre de places d'une colocation ou surface du balcon d'un T2, ignoré pour les autres types
     */
    public static Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        return switch (type) {
            case STUDIO -> new Studio(numero, surface, etage, meuble);
            case T1 -> new T1(numero, surface, etage, meuble);
            case T2 -> new T2(numero, surface, etage, meuble, param);
            case COLOCATION -> new Colocation(numero, surface, etage, meuble, Math.max(param, PLACES_MIN_COLOCATION));
        };
    }
}
