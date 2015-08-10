package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.RoleAction;

/**
 * Created by HipTeen on 12.07.2015.
 */
public interface Roles {

    boolean diesAfterNight();
    boolean diesAfterDay();

    void addAction(RoleAction action);
    void clearActions();

    boolean hasNightAction();
    int countPlayerForNightAction();

    void givePlayerRoleActions(List<Roles> player);

    String nightActionText();

    boolean isGrouped();
    boolean hasParent();
    void setParent(Roles parent);

    boolean isAlive();

    void kill();

    boolean isSet();
    void set();

    void endOfNight();

}
