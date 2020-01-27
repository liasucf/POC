package fr.tse.poclamain.quizadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.tse.poclamain.quizadmin.web.rest.TestUtil;

public class ReponseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReponseDTO.class);
        ReponseDTO reponseDTO1 = new ReponseDTO();
        reponseDTO1.setId(1L);
        ReponseDTO reponseDTO2 = new ReponseDTO();
        assertThat(reponseDTO1).isNotEqualTo(reponseDTO2);
        reponseDTO2.setId(reponseDTO1.getId());
        assertThat(reponseDTO1).isEqualTo(reponseDTO2);
        reponseDTO2.setId(2L);
        assertThat(reponseDTO1).isNotEqualTo(reponseDTO2);
        reponseDTO1.setId(null);
        assertThat(reponseDTO1).isNotEqualTo(reponseDTO2);
    }
}
