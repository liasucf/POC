package fr.tse.poclamain.quizadmin.service;

import fr.tse.poclamain.quizadmin.service.dto.MediaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tse.poclamain.quizadmin.domain.Media}.
 */
public interface MediaService {

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save.
     * @return the persisted entity.
     */
    MediaDTO save(MediaDTO mediaDTO);

    /**
     * Get all the media.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MediaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" media.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MediaDTO> findOne(Long id);

    /**
     * Delete the "id" media.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
