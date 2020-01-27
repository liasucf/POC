package fr.tse.poclamain.quizadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MediaMapperTest {

    private MediaMapper mediaMapper;

    @BeforeEach
    public void setUp() {
        mediaMapper = new MediaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(mediaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mediaMapper.fromId(null)).isNull();
    }
}
