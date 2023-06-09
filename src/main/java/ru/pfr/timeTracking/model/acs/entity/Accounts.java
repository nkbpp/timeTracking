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
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_ACC;

    @ManyToOne
    @JoinColumn(name = "ID_EM_ACC", referencedColumnName = "ID_EM", nullable = false)
    private Employees employees;

    private String LOGIN_ACC;

    @ManyToOne
    @JoinColumn(name = "ID_RES_ACC", referencedColumnName = "ID_RES", nullable = false)
    private Res res;

}
