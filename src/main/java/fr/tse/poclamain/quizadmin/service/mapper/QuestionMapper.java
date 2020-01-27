package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class, ThemeMapper.class, NiveauMapper.class, QuizMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "media.id", target = "mediaId")
    @Mapping(source = "theme.id", target = "themeId")
    @Mapping(source = "niveau.id", target = "niveauId")
    @Mapping(source = "quiz.id", target = "quizId")
    QuestionDTO toDto(Question question);

    @Mapping(source = "mediaId", target = "media")
    @Mapping(source = "themeId", target = "theme")
    @Mapping(source = "niveauId", target = "niveau")
    @Mapping(source = "quizId", target = "quiz")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
