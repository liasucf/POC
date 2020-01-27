package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.NiveauDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Niveau} and its DTO {@link NiveauDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NiveauMapper extends EntityMapper<NiveauDTO, Niveau> {


    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestions", ignore = true)
    Niveau toEntity(NiveauDTO niveauDTO);

    default Niveau fromId(Long id) {
        if (id == null) {
            return null;
        }
        Niveau niveau = new Niveau();
        niveau.setId(id);
        return niveau;
    }
}
