package com.gauravbhola.androidboilerplate.data;


import com.google.gson.Gson;

import com.gauravbhola.androidboilerplate.data.remote.ApiService;
import com.gauravbhola.androidboilerplate.data.remote.RepoSearchResponse;
import com.gauravbhola.androidboilerplate.model.event.ResourceEvent;
import com.gauravbhola.androidboilerplate.model.event.RepoSearchEvent;
import com.gauravbhola.androidboilerplate.util.AppExecutors;
import com.gauravbhola.androidboilerplate.util.NetworkBoundTask;

import org.greenrobot.eventbus.EventBus;

import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class RepoManager {
    public static final String TAG = RepoManager.class.getName();
    Gson mGson;
    ApiService mApiService;
    EventBus mEventBus;
    AppExecutors mAppExecutors;


    @Inject
    RepoManager(Gson gson, ApiService apiService, EventBus eventBus, AppExecutors appExecutors) {
        mGson = gson;
        mApiService = apiService;
        mEventBus = eventBus;
        mAppExecutors = appExecutors;
    }

    public void searchRepo(final String query) {
        Log.d(TAG, "searchRepo");
        if (TextUtils.isEmpty(query)) {
            mEventBus.post(new RepoSearchEvent(ResourceEvent.Status.ERROR, null, "Please enter a valid query"));
            return;
        }
        mEventBus.post(new RepoSearchEvent(ResourceEvent.Status.LOADING, null, null));

        mAppExecutors.networkIO().execute(new NetworkBoundTask<RepoSearchResponse, RepoSearchEvent>(mEventBus) {
            @Override
            public Call<RepoSearchResponse> createCall() {
                return mApiService.searchRepos(query);
            }

            @Override
            public RepoSearchEvent getNewEvent() {
                return new RepoSearchEvent();
            }

            @Override
            public void saveCallResult(RepoSearchResponse response) {
            }
        });
    }
}
