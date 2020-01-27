package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Media} and its DTO {@link MediaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {


    @Mapping(target = "reponses", ignore = true)
    @Mapping(target = "removeReponses", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestions", ignore = true)
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
