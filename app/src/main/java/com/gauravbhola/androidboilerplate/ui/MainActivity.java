package com.gauravbhola.androidboilerplate.ui;

import com.gauravbhola.androidboilerplate.BaseActivity;
import com.gauravbhola.androidboilerplate.R;
import com.gauravbhola.androidboilerplate.data.RepoManager;
import com.gauravbhola.androidboilerplate.model.Repo;
import com.gauravbhola.androidboilerplate.model.event.RepoSearchEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getName();
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
}
