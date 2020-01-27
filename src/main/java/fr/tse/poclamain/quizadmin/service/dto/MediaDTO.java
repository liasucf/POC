package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Media} entity.
 */
public class MediaDTO implements Serializable {

    private Long id;

    private String intitule;

    private String type;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (mediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
