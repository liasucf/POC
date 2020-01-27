package fr.tse.poclamain.quizadmin.repository;

import fr.tse.poclamain.quizadmin.domain.Joueur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Joueur entity.
 */
@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

    @Query(value = "select distinct joueur from Joueur joueur left join fetch joueur.reponses",
        countQuery = "select count(distinct joueur) from Joueur joueur")
    Page<Joueur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct joueur from Joueur joueur left join fetch joueur.reponses")
    List<Joueur> findAllWithEagerRelationships();

    @Query("select joueur from Joueur joueur left join fetch joueur.reponses where joueur.id =:id")
    Optional<Joueur> findOneWithEagerRelationships(@Param("id") Long id);

}
