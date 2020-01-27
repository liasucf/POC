package fr.tse.poclamain.quizadmin.service.impl;

import fr.tse.poclamain.quizadmin.service.QuizService;
import fr.tse.poclamain.quizadmin.domain.Quiz;
import fr.tse.poclamain.quizadmin.repository.QuizRepository;
import fr.tse.poclamain.quizadmin.service.dto.QuizDTO;
import fr.tse.poclamain.quizadmin.service.mapper.QuizMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Quiz}.
 */
@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);

    private final QuizRepository quizRepository;

    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Save a quiz.
     *
     * @param quizDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QuizDTO save(QuizDTO quizDTO) {
        log.debug("Request to save Quiz : {}", quizDTO);
        Quiz quiz = quizMapper.toEntity(quizDTO);
        quiz = quizRepository.save(quiz);
        return quizMapper.toDto(quiz);
    }

    /**
     * Get all the quizzes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuizDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quizzes");
        return quizRepository.findAll(pageable)
            .map(quizMapper::toDto);
    }


    /**
     * Get one quiz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuizDTO> findOne(Long id) {
        log.debug("Request to get Quiz : {}", id);
        return quizRepository.findById(id)
            .map(quizMapper::toDto);
    }

    /**
     * Delete the quiz by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quiz : {}", id);
        quizRepository.deleteById(id);
    }
}
