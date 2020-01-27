package fr.tse.poclamain.quizadmin.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "media")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reponse> reponses = new HashSet<>();

    @OneToMany(mappedBy = "media")
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

    public Media intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getType() {
        return type;
    }

    public Media type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Reponse> getReponses() {
        return reponses;
    }

    public Media reponses(Set<Reponse> reponses) {
        this.reponses = reponses;
        return this;
    }

    public Media addReponses(Reponse reponse) {
        this.reponses.add(reponse);
        reponse.setMedia(this);
        return this;
    }

    public Media removeReponses(Reponse reponse) {
        this.reponses.remove(reponse);
        reponse.setMedia(null);
        return this;
    }

    public void setReponses(Set<Reponse> reponses) {
        this.reponses = reponses;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Media questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Media addQuestions(Question question) {
        this.questions.add(question);
        question.setMedia(this);
        return this;
    }

    public Media removeQuestions(Question question) {
        this.questions.remove(question);
        question.setMedia(null);
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
        if (!(o instanceof Media)) {
            return false;
        }
        return id != null && id.equals(((Media) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
