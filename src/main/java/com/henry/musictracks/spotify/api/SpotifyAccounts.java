package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.Token;
import retrofit2.Call;
import retrofit2.http.*;

public interface SpotifyAccounts {

    @FormUrlEncoded
    @POST("api/token")
    Call<Token> obtainToken(@Header("Authorization") String authorization, @Field("grant_type") String grantType);

}
