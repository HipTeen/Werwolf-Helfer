package thorsten.yahya.werwolf.roles;

import java.util.List;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class Judge extends RolesAbstract {

    private boolean wokeUp;

    @Override
    public boolean hasNightAction() {
        return !wokeUp;
    }

    @Override
    public void givePlayerRoleActions(List<Roles> player) {
        super.givePlayerRoleActions(player);
        wokeUp = true;
    }
}
