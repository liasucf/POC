package fr.tse.poclamain.quizadmin.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "couleur")
    private String couleur;

    @OneToMany(mappedBy = "theme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Theme intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getCouleur() {
        return couleur;
    }

    public Theme couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Theme questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Theme addQuestions(Question question) {
        this.questions.add(question);
        question.setTheme(this);
        return this;
    }

    public Theme removeQuestions(Question question) {
        this.questions.remove(question);
        question.setTheme(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        return id != null && id.equals(((Theme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", couleur='" + getCouleur() + "'" +
            "}";
    }
}
