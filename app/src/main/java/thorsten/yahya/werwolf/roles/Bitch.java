package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.BitchSleepsWithAction;
import thorsten.yahya.werwolf.roles.roleActions.LovesOtherPlayerAction;
import thorsten.yahya.werwolf.roles.roleActions.RoleAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 15.07.2015.
 *
 * This role sleeps every night with another player. If the werewolfes try to kill the bitch,
 * they won't find here in her bed and thus she won't die. If they kill her one-night-stand,
 * both will die. If her ons is healed, she will still die!
 */
public class Bitch extends RoleAbstract {
    private boolean alreadyFuckedWithSomeone = false;
    private Role lastLover;
    private boolean locked = false;

    @Override
    public boolean hasNightAction() {
        return !alreadyFuckedWithSomeone;
    }

    @Override
    public int countPlayerForNightAction() {
        return 1;
    }

    @Override
    //Assert: player.size == 1
    public void givePlayerRoleActions(List<Role> player) {
        Role tonightLover = player.get(0);
        tonightLover.addAction(new BitchSleepsWithAction(this));
        lastLover = tonightLover;
        alreadyFuckedWithSomeone = true;
    }

    @Override
    public String nightActionText() {
        return "Die Dorfschlampe sucht sich ein Dorfbewohner, bei dem sie heute Nacht schläft."
                + "Wenn dieser Dorfbewohner stirbt, stirbt sie auch. Sollte sie von den Werwölfen " +
                "gebissen werden, überlebt sie.";
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        alreadyFuckedWithSomeone = false;
    }

    @Override
    public boolean diesAfterNight() {
        if (locked) {
            return false;
        }
        locked = true;
        for (RoleAction action : actions) {
            if (action instanceof LovesOtherPlayerAction) {
                if (((LovesOtherPlayerAction) action).myLoverDiesAfterNight()) {
                    locked = false;
                    return true;
                }
            } else if (action instanceof WitchKillAction) {
                locked = false;
                return true;
            }
        }
        locked = false;
        return lastLover != null && lastLover.hasWerewolfAction();
    }
}
