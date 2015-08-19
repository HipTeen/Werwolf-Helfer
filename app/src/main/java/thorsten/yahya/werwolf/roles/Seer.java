package thorsten.yahya.werwolf.roles;

import java.util.List;

/**
 * Created by HipTeen on 12.07.2015.
 *
 * The seer wakes up in the night and can choose one person. He then will get to know if this one
 * is a werewolve or not (or if he is bad or not, if you have other evil roles like vampires).
 */
public class Seer extends RoleAbstract {

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
    public void givePlayerRoleActions(List<Role> player) {
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
