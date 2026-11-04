package fr.efrei.residence;

import java.util.Optional;

public enum TypeLogement {
    STUDIO, T1, T2, COLOCATION;

    public static Optional<TypeLogement> depuis(String texte) {
        for (TypeLogement type : values()) {
            if (type.name().equals(texte)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
