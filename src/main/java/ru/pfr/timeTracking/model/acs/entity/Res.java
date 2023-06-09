package ru.pfr.timeTracking.model.acs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
public class Res  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_RES;
    private String NAME_RES;
    private Long CODE_RES;

}
