package com.henry.musictracks.config;

import com.henry.musictracks.spotify.api.SpotifyAPI;
import com.henry.musictracks.spotify.api.SpotifyAccounts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class MusicTrackConfig {

    @Bean
    Retrofit retrofitSpotifyAccountsFactory(@Value("${spotify.accounts-host}") String url) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create());
        return builder.build();
    }

    @Bean
    Retrofit retrofitSpotifyAPIFactory(@Value("${spotify.api-host}") String url) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create());
        return builder.build();
    }

    @Bean
    SpotifyAccounts spotifyAccounts(Retrofit retrofitSpotifyAccountsFactory) {
        return retrofitSpotifyAccountsFactory.create(SpotifyAccounts.class);
    }

    @Bean
    SpotifyAPI spotifyAPI(Retrofit retrofitSpotifyAPIFactory) {
        return retrofitSpotifyAPIFactory.create(SpotifyAPI.class);
    }

}
