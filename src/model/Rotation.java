package model;

import java.time.LocalTime;

public enum Rotation {

    ALPHA("Alpha", LocalTime.of(6, 0), LocalTime.of(12, 0)),
    BETA("Beta", LocalTime.of(12, 0), LocalTime.of(18, 0)),
    GAMMA("Gamma", LocalTime.of(18, 0), LocalTime.of(0, 0)),
    DELTA("Delta", LocalTime.of(0, 0), LocalTime.of(6, 0));

    public final String name;
    public final LocalTime start;
    public final LocalTime end;

    Rotation(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.start = startTime;
        this.end = endTime;
    }
}