package com.gauravbhola.androidboilerplate.model.event;


import com.gauravbhola.androidboilerplate.data.remote.RepoSearchResponse;

public class RepoSearchEvent extends ResourceEvent<RepoSearchResponse> {

    public RepoSearchEvent() {
        super();
    }


    public RepoSearchEvent(Status status, RepoSearchResponse data, String message) {
        super(status, data, message);
    }
}


