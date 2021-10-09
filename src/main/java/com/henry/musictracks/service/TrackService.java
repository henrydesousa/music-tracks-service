package com.henry.musictracks.service;

import com.henry.musictracks.model.Track;
import com.henry.musictracks.repository.TrackRepository;
import com.henry.musictracks.spotify.api.SpotifyClient;
import com.henry.musictracks.spotify.model.Item;
import com.henry.musictracks.spotify.model.TrackObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

    private final TrackRepository trackRepository;
    private final SpotifyClient spotifyClient;

    public Optional<Track> createTrack(String ISRC) {
        try {
            TrackObject trackObject = spotifyClient.fetchTrackMetadata(ISRC);
            if (trackObject != null && trackObject.getTracks() != null && trackObject.getTracks().getItems().size() > 0) {
                log.info(String.format("Creating new Track with ISRC: %s", ISRC));
                Item item = trackObject.getTracks().getItems().get(0);
                Track track = Track.builder()
                        .ISRC(ISRC)
                        .durationTimeMillis(item.getDurationTimeMillis())
                        .explicit(item.isExplicit())
                        .name(item.getName())
                        .build();
                return Optional.of(trackRepository.save(track));
            }
        } catch (IOException e) {
            log.error(String.format("An error has occurred creating Track with ISRC: %s", ISRC), e);
        }

        return Optional.empty();
    }

    public Optional<Track> findByISRC(String ISRC) {
        log.info(String.format("Searching Track with ISRC: %s", ISRC));
        return trackRepository.findByISRC(ISRC);
    }

}
