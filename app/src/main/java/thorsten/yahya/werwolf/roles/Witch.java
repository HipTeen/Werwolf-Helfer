package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.WitchHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 12.07.2015.
 *
 * This is one powerful role. The witch has two potions, one for killing, one for healing,
 * that she can each use independently once throughout the whole game.
 */
public class Witch extends RoleAbstract {

    private boolean hasHealPotionPhasePassed = false;
    private boolean hasKillPotionPhasePassed = false;

    private boolean hasHealPotion = true;
    private boolean hasKillPotion = true;

    @Override
    public boolean hasNightAction() {
        return !hasHealPotionPhasePassed || !hasKillPotionPhasePassed;
    }

    @Override
    public int countPlayerForNightAction() {
        if (hasHealPotionPhasePassed && hasKillPotionPhasePassed) {
            return 0;
        } else if (hasHealPotion || hasKillPotion) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void givePlayerRoleActions(List<Role> player) {
        if (!hasHealPotionPhasePassed) {
            if (!player.isEmpty()) {
                player.get(0).addAction(new WitchHealAction());
                hasHealPotion = false;
            }
            hasHealPotionPhasePassed = true;
        } else {
            if (!player.isEmpty()) {
                player.get(0).addAction(new WitchKillAction());
                hasKillPotion = false;
            }
            hasKillPotionPhasePassed = true;
        }
    }

    @Override
    public String nightActionText() {
        if (!hasHealPotionPhasePassed) {
            return "Wenn die Hexe möchte, rettet sie das Werwolf Opfer";
        } else {
            return "Wenn die Hexe möchte, kann sie jemanden umbringen";
        }
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        hasHealPotionPhasePassed = false;
        hasKillPotionPhasePassed = false;
    }
}
