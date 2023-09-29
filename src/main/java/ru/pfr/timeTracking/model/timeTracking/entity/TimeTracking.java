package ru.pfr.timeTracking.model.timeTracking.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TimeTracking extends AuditEntity{

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private LocalDate currentDate;

    private String orgCode;

    private String login;

    private String fam;

    private String name;

    private String otch;

    //Статусы
    private LocalDateTime beginningOfWork;
    private LocalDateTime endOfWork;

    private Boolean businessTrip; // командировка
    private Boolean vacation; // отпуск
    private Boolean sickLeave; // больничный
    private Integer machination = 0;

    //аудит
    @LastModifiedDate
    private LocalDateTime date_update;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime date_create;

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    //Значения по умолчанию
    @PrePersist
    void init(){
        if(machination == null) {
            machination = 0;
        }
        if(businessTrip == null) {
            businessTrip = false;
        }
        if(vacation == null) {
            vacation = false;
        }
        if(sickLeave == null) {
            sickLeave = false;
        }
    }

}
