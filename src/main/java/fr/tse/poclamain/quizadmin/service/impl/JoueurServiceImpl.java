package fr.tse.poclamain.quizadmin.service.impl;

import fr.tse.poclamain.quizadmin.service.JoueurService;
import fr.tse.poclamain.quizadmin.domain.Joueur;
import fr.tse.poclamain.quizadmin.repository.JoueurRepository;
import fr.tse.poclamain.quizadmin.service.dto.JoueurDTO;
import fr.tse.poclamain.quizadmin.service.mapper.JoueurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Joueur}.
 */
@Service
@Transactional
public class JoueurServiceImpl implements JoueurService {

    private final Logger log = LoggerFactory.getLogger(JoueurServiceImpl.class);

    private final JoueurRepository joueurRepository;

    private final JoueurMapper joueurMapper;

    public JoueurServiceImpl(JoueurRepository joueurRepository, JoueurMapper joueurMapper) {
        this.joueurRepository = joueurRepository;
        this.joueurMapper = joueurMapper;
    }

    /**
     * Save a joueur.
     *
     * @param joueurDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JoueurDTO save(JoueurDTO joueurDTO) {
        log.debug("Request to save Joueur : {}", joueurDTO);
        Joueur joueur = joueurMapper.toEntity(joueurDTO);
        joueur = joueurRepository.save(joueur);
        return joueurMapper.toDto(joueur);
    }

    /**
     * Get all the joueurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JoueurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Joueurs");
        return joueurRepository.findAll(pageable)
            .map(joueurMapper::toDto);
    }

    /**
     * Get all the joueurs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<JoueurDTO> findAllWithEagerRelationships(Pageable pageable) {
        return joueurRepository.findAllWithEagerRelationships(pageable).map(joueurMapper::toDto);
    }
    

    /**
     * Get one joueur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JoueurDTO> findOne(Long id) {
        log.debug("Request to get Joueur : {}", id);
        return joueurRepository.findOneWithEagerRelationships(id)
            .map(joueurMapper::toDto);
    }

    /**
     * Delete the joueur by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Joueur : {}", id);
        joueurRepository.deleteById(id);
    }
}
