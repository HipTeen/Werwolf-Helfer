package thorsten.yahya.werwolf.roles.roleActions;

/**
 * Created by HipTeen on 15.07.2015.
 */
public abstract class RoleActionAbstract implements RoleAction {

    @Override
    public boolean deleteAfterDay() {
        return true;
    }

    @Override
    public boolean deleteAfterNight() {
        return true;
    }
}
