package thorsten.yahya.werwolf.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import thorsten.yahya.werwolf.roles.Role;

/**
 * Created by HipTeen on 28.07.2015.
 */
public class PlayerCircleImageView extends ImageView {
    public static final BitmapDrawable yellow = new BitmapDrawable(createCircle(Color.YELLOW));
    public static final BitmapDrawable green = new BitmapDrawable(createCircle(Color.GREEN));
    public static final BitmapDrawable red = new BitmapDrawable(createCircle(Color.RED));



    private boolean selectedByPlayer = false;
    private PlayerCreateListener parent;
    private Role role;

    public PlayerCircleImageView(final Context context, final PlayerCreateListener parent) {
        super(context);
        this.setBackgroundDrawable(yellow);
        final ImageView iv = this;
        this.parent = parent;
        this.setOnTouchListener(new OnSwipeTouchListener(context) {

            @Override
            public void onSingleTap(MotionEvent event) {
                toggleColor();
            }

            @Override
            public void onLongTap(MotionEvent event) {
                iv.setVisibility(INVISIBLE);
                parent.incrementMaxPlayer();
            }
        });
    }

    private void toggleColor() {
        selectedByPlayer = !selectedByPlayer;
        updateColor();
    }

    public void updateColor() {
        if (role.isAlive()) {
            if (selectedByPlayer) {
                this.setBackgroundDrawable(green);
            } else {
                this.setBackgroundDrawable(yellow);
            }
        } else {
            this.setBackgroundDrawable(red);
        }
    }

    private static Bitmap createCircle(int color) {
        Bitmap bmp = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        c.drawCircle(100, 100, 100, paint);
        return bmp;
    }

    public PlayerCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isSelectedByPlayer() {
        return selectedByPlayer;
    }

    public void unselect() {
        selectedByPlayer = false;
        updateColor();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
