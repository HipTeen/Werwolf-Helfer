package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class Healer extends RolesAbstract {

    private Roles lastHealedPerson;
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
    public void givePlayerRoleActions(List<Roles> player) {
        Roles healedPlayer = player.get(0);
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
