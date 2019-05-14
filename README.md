# Lottie Color filter sample for Android
This repository is a sample of how to add color filters to a Lottie animation.

![Example](https://i.imgur.com/4OWpSwb.gif)


See also: https://stackoverflow.com/questions/43376316/lottie-android-add-color-overlay-to-animation
The Lottie file was found at https://lottiefiles.com/2478-check.

### 3.0.0+ method

Full overlay:

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


Single layer overlay:

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


Reset overlay:

    animationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
        new SimpleLottieValueCallback<ColorFilter>() {
            @Override
            public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                return null;
            }
    });

## Old method
In older version of Lottie this had to be done the following way:

Full color overlay:

    LottieAnimationView statusView = (LottieAnimationView) findViewById(R.id.check_animation_view); 
    statusView.addColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP));
    statusView.playAnimation();

Color only one layer:

    LottieAnimationView statusView = (LottieAnimationView) findViewById(R.id.check_animation_view); 
    statusView.addColorFilterToLayer("layername", new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP));
    statusView.playAnimation();