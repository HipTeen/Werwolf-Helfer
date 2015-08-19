package thorsten.yahya.werwolf.roles;

/**
 * Created by HipTeen on 29.07.2015.
 *
 * Factory class for roles
 */
public class RoleFactory {
    public static Role createRole(String title) {
        switch (title) {
            case "Amor":
                return new Cupid();
            case "Heiler":
                return new Healer();
            case "Dorfschlampe":
                return new Bitch();
            case "Werwolf":
                return new Werwolf();
            case "Dorfbewohner":
                return new VillagePeople();
            case "Aussätzige":
                return new Leper();
            case "Hexe":
                return new Witch();
            case "Seherin":
                return new Seer();
            case "Verfluchter":
                return new Cursed();
            case "Rabe":
                return new Raven();
            case "Jäger":
                return new Hunter();
            case "Richter":
                return new Judge();
            default:
                throw new RuntimeException("Unknown Role: " + title);
        }
    }
}
