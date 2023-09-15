package ru.pfr.timeTracking.model.timeTracking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TimeParam {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(12)")
    private String orgCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "mornBegin_hour")),
            @AttributeOverride(name = "minute", column = @Column(name = "mornBegin_minute"))
    })
    private TimeHourMinute mornBegin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "mornEnd_hour")),
            @AttributeOverride(name = "minute", column = @Column(name = "mornEnd_minute"))
    })
    private TimeHourMinute mornEnd;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "evnBegin_hour")),
            @AttributeOverride(name = "minute", column = @Column(name = "evnBegin_minute"))
    })
    private TimeHourMinute evnBegin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "evnEnd_hour")),
            @AttributeOverride(name = "minute", column = @Column(name = "evnEnd_minute"))
    })
    private TimeHourMinute evnEnd;

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    //Значения по умолчанию
    @PrePersist
    void init() {
        if (mornBegin == null) {
            mornBegin = new TimeHourMinute(8, 0);
        }
        if (mornEnd == null) {
            mornEnd = new TimeHourMinute(9, 0);
        }
        if (evnBegin == null) {
            evnBegin = new TimeHourMinute(16, 0);
        }
        if (evnEnd == null) {
            evnEnd = new TimeHourMinute(17, 0);
        }
    }

    public boolean afterMorning(LocalDate date, LocalDateTime timeNow) {
        return LocalDateTime.of(
                date,
                LocalTime.of(mornEnd.getHour(), mornEnd.getMinute())
        ).isBefore(timeNow);
    }

    public boolean afterEvening(LocalDate date, LocalDateTime timeNow) {
        return LocalDateTime.of(
                date,
                LocalTime.of(evnEnd.getHour(), evnEnd.getMinute())
        ).isBefore(timeNow);
    }

    @Transient
    public LocalDateTime getEvening(LocalDate date) {
        return LocalDateTime.of(
                date,
                LocalTime.of(evnEnd.getHour(), evnEnd.getMinute())
        );
    }

    @Transient
    public LocalDateTime getMorning(LocalDate date) {
        return LocalDateTime.of(
                date,
                LocalTime.of(mornEnd.getHour(), mornEnd.getMinute())
        );
    }

}

