package thorsten.yahya.werwolf.roles;

import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;
import thorsten.yahya.werwolf.roles.roleActions.LovesOtherPlayerAction;
import thorsten.yahya.werwolf.roles.roleActions.RoleAction;
import thorsten.yahya.werwolf.roles.roleActions.WerwolfKillAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class Cursed extends RolesAbstract {

    private boolean isWerwolf = false;
    private boolean wokeUp = false;

    @Override
    public boolean diesAfterNight() {
        if (locked) {
            return false;
        }
        locked = true;
        boolean werwolfKilled = false;
        boolean healerHealed = false;
        for (RoleAction action : actions) {
            if (action instanceof WitchHealAction) {
                locked = false;
                return false;
            } else if (action instanceof WitchKillAction) {
                //Assumption Witch only uses one Potion at one person
                locked = false;
                return true;
            } else if (action instanceof WerwolfKillAction) {
                werwolfKilled = true;
            } else if (action instanceof HealerHealAction) {
                healerHealed = true;
            } else if (action instanceof LovesOtherPlayerAction) {
                LovesOtherPlayerAction loveAction = (LovesOtherPlayerAction) action;
                if (loveAction.myLoverDiesAfterNight())
                    locked = false;
                    return true;
            }
        }
        boolean killedByWerwolf = !healerHealed && werwolfKilled;
        if (killedByWerwolf) {
            isWerwolf = true;
        }
        locked = false;
        return false;
    }

    @Override
    public boolean hasNightAction() {
        return !wokeUp;
    }

    public boolean isWerwolf() {
        return isWerwolf;
    }

    @Override
    public String nightActionText() {
        wokeUp = true;
        diesAfterNight();
        if (isWerwolf) {
            return "Der Verfluchte erwacht und erfährt, ob er sich verwandelt hat." +
                    "Anmerkung: Er ist mittlerweile ein Werwolf";
        } else {
            return "Der Verfluchte erwacht und erfährt, ob er sich verwandelt hat." +
                    "Anmerkung: Er ist noch kein Werwolf";
        }
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        wokeUp = false;
    }
}
