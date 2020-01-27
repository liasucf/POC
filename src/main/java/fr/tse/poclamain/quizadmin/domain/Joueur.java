package fr.tse.poclamain.quizadmin.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Joueur.
 */
@Entity
@Table(name = "joueur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "joueur_reponses",
               joinColumns = @JoinColumn(name = "joueur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "reponses_id", referencedColumnName = "id"))
    private Set<Reponse> reponses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Reponse> getReponses() {
        return reponses;
    }

    public Joueur reponses(Set<Reponse> reponses) {
        this.reponses = reponses;
        return this;
    }

    public Joueur addReponses(Reponse reponse) {
        this.reponses.add(reponse);
        reponse.getQuestions().add(this);
        return this;
    }

    public Joueur removeReponses(Reponse reponse) {
        this.reponses.remove(reponse);
        reponse.getQuestions().remove(this);
        return this;
    }

    public void setReponses(Set<Reponse> reponses) {
        this.reponses = reponses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Joueur)) {
            return false;
        }
        return id != null && id.equals(((Joueur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Joueur{" +
            "id=" + getId() +
            "}";
    }
}
