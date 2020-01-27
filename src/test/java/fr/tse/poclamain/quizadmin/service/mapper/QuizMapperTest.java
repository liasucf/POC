package fr.tse.poclamain.quizadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class QuizMapperTest {

    private QuizMapper quizMapper;

    @BeforeEach
    public void setUp() {
        quizMapper = new QuizMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(quizMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(quizMapper.fromId(null)).isNull();
    }
}
