package fr.efrei.residence;

/**
 * Seul endroit qui crée les logements.
 */
public final class FabriqueLogements {

    private static final int PLACES_MIN_COLOCATION = 2;

    private FabriqueLogements() {
    }

    /**
     * @param places        nombre de places, utilisé seulement pour une colocation
     * @param surfaceBalcon surface du balcon en m², utilisée seulement pour un T2
     */
    public static Logement creer(TypeLogement type, String numero, double surface, int etage, boolean meuble,
                                 int places, double surfaceBalcon) {
        return switch (type) {
            case STUDIO -> new Studio(numero, surface, etage, meuble);
            case T1 -> new T1(numero, surface, etage, meuble);
            case T2 -> new T2(numero, surface, etage, meuble, surfaceBalcon);
            case COLOCATION -> new Colocation(numero, surface, etage, meuble, Math.max(places, PLACES_MIN_COLOCATION));
            case DUPLEX -> new Duplex(numero, surface, etage, meuble);
        };
    }
}
