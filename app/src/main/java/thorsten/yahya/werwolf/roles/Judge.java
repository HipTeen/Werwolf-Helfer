package thorsten.yahya.werwolf.roles;

import java.util.List;

/**
 * Created by HipTeen on 15.07.2015.
 *
 * If the judge is still alive, not the majority chooses the victim at daytime;
 * only his vote counts.
 */
public class Judge extends RoleAbstract {

    private boolean wokeUp;

    @Override
    public boolean hasNightAction() {
        return !wokeUp;
    }

    @Override
    public void givePlayerRoleActions(List<Role> player) {
        super.givePlayerRoleActions(player);
        wokeUp = true;
    }
}
