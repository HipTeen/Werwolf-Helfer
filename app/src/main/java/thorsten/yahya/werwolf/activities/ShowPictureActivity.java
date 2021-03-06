package thorsten.yahya.werwolf.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thorsten.yahya.werwolf.R;
import thorsten.yahya.werwolf.controller.StateController;
import thorsten.yahya.werwolf.listViewUtil.Model;
import thorsten.yahya.werwolf.listener.PlayerCreateListener;
import thorsten.yahya.werwolf.listener.PlayerModel;
import thorsten.yahya.werwolf.roles.Role;
import thorsten.yahya.werwolf.roles.RoleFactory;
import thorsten.yahya.werwolf.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ShowPictureActivity extends Activity {
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
    private List<Model> models;
    private int maxPlayer;
    private List<Role> roles;
    private PlayerModel playerModel = new PlayerModel();
    private StateController stateController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show_picture);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        String path = (String) extras.get("path");
        models = extras.getParcelableArrayList("models");
        maxPlayer = countPlayers(models);
        roles = getRolesFromModels(models);
        contentView.setOnTouchListener(new PlayerCreateListener(this, maxPlayer, playerModel));
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
        stateController = new StateController(roles,playerModel,this);
        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateController.next();
            }
        });
    }

    private List<Role> getRolesFromModels(List<Model> models) {
        List<Role> roles = new ArrayList<>();
        for (Model model : models) {
            roles.addAll(getRolesFromModel(model));
        }
        return roles;
    }

    private int countPlayers(List<Model> models) {
        int count = 0;
        for (Model model : models) {
            count += model.getCount();
        }
        return count;
    }

    private List<Role> getRolesFromModel(Model model) {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < model.getCount(); i++) {
            Role previous = RoleFactory.createRole(model.getTitle());
            roles.add(previous);
        }
        return roles;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void toggleUi() {
        if (TOGGLE_ON_CLICK) {
            mSystemUiHider.toggle();
        } else {
            mSystemUiHider.show();
        }
    }

    public void notifyAbout(Role currentRole) {
        String text = currentRole.nightActionText();
        setInfoText(text);
    }

    public void setInfoText(String message) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void informEndGame(String winMessage) {
        setInfoText(winMessage);
    }
}
