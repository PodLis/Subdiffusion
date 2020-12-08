package net.mz.rb.subdiffusion;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.mz.rb.subdiffusion.data.SubContract;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    LinearLayout layout;
    AddHelperForSettings addHelperForSettings;
    //Button addButton, viewButton;
    FancyButton addButton, viewButton;
    EditText editName;
    private int id;
    int n = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);
        Functions.width = size.x;
        Functions.height = size.y - this.getStatusBarHeight();

        layout = (LinearLayout) findViewById(R.id.addLayout);
        /*addButton = (Button) findViewById(R.id.addButton);
        viewButton = (Button) findViewById(R.id.viewButton);*/
        addButton = (FancyButton) findViewById(R.id.addButton);
        viewButton = (FancyButton) findViewById(R.id.viewButton);
        editName = (EditText) findViewById(R.id.editName);
        resetFunctions();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if (id == -1){
            addHelperForSettings = new AddHelperForSettings(n, layout, this);
        } else {
            addButton.setText(this.getString(R.string.EditPattern));
            addHelperForSettings = new AddHelperForSettings(n, layout, this, id, editName);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        addHelperForSettings.onLastChange(seekBar);
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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1){
                    inPattern();
                } else {
                    upPattern();
                }
                Intent intent = new Intent(AddActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHelperForSettings.setParametersToFunctions();
                Intent intent = new Intent(AddActivity.this, Class_new.class);
                startActivity(intent);
            }
        });

        addHelperForSettings.atFirst();
        for (int i = 0; i < n; i++) {
            addHelperForSettings.seekBars[i].setOnSeekBarChangeListener(this);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void resetFunctions() {
        Functions.points = 25;
        Functions.difSpeed = 1;
        Functions.radius = 1;
        Functions.alpha = 1.0;
        Functions.koeff = 100.0;
        Functions.sigma1 = 1.0;
        Functions.sigma2 = 0.0;
        Functions.X = 13;
        Functions.Y = 13;
    }

    public void inPattern() {
        long l = addHelperForSettings.insertPattern(editName.getText().toString().trim());
        if (l == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении шаблона", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Шаблон заведён под номером: " + l, Toast.LENGTH_SHORT).show();
        }
    }

    public void upPattern() {
        addHelperForSettings.updatePattern(editName.getText().toString().trim());
            Toast.makeText(this, "Шаблон успешно изменён", Toast.LENGTH_SHORT).show();
    }
}

class AddHelperForSettings {
    private int iD;
    private String name;
    protected SeekBar[] seekBars;
    private TextView[] textViews, texts;
    private double sigma1, sigma2, alpha, koeff;
    private int n, difSpeed, points, pointsInHor, pointsInVer, radius, width, height;
    private int maxPointsInHor, maxPointsInVer;
    private static final int MIN_VALUE = 1;
    private Context context;
    private TheBestHelperWithDB theBestHelperWithDB;

    AddHelperForSettings(int number, LinearLayout linearLayout, Context context) {
        n = number;
        this.context = context;
        theBestHelperWithDB = new TheBestHelperWithDB(this.context);
        seekBars = new SeekBar[n];
        textViews = new TextView[n];
        texts = new TextView[n];
        this.getParametersFromFunctions();
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
            textViews[i].setHeight(150);
            texts[i].setHeight(120);
            texts[i].setGravity(Gravity.CENTER);
            textViews[i].setTextColor(ContextCompat.getColor(context, R.color.secondary_text));
            seekBars[i].setMax(1000);
            seekBars[i].setProgress(0);
            linearLayout.addView(textViews[i]);
            linearLayout.addView(seekBars[i]);
            linearLayout.addView(texts[i]);
        }
        iD = -1;
    }

    AddHelperForSettings(int number, LinearLayout linearLayout, Context context, int iD, EditText editName) {
        this(number, linearLayout, context);
        this.iD = iD;
        this.getParametersFromDataBase();
        editName.setText(name);
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
                            seekBars[4].setProgress((int) ((1. - sigma1) * 1000.));
                        }
                    }
                    break;
                    case 4: {
                        sigma2 = seekBar.getProgress() / 1000.;
                        texts[it].setText(String.valueOf(sigma2));
                        if (sigma1 + sigma2 > 1.) {
                            seekBars[3].setProgress((int) ((1. - sigma2) * 1000.));
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

    void setParametersToFunctions() {
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

    private void getParametersFromFunctions() {
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

    private void getParametersFromDataBase() {
        sigma1 = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_SIGMA_1, iD);
        sigma2 = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_SIGMA_2, iD);
        points = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS, iD);
        difSpeed = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_DIF_SPEED, iD);
        radius = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_RADIUS, iD);
        alpha = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_ALPHA, iD);
        koeff = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_KOEFF, iD);
        pointsInHor = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS_IN_HOR, iD);
        pointsInVer = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS_IN_VER, iD);
        width = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_WIDTH, iD);
        height = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_HEIGHT, iD);
        name = theBestHelperWithDB.getName(iD);
    }

    void updatePattern(String name) {
        theBestHelperWithDB.updatePost(iD, name,
                new int[] {width, height, radius, pointsInHor, pointsInVer, points, difSpeed},
                new double[] {sigma1, sigma2, alpha, koeff});
    }

    long insertPattern(String name) {
        return theBestHelperWithDB.addNewPost(name,
                new int[] {width, height, radius, pointsInHor, pointsInVer, points, difSpeed},
                new double[] {sigma1, sigma2, alpha, koeff});
    }
}