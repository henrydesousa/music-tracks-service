package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.TrackMetadata;
import retrofit2.Call;
import retrofit2.http.*;

public interface SpotifyAPI {

    @GET("v1/search")
    Call<TrackMetadata> fetchTrackMetadata(@Header("Authorization") String accessToken,
                                           @Query("q") String query,
                                           @Query("type") String type);

}
