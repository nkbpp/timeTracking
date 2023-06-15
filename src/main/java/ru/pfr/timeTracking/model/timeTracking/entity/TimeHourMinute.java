package ru.pfr.timeTracking.model.timeTracking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeHourMinute {
    private Integer hour;
    private Integer minute;
}
