package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.ReponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reponse} and its DTO {@link ReponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class})
public interface ReponseMapper extends EntityMapper<ReponseDTO, Reponse> {

    @Mapping(source = "media.id", target = "mediaId")
    ReponseDTO toDto(Reponse reponse);

    @Mapping(source = "mediaId", target = "media")
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestions", ignore = true)
    Reponse toEntity(ReponseDTO reponseDTO);

    default Reponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reponse reponse = new Reponse();
        reponse.setId(id);
        return reponse;
    }
}
