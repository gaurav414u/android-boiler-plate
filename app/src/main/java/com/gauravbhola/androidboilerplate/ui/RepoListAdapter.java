package com.gauravbhola.androidboilerplate.ui;


import com.gauravbhola.androidboilerplate.R;
import com.gauravbhola.androidboilerplate.model.Repo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;


public class RepoListAdapter extends RecyclerView.Adapter<MainActivity.RepoListViewHolder> {
    Context mContext;
    List<Repo> mRepoList;
    ItemClickListener clickListner;
    public interface ItemClickListener {
        public void onItemClick(int position);
    }

    public RepoListAdapter(Context context, List<Repo> items) {
        super();
        mContext = context;
        mRepoList = items;
    }


    public void setClickListner(ItemClickListener clickListner) {
        this.clickListner = clickListner;
    }

    @Override
    public MainActivity.RepoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_search, parent, false);
        final MainActivity.RepoListViewHolder viewHolder = new MainActivity.RepoListViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (RepoListAdapter.this.clickListner != null){
                        RepoListAdapter.this.clickListner.onItemClick(pos);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivity.RepoListViewHolder holder, int position) {
        holder.bind(mRepoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }
}
