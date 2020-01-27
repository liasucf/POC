package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Joueur} entity.
 */
public class JoueurDTO implements Serializable {

    private Long id;


    private Set<ReponseDTO> reponses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ReponseDTO> getReponses() {
        return reponses;
    }

    public void setReponses(Set<ReponseDTO> reponses) {
        this.reponses = reponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JoueurDTO joueurDTO = (JoueurDTO) o;
        if (joueurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), joueurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JoueurDTO{" +
            "id=" + getId() +
            "}";
    }
}
