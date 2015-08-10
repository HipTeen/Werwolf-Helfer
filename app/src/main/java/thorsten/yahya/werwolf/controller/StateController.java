package thorsten.yahya.werwolf.controller;

import java.util.List;

import thorsten.yahya.werwolf.activities.ShowPictureActivity;
import thorsten.yahya.werwolf.listener.PlayerCircleImageView;
import thorsten.yahya.werwolf.listener.PlayerModel;
import thorsten.yahya.werwolf.roles.Roles;
import thorsten.yahya.werwolf.roles.Werwolf;
import thorsten.yahya.werwolf.roles.roleActions.VoteKillAction;

/**
 * Created by HipTeen on 29.07.2015.
 */
public class StateController {

    private static final int INITIALIZE = 0;
    private static final int NIGHT_FIRST = 1;
    private static final int NIGHT_LATER = 2;
    private static final int DAY_START = 3;
    private static final int DAY_END = 4;
    private static final int CHOOSE_ROLE_NIGHT_FIRST = 5;
    private static final int CHOOSE_ROLE_NIGHT_LATER = 6;
    private int state;
    private int pos;
    private Roles currentRole;
    private Roles nextRole;
    private ShowPictureActivity showPictureActivity;

    private PlayerModel playerModel;
    private List<Roles> roles;

    public StateController(List<Roles> roles, PlayerModel playerModel, ShowPictureActivity showPictureActivity) {
        this.roles = roles;
        this.playerModel = playerModel;
        this.showPictureActivity = showPictureActivity;
    }

    public void next() {
        switch (state) {
            case INITIALIZE:
                playerModel.givePlayerDummyRoles();
            case NIGHT_FIRST:
                if (hasNextRole()) {
                    playerModel.resetSelections();
                    currentRole = nextRole;
                    if (currentRole.isSet()) {
                        showPictureActivity.notifyAbout(currentRole);
                        state = NIGHT_LATER;
                    } else {
                        showPictureActivity.setInfoText("Bitte " + currentRole.toString() + " auswählen");
                        state = CHOOSE_ROLE_NIGHT_FIRST;
                    }
                } else {
                    state = DAY_START;
                    next();
                }
                break;
            case CHOOSE_ROLE_NIGHT_FIRST:
                connectPlayerAndCircle();
                state = NIGHT_FIRST;
                next();
                break;
            case CHOOSE_ROLE_NIGHT_LATER:
                connectPlayerAndCircle();
                state = NIGHT_FIRST;
                next();
                break;
            case NIGHT_LATER:
                currentRole.givePlayerRoleActions(playerModel.getSelectedRoles());
                playerModel.resetSelections();
                if (hasNextRole()) {
                    currentRole = nextRole;
                    if (currentRole.isSet()) {
                        showPictureActivity.notifyAbout(currentRole);
                        state = NIGHT_LATER;
                    } else {
                        showPictureActivity.setInfoText("Bitte " + currentRole.toString() + " auswählen");
                        state = CHOOSE_ROLE_NIGHT_LATER;
                    }
                } else {
                    state = DAY_START;
                    next();
                }
                break;
            case DAY_START:
                killAfterNight();
                if (checkWin()) {
                    return;
                }
                endRoleNight();
                showPictureActivity.setInfoText("Abstimmung startet");
                state = DAY_END;
                break;
            case DAY_END:
                killVotes();
                killAfterDay();
                if (checkWin()) {
                    return;
                }
                showPictureActivity.setInfoText("Das Dorf schläft ein");
                state = NIGHT_FIRST;
                break;
        }
        playerModel.updatePlayerColor();
    }



    private void endRoleNight() {
        for (Roles role : roles) {
            role.endOfNight();
        }
    }

    private boolean checkWin() {
        int alivePlayer = countAliveRoles(roles);
        int aliveWerwolf = countAliveWerwolf(roles);
        if (aliveWerwolf == 0 && alivePlayer == 0) {
            showPictureActivity.informEndGame("Unentschieden!!");
            return true;
        } else if (aliveWerwolf == 0) {
            showPictureActivity.informEndGame("Das Dorf hat gewonnen");
            return true;
        } else if(alivePlayer == aliveWerwolf) {
            showPictureActivity.informEndGame("Die Werwölfe haben gewonnen");
            return true;
        }
        return false;
    }

    private int countAliveWerwolf(List<Roles> roles) {
        int aliveWerwolfes = 0;
        for (Roles role : roles) {
            if (role.isAlive() && role instanceof Werwolf) {
                aliveWerwolfes++;
            }
        }
        return aliveWerwolfes;
    }

    private int countAliveRoles(List<Roles> roles) {
        int alivePlayer = 0;
        for (Roles role : roles) {
            if (role.isAlive()) {
                alivePlayer++;
            }
        }
        return alivePlayer;
    }

    private void connectPlayerAndCircle() {
        List<PlayerCircleImageView> selected = playerModel.getSelected();
        Roles role = null;
        for (int i = 0; i < selected.size(); i++) {
            Roles oldRole = role;
            role = this.roles.get(pos + i);
            selected.get(i).setRole(role);
            role.set();
            role.setParent(oldRole);
        }
        playerModel.resetSelections();
    }

    private void killAfterNight() {
        for (Roles role : roles) {
            if (role.diesAfterNight()) {
                role.kill();
            }
        }
    }

    private void killAfterDay() {
        for (Roles role : roles) {
            if (role.diesAfterDay()) {
                role.kill();
            }
        }
    }

    private void killVotes() {
        for (Roles selected : playerModel.getSelectedRoles()) {
            selected.addAction(new VoteKillAction());
        }
    }

    private boolean hasNextRole() {
        while (pos < roles.size()) {
            Roles role = this.roles.get(pos);
            if (role.hasNightAction()) {
                nextRole = role;
                return true;
            }
            pos++;
        }
        return false;
    }

}
