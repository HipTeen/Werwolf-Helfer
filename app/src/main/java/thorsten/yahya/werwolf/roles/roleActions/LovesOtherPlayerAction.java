package thorsten.yahya.werwolf.roles.roleActions;

import thorsten.yahya.werwolf.roles.Role;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class LovesOtherPlayerAction extends RoleActionAbstract {

    private Role myLover;

    public LovesOtherPlayerAction(Role myLover) {
        this.myLover = myLover;
    }

    public boolean myLoverDiesAfterNight() {
        return myLover.diesAfterNight();
    }

    public boolean myLoverDiesAfterDay() {
        return myLover.diesAfterDay();
    }

    @Override
    public boolean deleteAfterDay() {
        return false;
    }

    @Override
    public boolean deleteAfterNight() {
        return false;
    }
}
