package com.henry.musictracks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "track_sequence")
    private Long id;
    private String ISRC;
    private long durationTimeMillis;
    private boolean explicit;
    private String name;

}
