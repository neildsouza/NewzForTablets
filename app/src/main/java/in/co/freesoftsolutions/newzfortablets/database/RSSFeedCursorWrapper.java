package in.co.freesoftsolutions.newzfortablets.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import in.co.freesoftsolutions.newzfortablets.model.RSSFeed;

public class RSSFeedCursorWrapper extends CursorWrapper {
    public RSSFeedCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public RSSFeed getRSSFeed() {
        String uuidString = getString(getColumnIndex(RSSFeedsSchema.RSSFeedsTable.Cols.UUID));
        String channel = getString(getColumnIndex(RSSFeedsSchema.RSSFeedsTable.Cols.CHANNEL));
        String url = getString(getColumnIndex(RSSFeedsSchema.RSSFeedsTable.Cols.URL));

        RSSFeed rssFeed = new RSSFeed(UUID.fromString(uuidString));
        rssFeed.setChannel(channel);
        rssFeed.setUrl(url);

        return rssFeed;
    }
}
