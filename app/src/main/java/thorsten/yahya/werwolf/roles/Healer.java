package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;

/**
 * Created by HipTeen on 15.07.2015.
 *
 * The healer chooses a player every night who won't die by werewolves. He can't choose
 * the same player two night in a row
 */
public class Healer extends RoleAbstract {

    private Role lastHealedPerson;
    private boolean hasHealed;

    @Override
    public boolean hasNightAction() {
        return !hasHealed;
    }

    @Override
    public int countPlayerForNightAction() {
        return 1;
    }

    @Override
    public void givePlayerRoleActions(List<Role> player) {
        Role healedPlayer = player.get(0);
        healedPlayer.addAction(new HealerHealAction());
        lastHealedPerson = healedPlayer;
        hasHealed = true;
    }

    @Override
    public String nightActionText() {
        return "Der Heiler sucht sich eine Person aus, die er heilen möchte."+
                " Er darf nicht die selbe Person zwei Runden hintereinander aussuchen!";
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        hasHealed = false;
    }
}
