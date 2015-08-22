package thorsten.yahya.werwolf.roles;

import java.util.List;

import thorsten.yahya.werwolf.roles.roleActions.LovesOtherPlayerAction;

/**
 * Created by HipTeen on 15.07.2015.
 *
 * The cupid chooses two player in his first round who instantly will fall in love.
 * These two players are now connected, if one dies the other will die as well.
 */
public class Cupid extends RoleAbstract {

    private boolean hasUsedLoveArrows;

    @Override
    public boolean hasNightAction() {
        return !hasUsedLoveArrows;
    }

    @Override
    public int countPlayerForNightAction() {
        return 2;
    }

    @Override
    //Assert player.size == 2
    public void givePlayerRoleActions(List<Role> player) {
        Role firstLover = player.get(0);
        Role secondLover = player.get(1);
        firstLover.addAction(new LovesOtherPlayerAction(secondLover));
        secondLover.addAction(new LovesOtherPlayerAction(firstLover));
        hasUsedLoveArrows = true;
    }

    @Override
    public String nightActionText() {
        return "Amor sucht sie zwei Dorfbewohner aus, die sich sofort verlieben. Nun hängen ihre Leben aneinander."
                + "Stirbt einer, stirbt auch der andere. Durch ihre Liebe bedingt, beschuldigen sie sich nicht gegenseitig"
                + " und versuchen unter allen Umständen gemeinsam zu überleben, selbst wenn einer von Ihnen Werwolf ist.";
    }

    @Override
    public String toString() {
        return "Armor";
    }
}
