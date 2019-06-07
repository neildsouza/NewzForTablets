package in.co.freesoftsolutions.newzfortablets.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import in.co.freesoftsolutions.newzfortablets.database.*;
import in.co.freesoftsolutions.newzfortablets.database.RSSFeedsSchema.RSSFeedsTable;


public final class RSSFeeds {
    private static RSSFeeds sRSSFeeds;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private RSSFeeds(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RSSFeedsOpenHelper(mContext).getWritableDatabase();
    }

    public static RSSFeeds get(Context context) {
        if (sRSSFeeds == null) {
            sRSSFeeds = new RSSFeeds(context);
        }
        return sRSSFeeds;
    }

    public void addRSSFeed(RSSFeed rssFeed) {
        ContentValues values = getContentValues(rssFeed);
        mDatabase.insert(RSSFeedsTable.NAME, null, values);
    }

    public void updateRSSFeed(RSSFeed rssFeed) {
        String uuidString = rssFeed.getUUID().toString();
        ContentValues values = getContentValues(rssFeed);

        mDatabase.update(RSSFeedsTable.NAME, values,
                RSSFeedsTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    public void deleteRSSFeed(UUID uuid) {
        String uuidString = uuid.toString();
        mDatabase.delete(RSSFeedsTable.NAME,
                RSSFeedsTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private RSSFeedCursorWrapper queryRSSFeedsTable(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                RSSFeedsTable.NAME, null,
                whereClause, whereArgs, null, null, null);

        return new RSSFeedCursorWrapper(cursor);
    }

    public RSSFeed getRSSFeed(UUID id) {
        RSSFeedCursorWrapper cursor = queryRSSFeedsTable(
                RSSFeedsTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getRSSFeed();

        } finally {
            cursor.close();
        }
    }

    public List<RSSFeed> getRSSFeeds() {
        List<RSSFeed> rssFeeds = new ArrayList<>();

        RSSFeedCursorWrapper cursor = queryRSSFeedsTable(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rssFeeds.add(cursor.getRSSFeed());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return rssFeeds;
    }

    private static ContentValues getContentValues(RSSFeed rssFeed) {
        ContentValues values = new ContentValues();
        values.put(RSSFeedsTable.Cols.UUID, rssFeed.getUUID().toString());
        values.put(RSSFeedsTable.Cols.CHANNEL, rssFeed.getChannel());
        values.put(RSSFeedsTable.Cols.URL, rssFeed.getUrl());

        return values;
    }
}
