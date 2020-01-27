package fr.tse.poclamain.quizadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.tse.poclamain.quizadmin.web.rest.TestUtil;

public class JoueurDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JoueurDTO.class);
        JoueurDTO joueurDTO1 = new JoueurDTO();
        joueurDTO1.setId(1L);
        JoueurDTO joueurDTO2 = new JoueurDTO();
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
        joueurDTO2.setId(joueurDTO1.getId());
        assertThat(joueurDTO1).isEqualTo(joueurDTO2);
        joueurDTO2.setId(2L);
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
        joueurDTO1.setId(null);
        assertThat(joueurDTO1).isNotEqualTo(joueurDTO2);
    }
}
