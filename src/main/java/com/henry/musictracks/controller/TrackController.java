package com.henry.musictracks.controller;

import com.henry.musictracks.model.Track;
import com.henry.musictracks.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/track")
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/{isrc}")
    public ResponseEntity<Track> createTrack(@PathVariable("isrc") String ISRC) {
        trackService.createTrack(ISRC);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(ISRC)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{isrc}")
    public ResponseEntity<Track> getTrackByISRC(@PathVariable("isrc") String ISRC) {
        return ResponseEntity.of(trackService.findByISRC(ISRC));
    }

}
