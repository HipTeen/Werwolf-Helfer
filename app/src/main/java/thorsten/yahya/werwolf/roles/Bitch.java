package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.BitchSleepsWithAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class Bitch extends RolesAbstract {
    private boolean alreadyFuckedWithSomeone = false;

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
    public void givePlayerRoleActions(List<Roles> player) {
        player.get(0).addAction(new BitchSleepsWithAction(this));
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
}
