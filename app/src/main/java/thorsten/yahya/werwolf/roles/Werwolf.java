package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.WerwolfKillAction;

/**
 * Created by HipTeen on 12.07.2015.
 *
 * The bad guys that give the game its name. Every night they wake up and kill someone. And eat him.
 * Delicious!
 */
public class Werwolf extends RoleAbstract {

    private int peopleToKill = 1;
    private Role parentWerwolf;

    @Override
    public boolean hasNightAction() {
        return !hasParent() && (peopleToKill > 0);
    }

    @Override
    public int countPlayerForNightAction() {
        return 1;
    }

    @Override
    public void givePlayerRoleActions(List<Role> player) {
        for (Role role : player) {
            role.addAction(new WerwolfKillAction());
        }
        peopleToKill = 0;
    }

    @Override
    public String nightActionText() {
        return "Die Werwölfe erwachen und suchen sich " + peopleToKill + " Opfer aus.";
    }

    @Override
    public void setParent(Role parent) {
        this.parentWerwolf = parent;
    }

    @Override
    public boolean hasParent() {
        return parentWerwolf != null;
    }

    @Override
    public boolean isGrouped() {
        return true;
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        peopleToKill++;
    }
}
