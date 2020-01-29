package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Reponse} entity.
 */
public class ReponseDTO implements Serializable {

    private Long id;

    private String intitule;

    private Boolean isTrue;


    private Long mediaId;

    private Long questionId;

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

    public Boolean isIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReponseDTO reponseDTO = (ReponseDTO) o;
        if (reponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReponseDTO{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", isTrue='" + isIsTrue() + "'" +
            ", mediaId=" + getMediaId() +
            ", questionId=" + getQuestionId() +
            "}";
    }
}
