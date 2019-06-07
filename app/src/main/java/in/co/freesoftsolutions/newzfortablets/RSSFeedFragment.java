package in.co.freesoftsolutions.newzfortablets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.UUID;

import in.co.freesoftsolutions.newzfortablets.model.RSSFeed;
import in.co.freesoftsolutions.newzfortablets.model.RSSFeeds;

public class RSSFeedFragment extends Fragment {
    private static final String ARG_UUID = "uuid";

    private RSSFeed mRSSFeed;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    public static RSSFeedFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID, uuid);

        RSSFeedFragment rssFeedFragment = new RSSFeedFragment();
        rssFeedFragment.setArguments(args);

        return rssFeedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getArguments().getSerializable(ARG_UUID);
        mRSSFeed = RSSFeeds.get(getActivity()).getRSSFeed(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss_feed, container, false);

        mRecyclerView = view.findViewById(R.id.rss_feed_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class ArticleHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Article mArticle;
        private TextView mTitleTextView;

        ArticleHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.rss_feed_title_text_view);
            itemView.setOnClickListener(this);
        }

        void bindViewHolder(Article article) {
            mArticle = article;
            mTitleTextView.setText(article.getTitle());
        }

        @Override
        public void onClick(View view) {
            Uri webPage = Uri.parse(mArticle.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder> {
        private ArrayList<Article> mArticles;

        ArticleAdapter(ArrayList<Article> items) {
            mArticles = items;
        }

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_rss_feed, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int pos) {
            Article article = mArticles.get(pos);
            holder.bindViewHolder(article);
        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }
    }

    private void updateUI() {
        String urlString = mRSSFeed.getUrl();
        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                if (mAdapter == null) {
                    mAdapter = new ArticleAdapter(new ArrayList<>(list));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onError() {
                //what to do in case of error
            }
        });
    }
}
