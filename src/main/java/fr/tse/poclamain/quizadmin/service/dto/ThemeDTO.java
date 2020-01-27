package fr.tse.poclamain.quizadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.tse.poclamain.quizadmin.domain.Theme} entity.
 */
public class ThemeDTO implements Serializable {

    private Long id;

    private String intitule;

    private String couleur;


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

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThemeDTO themeDTO = (ThemeDTO) o;
        if (themeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThemeDTO{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", couleur='" + getCouleur() + "'" +
            "}";
    }
}
