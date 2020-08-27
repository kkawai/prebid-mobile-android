package org.prebid.mobile.app;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.InterstitialAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;

public class RubiconIntersitialGamDemoActivity extends AppCompatActivity {
    AdUnit adUnit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        adUnit = new InterstitialAdUnit(Constants.PBS_CONFIG_ID_INTERSTITIAL_RUBICON);
        final PublisherInterstitialAd amInterstitial = new PublisherInterstitialAd(this);
        amInterstitial.setAdUnitId(Constants.DFP_INTERSTITIAL_ADUNIT_ID_RUBICON);
        amInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                amInterstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(RubiconIntersitialGamDemoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(RubiconIntersitialGamDemoActivity.this);
                }
                builder.setTitle("Failed to load AdManager interstitial ad")
                        .setMessage("Error code: " + i)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        int millis = getIntent().getIntExtra(Constants.AUTO_REFRESH_NAME, 0);
        adUnit.setAutoRefreshPeriodMillis(millis);
        final PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        adUnit.fetchDemand(builder, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                PublisherAdRequest request = builder.build();
                amInterstitial.loadAd(request);
            }
        });
    }
}
