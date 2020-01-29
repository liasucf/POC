package fr.tse.poclamain.quizadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reponse.
 */
@Entity
@Table(name = "reponse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "is_true")
    private Boolean isTrue;

    @ManyToOne
    @JsonIgnoreProperties("reponses")
    private Media media;

    @ManyToOne
    @JsonIgnoreProperties("reponses")
    private Question question;

    @ManyToMany(mappedBy = "reponses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Joueur> questions = new HashSet<>();

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

    public Reponse intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Boolean isIsTrue() {
        return isTrue;
    }

    public Reponse isTrue(Boolean isTrue) {
        this.isTrue = isTrue;
        return this;
    }

    public void setIsTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    public Media getMedia() {
        return media;
    }

    public Reponse media(Media media) {
        this.media = media;
        return this;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Question getQuestion() {
        return question;
    }

    public Reponse question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Joueur> getQuestions() {
        return questions;
    }

    public Reponse questions(Set<Joueur> joueurs) {
        this.questions = joueurs;
        return this;
    }

    public Reponse addQuestions(Joueur joueur) {
        this.questions.add(joueur);
        joueur.getReponses().add(this);
        return this;
    }

    public Reponse removeQuestions(Joueur joueur) {
        this.questions.remove(joueur);
        joueur.getReponses().remove(this);
        return this;
    }

    public void setQuestions(Set<Joueur> joueurs) {
        this.questions = joueurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reponse)) {
            return false;
        }
        return id != null && id.equals(((Reponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reponse{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", isTrue='" + isIsTrue() + "'" +
            "}";
    }
}
