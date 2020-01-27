package fr.tse.poclamain.quizadmin.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Admin.
 */
@Entity
@Table(name = "admin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "admin")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quiz> quizs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Quiz> getQuizs() {
        return quizs;
    }

    public Admin quizs(Set<Quiz> quizzes) {
        this.quizs = quizzes;
        return this;
    }

    public Admin addQuizs(Quiz quiz) {
        this.quizs.add(quiz);
        quiz.setAdmin(this);
        return this;
    }

    public Admin removeQuizs(Quiz quiz) {
        this.quizs.remove(quiz);
        quiz.setAdmin(null);
        return this;
    }

    public void setQuizs(Set<Quiz> quizzes) {
        this.quizs = quizzes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        return id != null && id.equals(((Admin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            "}";
    }
}
