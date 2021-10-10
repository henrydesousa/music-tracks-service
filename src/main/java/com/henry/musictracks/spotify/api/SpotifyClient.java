package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.TrackObject;
import java.io.IOException;
import java.util.Optional;

public interface SpotifyClient {

    Optional<TrackObject> fetchTrackMetadata(String ISRC) throws IOException;

}
