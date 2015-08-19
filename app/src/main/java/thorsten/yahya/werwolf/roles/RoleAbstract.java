package thorsten.yahya.werwolf.roles;

import java.util.ArrayList;
import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;
import thorsten.yahya.werwolf.roles.roleActions.LovesOtherPlayerAction;
import thorsten.yahya.werwolf.roles.roleActions.RoleAction;
import thorsten.yahya.werwolf.roles.roleActions.VoteKillAction;
import thorsten.yahya.werwolf.roles.roleActions.WerwolfKillAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 14.07.2015.
 *
 * Convenience class that gives default behaviour.
 * You should extend this class if you add a new role and overwrite selected methods to
 * change the behaviour
 */
public abstract class RoleAbstract implements Role {

    protected boolean isAlive = true;

    protected List<RoleAction> actions = new ArrayList<>();

    protected boolean locked;

    private boolean set;

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
                if (loveAction.myLoverDiesAfterNight()) {
                    locked = false;
                    return true;
                }
            }
        }
        locked = false;
        return !healerHealed && werwolfKilled;
    }

    @Override
    public void addAction(RoleAction action) {
        actions.add(action);
    }

    @Override
    public boolean diesAfterDay() {
        if (locked) {
            return false;
        }
        locked = true;
        for (RoleAction action : actions) {
            if (action instanceof VoteKillAction) {
                locked = false;
                return true;
            }
            if (action instanceof LovesOtherPlayerAction) {
                if (((LovesOtherPlayerAction) action).myLoverDiesAfterDay()) {
                    locked = false;
                    return true;
                }
            }
        }
        locked = false;
        return false;
    }

    @Override
    public void clearActions() {
        actions.clear();
    }

    @Override
    public boolean hasNightAction() {
        return false;
    }

    @Override
    public int countPlayerForNightAction() {
        return 0;
    }

    @Override
    public void givePlayerRoleActions(List<Role> player) {}

    @Override
    public String nightActionText() {
        return "";
    }

    @Override
    public void setParent(Role parent) {}

    @Override
    public boolean hasParent() {
        return false;
    }

    @Override
    public boolean isGrouped() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isAlive = false;
    }

    @Override
    public void copyFromOtherRolePlayer(Role undeterminedPlayer) {
        this.actions = undeterminedPlayer.getActions();
    }

    @Override
    public void set() {
        set = true;
    }

    @Override
    public boolean isSet() {
        return set;
    }

    @Override
    public void endOfNight() {
        List<RoleAction> newActions = new ArrayList<>();
        for (RoleAction action : actions) {
            if (!action.deleteAfterNight()) {
                newActions.add(action);
            }
        }
        actions = newActions;
    }

    @Override
    public List<RoleAction> getActions() {
        return actions;
    }

    @Override
    public boolean hasWerewolfAction() {
        boolean hasWerewolfKillAction = false;
        for (RoleAction action : actions) {
            hasWerewolfKillAction |= action instanceof WerwolfKillAction;
        }
        return hasWerewolfKillAction;
    }
}
