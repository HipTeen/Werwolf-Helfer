package thorsten.yahya.werwolf.test;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import thorsten.yahya.werwolf.roles.Bitch;
import thorsten.yahya.werwolf.roles.Cupid;
import thorsten.yahya.werwolf.roles.Healer;
import thorsten.yahya.werwolf.roles.Hunter;
import thorsten.yahya.werwolf.roles.Judge;
import thorsten.yahya.werwolf.roles.Leper;
import thorsten.yahya.werwolf.roles.Raven;
import thorsten.yahya.werwolf.roles.Role;
import thorsten.yahya.werwolf.roles.Seer;
import thorsten.yahya.werwolf.roles.UndeterminedPlayer;
import thorsten.yahya.werwolf.roles.VillagePeople;
import thorsten.yahya.werwolf.roles.Werwolf;
import thorsten.yahya.werwolf.roles.Witch;
import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;
import thorsten.yahya.werwolf.roles.roleActions.LovesOtherPlayerAction;
import thorsten.yahya.werwolf.roles.roleActions.WerwolfKillAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class RoleActionTest extends InstrumentationTestCase {

    public void testDiesAfterNight() {
        testDiesAfterNightForBitch();
        testDiesAfterNightForStandardRole(new Cupid());
        //testDiesAfterNightForCursed();
        testDiesAfterNightForStandardRole(new Healer());
        testDiesAfterNightForStandardRole(new Hunter());
        testDiesAfterNightForStandardRole(new Judge());
        testDiesAfterNightForStandardRole(new Leper());
        testDiesAfterNightForStandardRole(new Raven());
        testDiesAfterNightForStandardRole(new Seer());
        testDiesAfterNightForStandardRole(new UndeterminedPlayer());
        testDiesAfterNightForStandardRole(new VillagePeople());
        testDiesAfterNightForStandardRole(new Werwolf());
        testDiesAfterNightForStandardRole(new Witch());
    }

    private void testDiesAfterNightForBitch() {
        Bitch bitch = new Bitch();

        //No Death without action
        assertFalse(bitch.diesAfterNight());

        //Won't die if she get killed
        bitch.addAction(new WerwolfKillAction());
        assertFalse(bitch.diesAfterNight());

        //Will die if her one night stand is killed
        bitch.clearActions();
        VillagePeople villagePeople = new VillagePeople();
        villagePeople.addAction(new WerwolfKillAction());
        List<Role> tempList = new ArrayList<>();
        tempList.add(villagePeople);
        bitch.givePlayerRoleActions(tempList);
        assertTrue(bitch.diesAfterNight());
        //Healing here one night stand won't help her
        villagePeople.addAction(new WitchHealAction());
        assertTrue(bitch.diesAfterNight());

        //Bitch get killed by witch
        bitch.clearActions();
        bitch.addAction(new WitchKillAction());
        assertTrue(bitch.diesAfterNight());

        //Bitch is affected by her lovers dead
        bitch.clearActions();
        villagePeople.clearActions();
        villagePeople.addAction(new LovesOtherPlayerAction(bitch));
        bitch.addAction(new LovesOtherPlayerAction(villagePeople));
        villagePeople.addAction(new WerwolfKillAction());
        assertTrue(bitch.diesAfterNight());



    }

    private void testDiesAfterNightForStandardRole(Role role) {
        //No Death without action
        assertFalse(role.diesAfterNight());

        //Death after Werwolf Action
        role.addAction(new WerwolfKillAction());
        assertTrue(role.diesAfterNight());

        //Heal Potion of Witch
        role.addAction(new WitchHealAction());
        assertFalse(role.diesAfterNight());

        role.clearActions();
        //No Matter if first Witch Heals oder Werwol kills
        role.addAction(new WitchHealAction());
        role.addAction(new WerwolfKillAction());
        assertFalse(role.diesAfterNight());

        role.clearActions();
        //Death Potion of Witch kills
        role.addAction(new WitchKillAction());
        assertTrue(role.diesAfterNight());

        //Witch kill Action and Werwolf kill together still kills
        role.addAction(new WerwolfKillAction());
        assertTrue(role.diesAfterNight());

        role.clearActions();
        //Healer prevent kill
        role.addAction(new WerwolfKillAction());
        role.addAction(new HealerHealAction());
        assertFalse(role.diesAfterNight());

        //Kill Action of witcher kills also alone
        role.addAction(new WitchKillAction());
        assertTrue(role.diesAfterNight());
    }
}
