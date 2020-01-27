package fr.tse.poclamain.quizadmin.service.impl;

import fr.tse.poclamain.quizadmin.service.ThemeService;
import fr.tse.poclamain.quizadmin.domain.Theme;
import fr.tse.poclamain.quizadmin.repository.ThemeRepository;
import fr.tse.poclamain.quizadmin.service.dto.ThemeDTO;
import fr.tse.poclamain.quizadmin.service.mapper.ThemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Theme}.
 */
@Service
@Transactional
public class ThemeServiceImpl implements ThemeService {

    private final Logger log = LoggerFactory.getLogger(ThemeServiceImpl.class);

    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    public ThemeServiceImpl(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    /**
     * Save a theme.
     *
     * @param themeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ThemeDTO save(ThemeDTO themeDTO) {
        log.debug("Request to save Theme : {}", themeDTO);
        Theme theme = themeMapper.toEntity(themeDTO);
        theme = themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }

    /**
     * Get all the themes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ThemeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Themes");
        return themeRepository.findAll(pageable)
            .map(themeMapper::toDto);
    }


    /**
     * Get one theme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeDTO> findOne(Long id) {
        log.debug("Request to get Theme : {}", id);
        return themeRepository.findById(id)
            .map(themeMapper::toDto);
    }

    /**
     * Delete the theme by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Theme : {}", id);
        themeRepository.deleteById(id);
    }
}
