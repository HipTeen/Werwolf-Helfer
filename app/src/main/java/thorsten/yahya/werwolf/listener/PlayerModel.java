package thorsten.yahya.werwolf.listener;

import java.util.ArrayList;
import java.util.List;

import thorsten.yahya.werwolf.roles.Roles;
import thorsten.yahya.werwolf.roles.UndeterminedPlayer;

/**
 * Created by HipTeen on 29.07.2015.
 */
public class PlayerModel {

    private List<PlayerCircleImageView> playerCircles;

    public PlayerModel() {
        playerCircles = new ArrayList<>();
    }

    public void addPlayerCircle(PlayerCircleImageView playerCircle) {
        playerCircles.add(playerCircle);
    }

    public void removePlayerCircle(PlayerCircleImageView playerCircle) {
        playerCircles.remove(playerCircle);
    }

    public void resetSelections() {
        for (PlayerCircleImageView playerCircle : playerCircles) {
            playerCircle.unselect();
        }
    }

    //TODO benutze getSelected
    public List<Roles> getSelectedRoles() {
        List<Roles> selectedRoles = new ArrayList<>();
        for (PlayerCircleImageView playerCircle : playerCircles) {
            if (playerCircle.isSelectedByPlayer()) {
                selectedRoles.add(playerCircle.getRole());
            }
        }
        return selectedRoles;
    }

    public List<PlayerCircleImageView> getSelected() {
        List<PlayerCircleImageView> selected = new ArrayList<>();
        for (PlayerCircleImageView playerCircle : playerCircles) {
            if (playerCircle.isSelectedByPlayer()) {
                selected.add(playerCircle);
            }
        }
        return selected;
    }

    public void givePlayerDummyRoles() {
        for (PlayerCircleImageView playerCircle : playerCircles) {
            playerCircle.setRole(new UndeterminedPlayer());
        }
    }

    public void updatePlayerColor() {
        for (PlayerCircleImageView playerCircle: playerCircles) {
            playerCircle.updateColor();
        }
    }
}
