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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton originalButton;
    private AppCompatButton partialButton;
    private AppCompatButton fullButton;
    private LottieAnimationView animationView;
    private AppCompatTextView stateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalButton = findViewById(R.id.action_original);
        partialButton = findViewById(R.id.action_layer_only);
        fullButton = findViewById(R.id.action_full);
        animationView = findViewById(R.id.animation_view);
        stateText = findViewById(R.id.state_label);

        originalButton.setOnClickListener(this);
        partialButton.setOnClickListener(this);
        fullButton.setOnClickListener(this);

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

    private void resetAnimationView() {
        animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                new SimpleLottieValueCallback<ColorFilter>() {
                    @Override
                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                        return null;
                    }
                });
    }
}
