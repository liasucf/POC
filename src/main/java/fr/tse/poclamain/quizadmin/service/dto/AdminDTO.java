package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Admin} entity.
 */
public class AdminDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdminDTO adminDTO = (AdminDTO) o;
        if (adminDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
            "id=" + getId() +
            "}";
    }
}
