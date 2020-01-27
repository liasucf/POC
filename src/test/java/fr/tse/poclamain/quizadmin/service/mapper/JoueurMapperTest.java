package fr.tse.poclamain.quizadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class JoueurMapperTest {

    private JoueurMapper joueurMapper;

    @BeforeEach
    public void setUp() {
        joueurMapper = new JoueurMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(joueurMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(joueurMapper.fromId(null)).isNull();
    }
}
