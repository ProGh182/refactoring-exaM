package fr.efrei.residence;

/**
 * Trace d'une notification envoyée.
 *
 * @param destinataire adresse email, numéro de téléphone ou adresse postale
 * @param objet        sujet de l'email ou du courrier, texte complet du SMS
 * @param cout         coût de l'envoi (timbre pour un courrier)
 */
public record Envoi(Canal canal, String destinataire, String objet, double cout) {
}
