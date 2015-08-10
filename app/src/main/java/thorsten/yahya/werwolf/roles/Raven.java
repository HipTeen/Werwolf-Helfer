package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.RavenShitAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class Raven extends RolesAbstract {

    private boolean didShit;

    @Override
    public boolean hasNightAction() {
        return !didShit;
    }

    @Override
    public int countPlayerForNightAction() {
        return 1;
    }

    @Override
    public void givePlayerRoleActions(List<Roles> player) {
        player.get(0).addAction(new RavenShitAction());
        didShit = true;
    }

    @Override
    public String nightActionText() {
        return "Der Rabe kann sich einen Spieler aussuchen, der bei der Abstimmung am Tag" +
                " zwei stimmen gegen sicht hat.";
    }

    @Override
    public void endOfNight() {
        super.endOfNight();
        didShit = false;
    }


}
