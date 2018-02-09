package net.ammal.elasticsearch.javaapitest.model;

import net.ammal.elasticsearch.javaapitest.repository.UserDocument;

import java.util.List;

public class SearchResponseJson {

    private final long timeTakenForSearch;

    private final long hits;

    private final List<UserDocument> users;

    public SearchResponseJson(long timeTakenForSearch, long hits, List<UserDocument> users) {
        this.timeTakenForSearch = timeTakenForSearch;
        this.hits = hits;
        this.users = users;
    }

    public long getTimeTakenForSearch() {
        return timeTakenForSearch;
    }

    public long getHits() {
        return hits;
    }

    public List<UserDocument> getUsers() {
        return users;
    }
}
