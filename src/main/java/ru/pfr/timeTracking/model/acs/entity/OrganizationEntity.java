package ru.pfr.timeTracking.model.acs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
@Table(name = "ORGANIZATION_ENTITY")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String CODE;
    private String FULL_NAME;
    private String NAME;

}
