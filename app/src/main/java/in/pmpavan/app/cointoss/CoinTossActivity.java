package in.pmpavan.app.cointoss;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.pmpavan.app.cointoss.animations.DisplayNextView;
import in.pmpavan.app.cointoss.animations.FlipAnimation;
import in.pmpavan.app.cointoss.animations.FlipListener;

public class CoinTossActivity extends AppCompatActivity implements CoinTossController.CoinTossHelper {

    //    @BindView(R.id.textView)
//    TextView textView;
    @BindView(R.id.button)
    Button button;
    //    @BindView(R.id.imageView)
//    ImageView imageView;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.mainView)
    View mainView;


    @BindView(R.id.headsImageView)
    ImageView headsImageView;
    @BindView(R.id.tailsImageView)
    ImageView tailsImageView;

    private boolean isFirstImage = true;

    private CoinTossController coinTossController;
    private ValueAnimator mFlipAnimator;
    private CoinTossController.CoinFace coinFace;

    @Override
    public void showResult(CoinTossController.CoinFace coinFace) {
        int imgRes = coinTossController.getImgRes(coinFace);
        int txtRes = coinTossController.getTextRes(coinFace);
        this.coinFace = coinFace;
//        textView.setText(txtRes);
//        imageView.setImageResource(imgRes);
        Snackbar.make(mainView, txtRes, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupControllers();
    }

    private void handleCoinFlip() {
        mFlipAnimator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        coinTossController.performButtonClick();
                        mFlipAnimator.end();
                        if (coinFace == CoinTossController.CoinFace.TAILS) {
                            tailsImageView.setRotationY(0);
                            headsImageView.setVisibility(View.GONE);
                            tailsImageView.setVisibility(View.VISIBLE);
                            tailsImageView.requestFocus();
                        } else {
                            headsImageView.setRotationY(0);
                            tailsImageView.setVisibility(View.GONE);
                            headsImageView.setVisibility(View.VISIBLE);
                            headsImageView.requestFocus();
                        }
                    }
                });
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                coinTossController.performButtonClick();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mFlipAnimator.end();
//                        if (coinFace == CoinTossController.CoinFace.TAILS) {
//                            tailsImageView.setRotationY(0);
//                            headsImageView.setVisibility(View.GONE);
//                            tailsImageView.setVisibility(View.VISIBLE);
//                            tailsImageView.requestFocus();
//                        } else {
//                            headsImageView.setRotationY(0);
//                            tailsImageView.setVisibility(View.GONE);
//                            headsImageView.setVisibility(View.VISIBLE);
//                            headsImageView.requestFocus();
//                        }
//                    }
//                });
//            }
//        }, 1000);
    }

    private void setupControllers() {
        coinTossController = CoinTossController.getInstance(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCoinFlip();

            }
        });
        loadAdView();
        headsImageView = (ImageView) findViewById(R.id.headsImageView);
        tailsImageView = (ImageView) findViewById(R.id.tailsImageView);
        tailsImageView.setVisibility(View.GONE);

        mFlipAnimator = ValueAnimator.ofFloat(0f, 1f);
        mFlipAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mFlipAnimator.addUpdateListener(new FlipListener(headsImageView, tailsImageView));

        headsImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleCoinFlip();

            }
        });
    }

    private boolean isFlipped() {
        return mFlipAnimator.getAnimatedFraction() == 1;
    }

    private boolean isFlipping() {
        final float currentValue = mFlipAnimator.getAnimatedFraction();
        return (currentValue < 1 && currentValue > 0);
    }

    private void toggleFlip() {
        if (isFlipped()) {
            mFlipAnimator.reverse();
        } else {
            mFlipAnimator.start();
        }
    }

    private void loadAdView() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6159407697013834/9059419304");
        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest adRequest = builder.build();
        mAdView.loadAd(adRequest);
    }

    private void applyRotation(float start, float end) {
        // Find the center of image
        final float centerX = headsImageView.getWidth() / 2.0f;
        final float centerY = headsImageView.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation

        final FlipAnimation rotation =
                new FlipAnimation(start, end, centerX, centerY);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
//        rotation.setRepeatCount(Animation.INFINITE);
//        rotation.setRepeatMode(Animation.INFINITE);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, headsImageView, tailsImageView));

        if (isFirstImage) {
            headsImageView.startAnimation(rotation);
            start = 0;
            end = 90;
        } else {
            tailsImageView.startAnimation(rotation);
            start = 0;
            end = -90;
        }

    }
}
