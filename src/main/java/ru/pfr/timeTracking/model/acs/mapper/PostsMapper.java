package ru.pfr.timeTracking.model.acs.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.timeTracking.model.acs.dto.PostsDto;
import ru.pfr.timeTracking.model.acs.dto.ResDto;
import ru.pfr.timeTracking.model.acs.entity.Posts;
import ru.pfr.timeTracking.model.acs.entity.Res;

@Component
@RequiredArgsConstructor
public class PostsMapper {

    public PostsDto toDto(Posts obj) {
        return PostsDto.builder()
                .id(obj.getID_POST())
                .code(obj.getCODE())
                .name(obj.getNAME_POST())
                .head(obj.getHEAD())
                .build();
    }

    public Posts fromDto(PostsDto dto) {
        return Posts.builder()
                .ID_POST(dto.getId())
                .CODE(dto.getCode())
                .NAME_POST(dto.getName())
                .HEAD(dto.getHead())
                .build();
    }

}
