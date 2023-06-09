package ru.pfr.timeTracking.model.acs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_EM;

    private String FAM_EM;
    private String NAM_EM;
    private String OTCH_EM;
    private LocalDate DISMISSAL_DATE_EM; //дата увольнения

/*    @ManyToOne
    @JoinColumn(name = "ID_POD_EM", referencedColumnName = "ID_POD", nullable = false)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private Podrazd podrazd;*/

    @ManyToOne
    @JoinColumn(name = "ID_POST_EM", referencedColumnName = "ID_POST", nullable = false)
    private Posts posts;

    private LocalDate VACATION_END_DATE;
    private LocalDate VACATION_START_DATE;

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ENTITY_ID", referencedColumnName = "ID", nullable = false)
    private OrganizationEntity organizationEntity;

}
