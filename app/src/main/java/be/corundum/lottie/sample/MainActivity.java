package be.corundum.lottie.sample;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton originalButton;
    private AppCompatButton partialButton;
    private AppCompatButton fullButton;
    private AppCompatButton animateButton;
    private LottieAnimationView animationView;
    private AppCompatTextView stateText;

    private final int frames = 9;
    private int currentAnimationFrame = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalButton = findViewById(R.id.action_original);
        partialButton = findViewById(R.id.action_layer_only);
        fullButton = findViewById(R.id.action_full);
        animateButton = findViewById(R.id.action_animate);
        animationView = findViewById(R.id.animation_view);
        stateText = findViewById(R.id.state_label);

        originalButton.setOnClickListener(this);
        partialButton.setOnClickListener(this);
        fullButton.setOnClickListener(this);
        animateButton.setOnClickListener(this);

        playOriginal();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_original:
                playOriginal();
                break;
            case R.id.action_layer_only:
                playLayerOnly();
                break;
            case R.id.action_full:
                playFull();
                break;
            case R.id.action_animate:
                playColorAnimation();
                break;
        }
    }

    private void playOriginal() {
        resetAnimationView();
        animationView.playAnimation();
        stateText.setText(R.string.action_original);
    }

    private void playLayerOnly() {
        resetAnimationView();
        animationView.addValueCallback(
                new KeyPath("checkmark", "**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        animationView.playAnimation();
        stateText.setText(R.string.state_layer_only);
    }

    private void playFull() {
        resetAnimationView();
        animationView.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        animationView.playAnimation();
        stateText.setText(R.string.state_full);
    }

    private void playColorAnimation() {
        resetAnimationView();
        animationView.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {

                        // frameInfo.getOverallProgress() returns 0 for some reason, we'll calculate the progress manually
                        // int redValue = Math.round(frameInfo.getOverallProgress() * 100 / 255);

                        currentAnimationFrame++;
                        int currentProgress = currentAnimationFrame * 100 / frames;
                        int color = ColorUtils.blendARGB(Color.YELLOW, Color.RED, currentProgress);

                        return new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    }
                }
        );
        animationView.playAnimation();
        stateText.setText(R.string.state_animate);
    }

    private void resetAnimationView() {
        currentAnimationFrame = 0;
        animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return null;
                    }
                }
        );
    }
}
