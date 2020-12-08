package net.mz.rb.subdiffusion.data;

import android.provider.BaseColumns;

/**
 * Created by samsung on 04.05.2018.
 */

public final class SubContract {
    private SubContract(){
    }

    public static final class Pattern implements BaseColumns {
        public final static String TABLE_NAME = "patterns";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_WIDTH = "width";
        public final static String COLUMN_HEIGHT = "height";
        public final static String COLUMN_RADIUS = "radius";
        public final static String COLUMN_POINTS_IN_HOR = "pointsInHor";
        public final static String COLUMN_POINTS_IN_VER = "pointsInVer";
        public final static String COLUMN_POINTS = "points";
        public final static String COLUMN_DIF_SPEED = "difSpeed";
        public final static String COLUMN_SIGMA_1 = "sigma1";
        public final static String COLUMN_SIGMA_2 = "sigma2";
        public final static String COLUMN_ALPHA = "alpha";
        public final static String COLUMN_KOEFF = "koeff";
    }
}
