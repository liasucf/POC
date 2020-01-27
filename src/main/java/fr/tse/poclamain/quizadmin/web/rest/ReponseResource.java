package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.ReponseService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.ReponseDTO;

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
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Reponse}.
 */
@RestController
@RequestMapping("/api")
public class ReponseResource {

    private final Logger log = LoggerFactory.getLogger(ReponseResource.class);

    private static final String ENTITY_NAME = "reponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReponseService reponseService;

    public ReponseResource(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    /**
     * {@code POST  /reponses} : Create a new reponse.
     *
     * @param reponseDTO the reponseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reponseDTO, or with status {@code 400 (Bad Request)} if the reponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reponses")
    public ResponseEntity<ReponseDTO> createReponse(@RequestBody ReponseDTO reponseDTO) throws URISyntaxException {
        log.debug("REST request to save Reponse : {}", reponseDTO);
        if (reponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new reponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReponseDTO result = reponseService.save(reponseDTO);
        return ResponseEntity.created(new URI("/api/reponses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reponses} : Updates an existing reponse.
     *
     * @param reponseDTO the reponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reponseDTO,
     * or with status {@code 400 (Bad Request)} if the reponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reponses")
    public ResponseEntity<ReponseDTO> updateReponse(@RequestBody ReponseDTO reponseDTO) throws URISyntaxException {
        log.debug("REST request to update Reponse : {}", reponseDTO);
        if (reponseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReponseDTO result = reponseService.save(reponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reponseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reponses} : get all the reponses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reponses in body.
     */
    @GetMapping("/reponses")
    public ResponseEntity<List<ReponseDTO>> getAllReponses(Pageable pageable) {
        log.debug("REST request to get a page of Reponses");
        Page<ReponseDTO> page = reponseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reponses/:id} : get the "id" reponse.
     *
     * @param id the id of the reponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reponseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reponses/{id}")
    public ResponseEntity<ReponseDTO> getReponse(@PathVariable Long id) {
        log.debug("REST request to get Reponse : {}", id);
        Optional<ReponseDTO> reponseDTO = reponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reponseDTO);
    }

    /**
     * {@code DELETE  /reponses/:id} : delete the "id" reponse.
     *
     * @param id the id of the reponseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reponses/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable Long id) {
        log.debug("REST request to delete Reponse : {}", id);
        reponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
