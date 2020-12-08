package net.mz.rb.subdiffusion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.TextView;

import net.mz.rb.subdiffusion.data.SubContract;
import net.mz.rb.subdiffusion.data.SubDataHelper;

public class Class_new extends Activity {
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        if (id != -1)
            setOnFunctions();
        setContentView(new Draw_dif(this));
    }

    private void setOnFunctions() {
        TheBestHelperWithDB theBestHelperWithDB = new TheBestHelperWithDB(this);
        Functions.width = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_WIDTH, id);
        Functions.height = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_HEIGHT, id);
        Functions.radius = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_RADIUS, id);
        Functions.points = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS, id);
        Functions.difSpeed = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_DIF_SPEED, id);
        Functions.sigma1 = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_SIGMA_1, id);
        Functions.sigma2 = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_SIGMA_2, id);
        Functions.alpha = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_ALPHA, id);
        Functions.koeff = theBestHelperWithDB.getDoubleParameter(SubContract.Pattern.COLUMN_KOEFF, id);;
        Functions.X = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS_IN_HOR, id);
        Functions.Y = theBestHelperWithDB.getIntParameter(SubContract.Pattern.COLUMN_POINTS_IN_VER, id);
    }
}