package fr.tse.poclamain.quizadmin.service;

import fr.tse.poclamain.quizadmin.service.dto.AdminDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.tse.poclamain.quizadmin.domain.Admin}.
 */
public interface AdminService {

    /**
     * Save a admin.
     *
     * @param adminDTO the entity to save.
     * @return the persisted entity.
     */
    AdminDTO save(AdminDTO adminDTO);

    /**
     * Get all the admins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdminDTO> findAll(Pageable pageable);


    /**
     * Get the "id" admin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminDTO> findOne(Long id);

    /**
     * Delete the "id" admin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
