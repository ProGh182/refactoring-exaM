package fr.efrei.residence;

/**
 * Seul endroit qui crée les services de notification.
 */
public final class FabriqueCanaux {

    private FabriqueCanaux() {
    }

    public static CanalNotification pour(Canal canal) {
        return switch (canal) {
            case EMAIL -> new EmailService();
            case SMS -> new SmsService();
            case COURRIER -> new CourrierService();
        };
    }
}
