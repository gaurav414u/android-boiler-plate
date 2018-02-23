package com.gauravbhola.androidboilerplate.ui;

import com.bumptech.glide.Glide;
import com.gauravbhola.androidboilerplate.BaseActivity;
import com.gauravbhola.androidboilerplate.R;
import com.gauravbhola.androidboilerplate.data.RepoManager;
import com.gauravbhola.androidboilerplate.model.Repo;
import com.gauravbhola.androidboilerplate.model.event.RepoSearchEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RepoListAdapter.ItemClickListener{
    public static final String TAG = MainActivity.class.getName();
    private RepoListAdapter mRepoListAdapter;
    private List<Repo> mRepoList;
    @Inject
    RepoManager mRepoManager;
    @Inject
    EventBus mEventBus;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.edit_text)
    EditText mEditText;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.error_view)
    LinearLayout mErrorView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.error_text)
    TextView mErrorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        setupEditText();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    private void setupEditText() {
        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    mRepoManager.searchRepo(mEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @SuppressWarnings("unused")
    @MainThread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RepoSearchEvent s) {
        switch (s.getStatus()) {
            case ERROR:
                showError(s.getMessage());
                break;
            case LOADING:
                showLoading();
                break;
            case SUCCESS:
                showData(s.getData().getItems());
                break;
            default:
        }
    }

    @MainThread
    public void showError(String message) {
        mErrorTextView.setText(message);
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    @MainThread
    public void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @MainThread
    public void showData(List<Repo> repoList) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        mRepoList = repoList;
        mRepoListAdapter = new RepoListAdapter(getApplicationContext(), mRepoList);
        mRecyclerView.setLayoutAnimation(getListAnimationController(300));
        mRepoListAdapter.setClickListner(this);
        mRecyclerView.setAdapter(mRepoListAdapter);
        mRepoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, mRepoList.get(position).getFullName() +"clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    public LayoutAnimationController getListAnimationController(long animationDuration) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(animationDuration);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.ABSOLUTE, 200.0f,Animation.ABSOLUTE, 0.0f
        );
        animation.setDuration(animationDuration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        return controller;
    }

    public static class RepoListViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleView;
        TextView mDescriptionView;
        ImageView mImageView;
        View v;

        public RepoListViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            mTitleView = itemView.findViewById(R.id.tv_repo_title);
            mDescriptionView = itemView.findViewById(R.id.tv_repo_desc);
            mImageView = itemView.findViewById(R.id.iv_repo);
        }

        public void bind(Repo repo) {
            mTitleView.setText(repo.getFullName());
            mDescriptionView.setText(repo.getDescription());
            Glide.with(mImageView.getContext())
                    .load(repo.getOwner().getAvatarUrl())
                    .into(mImageView);
        }

    }
}
