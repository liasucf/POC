package fr.tse.poclamain.quizadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ReponseMapperTest {

    private ReponseMapper reponseMapper;

    @BeforeEach
    public void setUp() {
        reponseMapper = new ReponseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(reponseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reponseMapper.fromId(null)).isNull();
    }
}
