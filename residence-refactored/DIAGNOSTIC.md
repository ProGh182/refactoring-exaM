# Diagnostic du code historique

Les emplacements renvoient aux fichiers de `residence-legacy/src/main/java/fr/efrei/residence`.

| Défaut | Emplacement | Principe SOLID violé |
|---|---|---|
| Une seule classe gère les inscriptions, l'attribution, le cycle de vie du bail, les relances, le rapport et les notifications | `GestionResidence.java:10-421` | **S** – Responsabilité unique |
| Un nouveau type de logement oblige à modifier la cascade de `if` du loyer de base | `CalculateurLoyer.java:12-23` | **O** – Ouvert/fermé |
| `Colocation` hérite de `loger()` et `liberer()` mais lève une exception à leur appel | `Colocation.java:17-24` | **L** – Substitution de Liskov |
| L'interface impose à tous les logements les méthodes de colocation, de balcon et d'entretien | `ILogement.java:31-43` | **I** – Ségrégation des interfaces |
| Le service récupère un singleton et crée lui-même son calculateur | `GestionResidence.java:14-15`, `Database.java:22-27` | **D** – Inversion des dépendances |

## Palier 7 – Preuve par l'extension

Les fichiers ci-dessous sont dans `residence-refactored/src`.

| Extension | Nouvelles classes | Test unitaire |
|---|---|---|
| Type `DUPLEX` (12,50 €/m² + 80 €, 1 occupant, pas de balcon) | `Duplex`, `TarifDuplex` | `DuplexTest` |
| Option local vélo (+6 €, après l'assurance) | `OptionLocalVelo` | `OptionLocalVeloTest` |
| Profil `SPORTIF` (remise 15 %, solvabilité 2×, frais 50 €, préavis 15 jours) | `ProfilSportif` | `ProfilSportifTest` |
| Canal `PUSH` (`[PUSH] -> email : sujet`) | `PushService` | `PushServiceTest` |

**Fichiers existants modifiés : 10** (9 dans le code, 1 dans les tests).

| Fichier | Modification | Nature |
|---|---|---|
| `TypeLogement`, `Option`, `Profil`, `Canal` | Une valeur ajoutée à chaque enum | Liste des valeurs possibles |
| `FabriqueLogements`, `FabriqueProfils`, `FabriqueCanaux` | Un `case` ajouté | Fabriques |
| `AssembleurLoyer` | Une ligne pour le local vélo | Assemblage |
| `GenerateurRapport` | Une ligne : un type absent du parc n'a pas de ligne | Seule modification hors assemblage, nécessaire pour que le rapport initial reste identique ; les prochains types n'auront plus besoin de la modifier |
| `NouveauSystemeAdapter` (test) | Les envois `PUSH`, inconnus du contrat, ne sont pas transmis aux tests | Traduction |

Aucun service n'a été modifié : `ServiceAttribution`, `ServiceBail`, `ServiceNotification`, `CalculateurLoyer` et les autres classes existantes sont restés tels quels.

**Sortie de `Main` :** elle diffère maintenant de l'ancien code sur deux points, attendus. Le duplex de `Main` est accepté (`Logement ajouté : C003 (DUPLEX)` au lieu de `Type de logement inconnu : DUPLEX`), et le rapport affiche une ligne `Duplex       : 0/1`. Les 101 tests de contrat restent verts.
