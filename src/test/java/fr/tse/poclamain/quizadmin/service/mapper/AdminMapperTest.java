package fr.tse.poclamain.quizadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AdminMapperTest {

    private AdminMapper adminMapper;

    @BeforeEach
    public void setUp() {
        adminMapper = new AdminMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(adminMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adminMapper.fromId(null)).isNull();
    }
}
