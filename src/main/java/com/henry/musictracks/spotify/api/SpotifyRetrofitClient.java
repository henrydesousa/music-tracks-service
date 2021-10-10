package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.Token;
import com.henry.musictracks.spotify.model.TrackMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class SpotifyRetrofitClient implements SpotifyClient {

    private static final String AUTHORIZATION = "Basic ";
    private static final String GRANT_TYPE = "client_credentials";
    private static final String QUERY = "isrc:";
    private static final String TRACK = "track";

    private final SpotifyAccounts spotifyAccounts;
    private final SpotifyAPI spotifyAPI;

    @Value("${spotify.clientId-clientSecret-base64}")
    private String authorizationBase64;

    private Token getToken() throws IOException {
        Response<Token> tokenResponse = spotifyAccounts.obtainToken(AUTHORIZATION + authorizationBase64, GRANT_TYPE).execute();
        return tokenResponse.body();
    }

    @Override
    public Optional<TrackMetadata> fetchTrackMetadata(String ISRC) throws IOException {
        Token token = getToken();
        String accessToken = "Bearer " + token.getAccessToken();
        Response<TrackMetadata> trackResponse = spotifyAPI.fetchTrackMetadata(
                accessToken,
                QUERY + ISRC,
                TRACK).execute();
        return Optional.of(trackResponse.body());
    }
}
