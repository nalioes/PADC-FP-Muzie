package com.syncsource.org.muzie.rests;

/**
 * Created by nalioes on 1/14/17.
 */

public interface ScApiAccess {

    void getSearchScTrack(String query, String limit, String client_id);

    void getTopPopularTrack();

    void getGenresTrack(String genres, String num);

    void getNewHotGenresTrack(String genres, String num);

    void getNewHotTrack();
}
