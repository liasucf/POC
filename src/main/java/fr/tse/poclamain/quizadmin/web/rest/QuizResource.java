package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.QuizService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.QuizDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Quiz}.
 */
@RestController
@RequestMapping("/api")
public class QuizResource {

    private final Logger log = LoggerFactory.getLogger(QuizResource.class);

    private static final String ENTITY_NAME = "quiz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuizService quizService;

    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * {@code POST  /quizzes} : Create a new quiz.
     *
     * @param quizDTO the quizDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quizDTO, or with status {@code 400 (Bad Request)} if the quiz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quizzes")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) throws URISyntaxException {
        log.debug("REST request to save Quiz : {}", quizDTO);
        if (quizDTO.getId() != null) {
            throw new BadRequestAlertException("A new quiz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuizDTO result = quizService.save(quizDTO);
        return ResponseEntity.created(new URI("/api/quizzes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quizzes} : Updates an existing quiz.
     *
     * @param quizDTO the quizDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quizDTO,
     * or with status {@code 400 (Bad Request)} if the quizDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quizDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quizzes")
    public ResponseEntity<QuizDTO> updateQuiz(@RequestBody QuizDTO quizDTO) throws URISyntaxException {
        log.debug("REST request to update Quiz : {}", quizDTO);
        if (quizDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuizDTO result = quizService.save(quizDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quizDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quizzes} : get all the quizzes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quizzes in body.
     */
    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(Pageable pageable) {
        log.debug("REST request to get a page of Quizzes");
        Page<QuizDTO> page = quizService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quizzes/:id} : get the "id" quiz.
     *
     * @param id the id of the quizDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quizDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long id) {
        log.debug("REST request to get Quiz : {}", id);
        Optional<QuizDTO> quizDTO = quizService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quizDTO);
    }

    /**
     * {@code DELETE  /quizzes/:id} : delete the "id" quiz.
     *
     * @param id the id of the quizDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        log.debug("REST request to delete Quiz : {}", id);
        quizService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
