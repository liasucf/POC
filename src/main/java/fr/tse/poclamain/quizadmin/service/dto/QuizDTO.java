package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Quiz} entity.
 */
public class QuizDTO implements Serializable {

    private Long id;

    private String nom;


    private Long adminId;

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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuizDTO quizDTO = (QuizDTO) o;
        if (quizDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quizDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuizDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adminId=" + getAdminId() +
            "}";
    }
}
