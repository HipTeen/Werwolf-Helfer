package thorsten.yahya.werwolf.roles;

import java.util.List;

/**
 * Created by HipTeen on 12.07.2015.
 */
public class Seer extends RolesAbstract{

    private boolean hasSeen;

    @Override
    public boolean hasNightAction() {
        return !hasSeen;
    }

    @Override
    public int countPlayerForNightAction() {
        return 0;
    }

    @Override
    public void givePlayerRoleActions(List<Roles> player) {
        hasSeen = true;
    }

    @Override
    public String nightActionText() {
        return "Die Seherin sucht sich einen Spieler aus, von dem sie erfährt, ob es sich um einen" +
                " Werwolf handelt.";
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        hasSeen = false;
    }
}
