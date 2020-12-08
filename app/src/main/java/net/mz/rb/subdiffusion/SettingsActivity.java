package net.mz.rb.subdiffusion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.mz.rb.subdiffusion.data.SubDataHelper;
import net.mz.rb.subdiffusion.data.SubContract.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    FancyButton button3;
    //Button button3;
    LinearLayout layout;
    HelperForSettings helperForSettings;
    int n = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        android.graphics.Point size = new  android.graphics.Point();
        display.getSize(size);
        Functions.width = size.x;
        Functions.height = size.y - this.getStatusBarHeight();

        layout = (LinearLayout) findViewById(R.id.layout);
        button3 = (FancyButton) findViewById(R.id.startButton);
        //button3 = (Button) findViewById(R.id.button3);
        helperForSettings = new HelperForSettings(n, layout, this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            helperForSettings.onLastChange(seekBar);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onStart() {
        super.onStart();
		button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperForSettings.setParametersOnFunction();
                Intent intent = new Intent(SettingsActivity.this, Class_new.class);
                startActivity(intent);
            }
        });
        helperForSettings.atFirst();
        for (int i = 0; i < n; i++) {
            helperForSettings.seekBars[i].setOnSeekBarChangeListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

class HelperForSettings {
    protected SeekBar[] seekBars;
    private TextView[] textViews, texts;
    private double sigma1, sigma2, alpha, koeff;
    private int n, difSpeed, points, pointsInHor, pointsInVer, radius, width, height;
	private int maxPointsInHor, maxPointsInVer, maxDifSpeed, maxRadius;
	private static final int MIN_VALUE = 1;

    HelperForSettings(int number, LinearLayout linearLayout, Context context) {
        n = number;
        seekBars = new SeekBar[n];
        textViews = new TextView[n];
        texts = new TextView[n];
		this.getParametersFromDataBase();
		maxPointsInHor = Functions.calculateMaxPoints(width, radius);
		maxPointsInVer = Functions.calculateMaxPoints(height, radius);
        for (int i = 0; i < n; i++) {
            textViews[i] = new TextView(context);
            texts[i] = new TextView(context);
            seekBars[i] = new SeekBar(context);
            textViews[i].setText(R.string.ForEditText1 + i);
            texts[i].setTextColor(ContextCompat.getColor(context, R.color.primary_text));
            textViews[i].setPadding(20, 0, 0, 0);
            textViews[i].setTextSize(20);

            textViews[i].setWidth(linearLayout.getWidth());
            textViews[i].setHeight(175);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textViews[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            }

            texts[i].setHeight(120);
            texts[i].setGravity(Gravity.CENTER);
            textViews[i].setTextColor(ContextCompat.getColor(context, R.color.secondary_text));
            seekBars[i].setMax(1000);
            seekBars[i].setProgress(0);
            linearLayout.addView(textViews[i]);
            linearLayout.addView(seekBars[i]);
            linearLayout.addView(texts[i]);
        }
    }

    void atFirst() {
        for (int it = 0; it < n; it++) {
            switch (it) {
                case 0: {
                    texts[it].setText(String.valueOf(pointsInHor));
                    seekBars[it].setProgress((pointsInHor - MIN_VALUE) / 2);
					seekBars[it].setMax((maxPointsInHor - MIN_VALUE) / 2);
                }
                break;
                case 1: {
                    texts[it].setText(String.valueOf(pointsInVer));
                    seekBars[it].setProgress((pointsInVer - MIN_VALUE) / 2);
					seekBars[it].setMax((maxPointsInVer - MIN_VALUE) / 2);
                }
                break;
                case 2: {
                    seekBars[it].setMax(1300);
                    texts[it].setText(String.valueOf(points));
                    seekBars[it].setProgress(Functions.logAndPow(points));
                }
                break;
                case 3: {
                    texts[it].setText(String.valueOf(sigma1));
                    seekBars[it].setProgress((int) (sigma1 * 1000));
                }
                break;
                case 4: {
                    texts[it].setText(String.valueOf(sigma2));
                    seekBars[it].setProgress((int) (sigma2 * 1000));
                }
                break;
                case 5: {
                    texts[it].setText(String.valueOf(alpha));
                    seekBars[it].setProgress((int) (alpha * 1000));
                }
                break;
                case 6: {
                    texts[it].setText(String.valueOf(koeff));
                    seekBars[it].setProgress((int) (koeff * 10));
                }
                break;
                case 7: {
                    texts[it].setText(String.valueOf(difSpeed));
                    seekBars[it].setProgress(difSpeed - MIN_VALUE);
					seekBars[it].setMax(10 - MIN_VALUE);
                }
                break;
                case 8: {
                    texts[it].setText(String.valueOf(radius));
                    seekBars[it].setProgress(radius - MIN_VALUE);
					seekBars[it].setMax(20 - MIN_VALUE);
                }
                break;
                default: {
                    texts[it].setText(String.valueOf(seekBars[it].getProgress()));
                }
                break;
            }
        }
    }

    void onLastChange(SeekBar seekBar) {
        for (int it = 0; it < n; it++) {
            if (seekBar == seekBars[it]) {
                switch (it) {
                    case 0: {
						pointsInHor = seekBar.getProgress() * 2 + MIN_VALUE;
                        texts[it].setText(String.valueOf(pointsInHor));
                    }
                    break;
                    case 1: {
						pointsInVer = seekBar.getProgress() * 2 + MIN_VALUE;
                        texts[it].setText(String.valueOf(pointsInVer));
                    }
                    break;
                    case 2: {
						points = Functions.expAndRoot(seekBar.getProgress());
                        texts[it].setText(String.valueOf(points));
                    }
                    break;
                    case 3: {
						sigma1 = seekBar.getProgress() / 1000.;
                        texts[it].setText(String.valueOf(sigma1));
                        if (sigma1 + sigma2 > 1.) {
                            seekBars[4].setProgress((int)((1. - sigma1) * 1000.));
                        }
                    }
                    break;
                    case 4: {
						sigma2 = seekBar.getProgress() / 1000.;
                        texts[it].setText(String.valueOf(sigma2));
                        if (sigma1 + sigma2 > 1.) {
                            seekBars[3].setProgress((int)((1. - sigma2) * 1000.));
                        }
                    }
                    break;
                    case 5: {
						alpha = seekBar.getProgress() / 1000.;
                        texts[it].setText(String.valueOf(alpha));
                    }
                    break;
                    case 6: {
						koeff = seekBar.getProgress() / 10.0;
                        texts[it].setText(String.valueOf(koeff));
                    }
                    break;
                    case 7: {
						difSpeed = seekBar.getProgress() + MIN_VALUE;
                        texts[it].setText(String.valueOf(difSpeed));
                    }
                    break;
                    case 8: {
						radius = seekBar.getProgress() + MIN_VALUE;
                        texts[it].setText(String.valueOf(radius));
						maxPointsInHor = Functions.calculateMaxPoints(width, radius);
						maxPointsInVer = Functions.calculateMaxPoints(height, radius);
						if (pointsInHor > maxPointsInHor) {
							seekBars[0].setProgress((maxPointsInHor - MIN_VALUE) / 2);
						}
						if (pointsInVer > maxPointsInVer) {
							seekBars[1].setProgress((maxPointsInVer - MIN_VALUE) / 2);
						}
                        seekBars[0].setMax((maxPointsInHor - MIN_VALUE) / 2);
                        seekBars[1].setMax((maxPointsInVer - MIN_VALUE) / 2);
                    }
                    break;
                    default: {
                        texts[it].setText(String.valueOf(seekBar.getProgress() * 2 + 1));
                    }
                    break;
                }
            }
        }
    }

    public void setParametersOnFunction() {
        Functions.X = pointsInHor;
        Functions.Y = pointsInVer;
        Functions.points = points;
        Functions.sigma1 = sigma1;
        Functions.sigma2 = sigma2;
        Functions.alpha = alpha;
        Functions.koeff = koeff;
        Functions.difSpeed = difSpeed;
        Functions.radius = radius;
    }

    public void getParametersFromDataBase() {
        sigma1 = Functions.sigma1;
        sigma2 = Functions.sigma2;
        points = Functions.points;
        difSpeed = Functions.difSpeed;
        radius = Functions.radius;
        alpha = Functions.alpha;
        koeff = Functions.koeff;
        pointsInHor = Functions.X;
        pointsInVer = Functions.Y;
		width = Functions.width;
        height = Functions.height;
    }
}