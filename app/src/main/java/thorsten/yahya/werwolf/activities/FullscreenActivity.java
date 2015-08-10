package thorsten.yahya.werwolf.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import thorsten.yahya.werwolf.R;
import thorsten.yahya.werwolf.listViewUtil.Model;
import thorsten.yahya.werwolf.listViewUtil.MyAdapter;
import thorsten.yahya.werwolf.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends ActionBarActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private List<String> selectedItems = new ArrayList<>();
    private List<Model> modelsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        ListView listView = (ListView) findViewById(R.id.listView);

        modelsArrayList = generateData();
        MyAdapter adapter = new MyAdapter(this, modelsArrayList);

        listView.setAdapter(adapter);
    }

    private List<Model> generateData() {
        return new ArrayList<>(Arrays.asList(new Model("Amor"), new Model("Heiler"),
                new Model("Dorfschlampe"), new Model("Werwolf"), new Model("Dorfbewohner"),
                new Model("Aussätzige"), new Model("Hexe"), new Model("Seherin"),
                new Model("Verfluchter"), new Model("Rabe"), new Model("Richter"), new Model("Jäger")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fullscreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok_button:
                openUpCamera(null);
                return true;
            default:
                return false;
        }
    }

    public void openUpCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putParcelableArrayListExtra("models", (ArrayList<Model>) modelsArrayList);
        startActivityForResult(intent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
