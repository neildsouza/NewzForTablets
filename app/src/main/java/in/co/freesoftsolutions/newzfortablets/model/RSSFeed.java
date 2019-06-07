package in.co.freesoftsolutions.newzfortablets.model;

import java.util.UUID;

public final class RSSFeed {
    private UUID mUUID;
    private String mChannel;
    private String mUrl;

    public RSSFeed() {
        this(UUID.randomUUID());
    }

    public RSSFeed(UUID uuid) {
        mUUID = uuid;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getChannel() {
        return mChannel;
    }

    public void setChannel(String channel) {
        mChannel = channel;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String toString() {
        return "UUID: " + mUUID.toString() +
                ", Channel: " + mChannel +
                ", Url: " + mUrl;
    }
}
