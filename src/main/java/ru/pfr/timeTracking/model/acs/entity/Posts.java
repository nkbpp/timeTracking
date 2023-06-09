package ru.pfr.timeTracking.model.acs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_POST;
    private String NAME_POST;
    private Long CODE;
    private Integer HEAD; // 1 - начальник, 2 - зам, 3 - главный специалист

}
