package thorsten.yahya.werwolf.test;

import android.test.InstrumentationTestCase;

import thorsten.yahya.werwolf.roles.Cupid;
import thorsten.yahya.werwolf.roles.Healer;
import thorsten.yahya.werwolf.roles.Hunter;
import thorsten.yahya.werwolf.roles.Judge;
import thorsten.yahya.werwolf.roles.Leper;
import thorsten.yahya.werwolf.roles.Raven;
import thorsten.yahya.werwolf.roles.Roles;
import thorsten.yahya.werwolf.roles.Seer;
import thorsten.yahya.werwolf.roles.VillagePeople;
import thorsten.yahya.werwolf.roles.Werwolf;
import thorsten.yahya.werwolf.roles.Witch;
import thorsten.yahya.werwolf.roles.roleActions.HealerHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WerwolfKillAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchHealAction;
import thorsten.yahya.werwolf.roles.roleActions.WitchKillAction;

/**
 * Created by HipTeen on 15.07.2015.
 */
public class RolesActionTest extends InstrumentationTestCase {

    public void testSeerDiesAfterNight() {
        testDiesAfterNightForRole(new Seer());
        testDiesAfterNightForRole(new Werwolf());
        testDiesAfterNightForRole(new Witch());
        testDiesAfterNightForRole(new VillagePeople());
        testDiesAfterNightForRole(new Healer());
        testDiesAfterNightForRole(new Leper());
        testDiesAfterNightForRole(new Judge());
        testDiesAfterNightForRole(new Hunter());
        testDiesAfterNightForRole(new Cupid());
        testDiesAfterNightForRole(new Raven());
        //BITCH
        //Cursed
    }

    private void testDiesAfterNightForRole(Roles role) {
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
