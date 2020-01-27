package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.QuizDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quiz} and its DTO {@link QuizDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdminMapper.class})
public interface QuizMapper extends EntityMapper<QuizDTO, Quiz> {

    @Mapping(source = "admin.id", target = "adminId")
    QuizDTO toDto(Quiz quiz);

    @Mapping(target = "personnes", ignore = true)
    @Mapping(target = "removePersonnes", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestions", ignore = true)
    @Mapping(source = "adminId", target = "admin")
    Quiz toEntity(QuizDTO quizDTO);

    default Quiz fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quiz quiz = new Quiz();
        quiz.setId(id);
        return quiz;
    }
}
