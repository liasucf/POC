package fr.tse.poclamain.quizadmin.repository;

import fr.tse.poclamain.quizadmin.domain.Question;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
