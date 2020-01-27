package fr.tse.poclamain.quizadmin.repository;

import fr.tse.poclamain.quizadmin.domain.Personne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
