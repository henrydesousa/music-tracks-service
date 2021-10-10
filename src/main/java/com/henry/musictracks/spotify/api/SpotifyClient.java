package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.TrackMetadata;
import java.io.IOException;
import java.util.Optional;

public interface SpotifyClient {

    Optional<TrackMetadata> fetchTrackMetadata(String ISRC) throws IOException;

}
