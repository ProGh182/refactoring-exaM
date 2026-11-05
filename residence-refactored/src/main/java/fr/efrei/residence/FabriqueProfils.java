package fr.efrei.residence;

/**
 * Seul endroit qui choisit la stratégie d'un profil.
 */
public final class FabriqueProfils {

    private FabriqueProfils() {
    }

    /**
     * @param echelon échelon de bourse, ignoré si le profil n'est pas boursier
     */
    public static StrategieProfil pour(Profil profil, int echelon) {
        return switch (profil) {
            case STANDARD -> new ProfilStandard();
            case BOURSIER -> new ProfilBoursier(echelon);
            case ALTERNANT -> new ProfilAlternant();
            case INTERNATIONAL -> new ProfilInternational();
            case SPORTIF -> new ProfilSportif();
        };
    }
}
