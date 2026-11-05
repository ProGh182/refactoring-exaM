package fr.efrei.residence;

import java.util.Optional;

public enum TypeLogement {
    STUDIO("Studios      : ", ""),
    T1("T1           : ", ""),
    T2("T2           : ", ""),
    COLOCATION("Colocation   : ", " places");

    private final String libelleRapport;
    private final String uniteRapport;

    TypeLogement(String libelleRapport, String uniteRapport) {
        this.libelleRapport = libelleRapport;
        this.uniteRapport = uniteRapport;
    }

    public String getLibelleRapport() {
        return libelleRapport;
    }

    public String getUniteRapport() {
        return uniteRapport;
    }

    public static Optional<TypeLogement> depuis(String texte) {
        for (TypeLogement type : values()) {
            if (type.name().equals(texte)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
