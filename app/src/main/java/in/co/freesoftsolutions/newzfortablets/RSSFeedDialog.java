package in.co.freesoftsolutions.newzfortablets;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import in.co.freesoftsolutions.newzfortablets.model.RSSFeed;
import in.co.freesoftsolutions.newzfortablets.model.RSSFeeds;

public class RSSFeedDialog extends DialogFragment {
    public static final String EXTRA_FEED_ADDED =
            "freesoftsolutions.co.in.thenewz.rssfeeddialod.feedAdded";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_rss_feed, null);

        final EditText channelEditText = view.findViewById(R.id.channel_edit_text);
        final EditText urlEditText = view.findViewById(R.id.url_edit_text);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.rss_feed_dialog_title)
                .setPositiveButton(R.string.rss_feed_dialog_ok_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String channelText = channelEditText.getText().toString();
                                String urlText = urlEditText.getText().toString();
                                createRSSFeed(channelText, urlText);
                            }
                        })
                .create();
    }

    private void createRSSFeed(String channelText, String urlText) {
        if (!channelText.isEmpty() && !urlText.isEmpty()) {
            RSSFeed rssFeed = new RSSFeed();
            rssFeed.setChannel(channelText);
            rssFeed.setUrl(urlText);

            RSSFeeds.get(getActivity()).addRSSFeed(rssFeed);

            if (getTargetFragment() == null) {
                return;
            }

            Intent i = new Intent();
            i.putExtra(EXTRA_FEED_ADDED, 1);

            getTargetFragment()
                    .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        }
    }
}
