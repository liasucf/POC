package fr.tse.poclamain.quizadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Quiz.
 */
@Entity
@Table(name = "quiz")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "quiz")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Personne> personnes = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("quizs")
    private Admin admin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Quiz nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Personne> getPersonnes() {
        return personnes;
    }

    public Quiz personnes(Set<Personne> personnes) {
        this.personnes = personnes;
        return this;
    }

    public Quiz addPersonnes(Personne personne) {
        this.personnes.add(personne);
        personne.setQuiz(this);
        return this;
    }

    public Quiz removePersonnes(Personne personne) {
        this.personnes.remove(personne);
        personne.setQuiz(null);
        return this;
    }

    public void setPersonnes(Set<Personne> personnes) {
        this.personnes = personnes;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Quiz questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Quiz addQuestions(Question question) {
        this.questions.add(question);
        question.setQuiz(this);
        return this;
    }

    public Quiz removeQuestions(Question question) {
        this.questions.remove(question);
        question.setQuiz(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Quiz admin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quiz)) {
            return false;
        }
        return id != null && id.equals(((Quiz) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Quiz{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
