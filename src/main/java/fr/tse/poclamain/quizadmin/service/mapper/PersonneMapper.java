package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.PersonneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personne} and its DTO {@link PersonneDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuizMapper.class})
public interface PersonneMapper extends EntityMapper<PersonneDTO, Personne> {

    @Mapping(source = "quiz.id", target = "quizId")
    PersonneDTO toDto(Personne personne);

    @Mapping(source = "quizId", target = "quiz")
    Personne toEntity(PersonneDTO personneDTO);

    default Personne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(id);
        return personne;
    }
}
