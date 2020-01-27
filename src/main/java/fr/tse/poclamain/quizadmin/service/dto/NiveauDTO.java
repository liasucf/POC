package fr.tse.poclamain.quizadmin.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Niveau} entity.
 */
@ApiModel(description = "The Employee entity.")
public class NiveauDTO implements Serializable {

    private Long id;

    private String intitule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NiveauDTO niveauDTO = (NiveauDTO) o;
        if (niveauDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), niveauDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NiveauDTO{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            "}";
    }
}
