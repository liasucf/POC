package fr.tse.poclamain.quizadmin.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Personne} entity.
 */
@ApiModel(description = "not an ignored comment")
public class PersonneDTO implements Serializable {

    private Long id;

    private String nom;


    private Long quizId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonneDTO personneDTO = (PersonneDTO) o;
        if (personneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", quizId=" + getQuizId() +
            "}";
    }
}
