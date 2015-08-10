package thorsten.yahya.werwolf.roles.roleActions;

import thorsten.yahya.werwolf.roles.Bitch;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class BitchSleepsWithAction extends RoleActionAbstract {

    private Bitch bitch;

    public BitchSleepsWithAction(Bitch bitch) {
        this.bitch = bitch;
    }

    public Bitch getBitch() {
        return bitch;
    }
}
