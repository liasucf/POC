package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.ThemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Theme} and its DTO {@link ThemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThemeMapper extends EntityMapper<ThemeDTO, Theme> {


    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestions", ignore = true)
    Theme toEntity(ThemeDTO themeDTO);

    default Theme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Theme theme = new Theme();
        theme.setId(id);
        return theme;
    }
}
