package fr.tse.poclamain.quizadmin.service;

import fr.tse.poclamain.quizadmin.service.dto.PersonneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tse.poclamain.quizadmin.domain.Personne}.
 */
public interface PersonneService {

    /**
     * Save a personne.
     *
     * @param personneDTO the entity to save.
     * @return the persisted entity.
     */
    PersonneDTO save(PersonneDTO personneDTO);

    /**
     * Get all the personnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" personne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonneDTO> findOne(Long id);

    /**
     * Delete the "id" personne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
