package ru.pfr.timeTracking.model.timeTracking.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TimeParam {
    @Id
    private String orgCode;

    @Embedded
    private TimeHourMinute mornBegin;

    @Embedded
    private TimeHourMinute mornEnd;

    @Embedded
    private TimeHourMinute evnBegin;

    @Embedded
    private TimeHourMinute evnEnd;

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    //Значения по умолчанию
    @PrePersist
    void init(){
        if(mornBegin == null) {
            mornBegin = new TimeHourMinute(7,59);
        }
        if(mornEnd == null) {
            mornEnd = new TimeHourMinute(9,0);
        }
        if(evnBegin == null) {
            evnBegin = new TimeHourMinute(15,59);
        }
        if(evnEnd == null) {
            evnEnd = new TimeHourMinute(17,0);
        }
    }

}

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class TimeHourMinute {
    private Integer hour;
    private Integer minute;
}