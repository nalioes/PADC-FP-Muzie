package com.syncsource.org.muzie.rests;

import com.syncsource.org.muzie.BuildConfig;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.MostTrackContent;
import com.syncsource.org.muzie.model.SearchContentID;
import com.syncsource.org.muzie.model.TrackID;
import com.syncsource.org.muzie.utils.Config;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SyncSource on 9/1/2016.
 */
public class ApiClient implements MuzieApiAccess {

    private static Retrofit retrofit = null;
    private static ApiClient apiClient;
    private ApiInterface apiInterface;


    private ApiClient() {

        apiInterface = getClient().create(ApiInterface.class);
    }

    public static ApiClient getApiClientInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.YOUTUBE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    @Override
    public void getTrackID(String part, String query, String maxNumber, String key) {
        Call<SearchContentID> call = apiInterface.getTrackId(part, query, Config.TYPE, Config.CATEGORY, maxNumber, key);
        call.enqueue(new Callback<SearchContentID>() {
            @Override
            public void onResponse(Call<SearchContentID> call, Response<SearchContentID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnSnippetEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnSnippetEvent(false));
                }
            }

            @Override
            public void onFailure(Call<SearchContentID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnSnippetEvent(false));
            }
        });
    }

    @Override
    public void getTrackDuration(String part, String id, String key) {
        Call<TrackID> call = apiInterface.getTrackDuration(part, id, key);
        call.enqueue(new Callback<TrackID>() {
            @Override
            public void onResponse(Call<TrackID> call, Response<TrackID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnTrackIDEvent(true, response.body().getItems()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnTrackIDEvent(false));
                }
            }

            @Override
            public void onFailure(Call<TrackID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnTrackIDEvent(false));
            }
        });
    }

    @Override
    public void getNextTrackID(String token, String part, String query, String maxNumber, String key) {
        Call<SearchContentID> call = apiInterface.getNextTrackId(token, part, query, Config.TYPE, Config.CATEGORY, maxNumber, key);
        call.enqueue(new Callback<SearchContentID>() {
            @Override
            public void onResponse(Call<SearchContentID> call, Response<SearchContentID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnSnippetTrackEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnSnippetTrackEvent(false));
                }
            }

            @Override
            public void onFailure(Call<SearchContentID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnSnippetTrackEvent(false));
            }
        });
    }

    @Override
    public void getNextTrackDuration(String part, String id, String key) {
        Call<TrackID> call = apiInterface.getTrackDuration(part, id, key);
        call.enqueue(new Callback<TrackID>() {
            @Override
            public void onResponse(Call<TrackID> call, Response<TrackID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnNextTrackIDEvent(true, response.body().getItems()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnNextTrackIDEvent(false));
                }
            }

            @Override
            public void onFailure(Call<TrackID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnNextTrackIDEvent(false));
            }
        });
    }

    @Override
    public void getLatestTrack(String part, String afterAt, String beforeAt, String key) {
        Call<MostTrackContent> call = apiInterface.getLatestTrack(part, Config.TYPE, Config.CATEGORY, Config.MAX_NUMBER, Config.CHART, afterAt, beforeAt, key);
        call.enqueue(new Callback<MostTrackContent>() {
            @Override
            public void onResponse(Call<MostTrackContent> call, Response<MostTrackContent> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnLatestSnippetEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnLatestSnippetEvent(false));
                }
            }

            @Override
            public void onFailure(Call<MostTrackContent> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnLatestSnippetEvent(false));
            }
        });
    }

    @Override
    public void getLatestTrackDuration(String part, String id, String key) {
        Call<TrackID> call = apiInterface.getTrackDuration(part, id, key);
        call.enqueue(new Callback<TrackID>() {
            @Override
            public void onResponse(Call<TrackID> call, Response<TrackID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnLatestTrackIDEvent(true, response.body().getItems()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnLatestTrackIDEvent(false));
                }
            }

            @Override
            public void onFailure(Call<TrackID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnLatestTrackIDEvent(false));
            }
        });
    }

    @Override
    public void getNextLatestTrack(String token, String part, String afterAt, String beforeAt, String key) {
        Call<MostTrackContent> call = apiInterface.getNextLatestTrack(token, part, Config.TYPE, Config.CATEGORY, Config.MAX_NUMBER, Config.CHART, afterAt, beforeAt, key);
        call.enqueue(new Callback<MostTrackContent>() {
            @Override
            public void onResponse(Call<MostTrackContent> call, Response<MostTrackContent> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnNextLatestSnippetEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnNextLatestSnippetEvent(false));
                }
            }

            @Override
            public void onFailure(Call<MostTrackContent> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnNextLatestSnippetEvent(false));
            }
        });
    }

    @Override
    public void getNextLatestTrackDuration(String part, String id, String key) {
        Call<TrackID> call = apiInterface.getTrackDuration(part, id, key);
        call.enqueue(new Callback<TrackID>() {
            @Override
            public void onResponse(Call<TrackID> call, Response<TrackID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnNextLatestTrackIDEvent(true, response.body().getItems()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnNextLatestTrackIDEvent(false));
                }
            }

            @Override
            public void onFailure(Call<TrackID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnNextLatestTrackIDEvent(false));
            }
        });
    }

    @Override
    public void getRelatedTrackID(String part, String videoId, String maxNumber, String key) {
        Call<SearchContentID> call = apiInterface.getRelatedTrackId(part, Config.TYPE, videoId , maxNumber, key);
        call.enqueue(new Callback<SearchContentID>() {
            @Override
            public void onResponse(Call<SearchContentID> call, Response<SearchContentID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnRelatedSnippetEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnRelatedSnippetEvent(false));
                }
            }

            @Override
            public void onFailure(Call<SearchContentID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnRelatedSnippetEvent(false));
            }
        });
    }

    @Override
    public void getNextRelatedTrackID(String token, String part, String videoID,String maxNumber, String key) {
        Call<SearchContentID> call = apiInterface.getNextRelatedTrackId(token, part, Config.TYPE, videoID, maxNumber, key);
        call.enqueue(new Callback<SearchContentID>() {
            @Override
            public void onResponse(Call<SearchContentID> call, Response<SearchContentID> response) {
                if (response.body().getItems().size() > 0) {
                    EventBus.getDefault().post(new TrackEvent.OnNextRelatedSnippetEvent(true, response.body().getItems(), response.body().getNextPageToken()));
                } else {
                    EventBus.getDefault().post(new TrackEvent.OnNextRelatedSnippetEvent(false));
                }
            }

            @Override
            public void onFailure(Call<SearchContentID> call, Throwable t) {
                EventBus.getDefault().post(new TrackEvent.OnNextRelatedSnippetEvent(false));
            }
        });
    }
}
