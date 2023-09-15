package ru.pfr.timeTracking.model.acs.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsDto {
    private Long id;
    private String name;
    private Long code;
    private Integer head; // 1 - начальник, 2 - зам, 3 - главный специалист
}
