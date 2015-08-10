package thorsten.yahya.werwolf.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import thorsten.yahya.werwolf.R;
import thorsten.yahya.werwolf.activities.ShowPictureActivity;

/**
 * Created by HipTeen on 28.07.2015.
 */
public class PlayerCreateListener extends OnSwipeTouchListener {
    private ShowPictureActivity pictureActivity;
    private int playerLeft;
    private PlayerModel playerModel;


    public PlayerCreateListener(ShowPictureActivity c, int maxPlayer, PlayerModel playerModel) {
        super(c);
        pictureActivity = c;
        this.playerLeft = maxPlayer;
        this.playerModel = playerModel;
    }

    @Override
    public void onSwipeDown() {
        pictureActivity.toggleUi();
    }

    @Override
    public void onSingleTap(MotionEvent event) {
        if (playerLeft > 0) {
            createPlayerCircle(event);
            playerLeft--;
        }
    }

    private void createPlayerCircle(MotionEvent event) {
        RelativeLayout relativeLayout = (RelativeLayout) pictureActivity.findViewById(R.id.relLay);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.leftMargin = (int) event.getRawX() - 100;
        params.topMargin = (int) event.getRawY() - 100;

        PlayerCircleImageView img = new PlayerCircleImageView(pictureActivity, this);
        relativeLayout.addView(img, params);
        playerModel.addPlayerCircle(img);
    }

    public void incrementMaxPlayer() {
        playerLeft++;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return super.onTouch(view, motionEvent);
    }
}
