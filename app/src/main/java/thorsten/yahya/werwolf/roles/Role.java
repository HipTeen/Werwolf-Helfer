package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.RoleAction;

/**
 * Created by HipTeen on 12.07.2015.
 *
 * Interface for roles in the werewolf game. If you want to add a new role,
 * you have to create a class for this role that implements this class.
 * For convenience you might like to extend the {@link RoleAbstract}
 */
public interface Role {

    /**
     * Indicates if this role will die after the current night
     * @return true if the role will die
     */
    boolean diesAfterNight();

    /**
     * Indicates if this role will die after the current day
     * @return
     */
    boolean diesAfterDay();

    /**
     * Add an action to the queue of this role
     * @param action
     */
    void addAction(RoleAction action);

    /**
     * Delete all actions assigned to this role
     */
    void clearActions();

    /**
     * Indicates if this role has an action left to do this night
     * @return
     */
    boolean hasNightAction();

    /**
     * Number of players needed for the next action
     * @return
     */
    int countPlayerForNightAction();

    /**
     * Process current action and give the transferred roles an action
     * @param player list of roles that where marked for the action
     */
    void givePlayerRoleActions(List<Role> player);

    /**
     * Description of the current action
     * @return
     */
    String nightActionText();

    /**
     * Indicates if multiple roles of this kind wake up and act together, e.g. like the wolves
     * @return true if they wake up together
     */
    boolean isGrouped();

    /**
     * If the role is grouped, there is a need for a group leader. The one
     * without parent is the group leader
     * @return true if it has a parent
     */
    boolean hasParent();

    /**
     * Change the parent of this roles
     * @param parent the new parent
     */
    void setParent(Role parent);

    /**
     *
     * @return True if this role is still alive
     */
    boolean isAlive();

    /**
     * Kill this role
     */
    void kill();

    /**
     *
     * @return true if this role is already associated with a player
     */
    boolean isSet();

    /**
     * Change the isSet status to true
     */
    void set();

    /**
     * Notify this role about the end of the night
     */
    void endOfNight();

    /**
     *
     * @param undeterminedPlayer
     */
    void copyFromOtherRolePlayer(Role undeterminedPlayer);

    List<RoleAction> getActions();

    boolean hasWerewolfAction();
}
