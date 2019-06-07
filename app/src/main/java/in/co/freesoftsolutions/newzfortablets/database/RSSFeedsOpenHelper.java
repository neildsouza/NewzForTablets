package in.co.freesoftsolutions.newzfortablets.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.co.freesoftsolutions.newzfortablets.database.RSSFeedsSchema.RSSFeedsTable;

public final class RSSFeedsOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "rssFeedsDatabase.db";

    public RSSFeedsOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + RSSFeedsTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                RSSFeedsTable.Cols.UUID + ", " +
                RSSFeedsTable.Cols.CHANNEL + ", " +
                RSSFeedsTable.Cols.URL + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // not used
    }
}
