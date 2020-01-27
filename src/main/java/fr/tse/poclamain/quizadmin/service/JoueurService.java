package fr.tse.poclamain.quizadmin.service;

import fr.tse.poclamain.quizadmin.service.dto.JoueurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tse.poclamain.quizadmin.domain.Joueur}.
 */
public interface JoueurService {

    /**
     * Save a joueur.
     *
     * @param joueurDTO the entity to save.
     * @return the persisted entity.
     */
    JoueurDTO save(JoueurDTO joueurDTO);

    /**
     * Get all the joueurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JoueurDTO> findAll(Pageable pageable);

    /**
     * Get all the joueurs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<JoueurDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" joueur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JoueurDTO> findOne(Long id);

    /**
     * Delete the "id" joueur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
