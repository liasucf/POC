package fr.tse.poclamain.quizadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.tse.poclamain.quizadmin.web.rest.TestUtil;

public class PersonneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneDTO.class);
        PersonneDTO personneDTO1 = new PersonneDTO();
        personneDTO1.setId(1L);
        PersonneDTO personneDTO2 = new PersonneDTO();
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
        personneDTO2.setId(personneDTO1.getId());
        assertThat(personneDTO1).isEqualTo(personneDTO2);
        personneDTO2.setId(2L);
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
        personneDTO1.setId(null);
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
    }
}
