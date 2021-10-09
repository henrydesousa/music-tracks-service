package com.henry.musictracks.spotify.api;

import com.henry.musictracks.spotify.model.TrackObject;
import java.io.IOException;

public interface SpotifyClient {

    TrackObject fetchTrackMetadata(String ISRC) throws IOException;

}
