package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.NiveauService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.NiveauDTO;

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
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Niveau}.
 */
@RestController
@RequestMapping("/api")
public class NiveauResource {

    private final Logger log = LoggerFactory.getLogger(NiveauResource.class);

    private static final String ENTITY_NAME = "niveau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NiveauService niveauService;

    public NiveauResource(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    /**
     * {@code POST  /niveaus} : Create a new niveau.
     *
     * @param niveauDTO the niveauDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new niveauDTO, or with status {@code 400 (Bad Request)} if the niveau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/niveaus")
    public ResponseEntity<NiveauDTO> createNiveau(@RequestBody NiveauDTO niveauDTO) throws URISyntaxException {
        log.debug("REST request to save Niveau : {}", niveauDTO);
        if (niveauDTO.getId() != null) {
            throw new BadRequestAlertException("A new niveau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NiveauDTO result = niveauService.save(niveauDTO);
        return ResponseEntity.created(new URI("/api/niveaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /niveaus} : Updates an existing niveau.
     *
     * @param niveauDTO the niveauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveauDTO,
     * or with status {@code 400 (Bad Request)} if the niveauDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the niveauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/niveaus")
    public ResponseEntity<NiveauDTO> updateNiveau(@RequestBody NiveauDTO niveauDTO) throws URISyntaxException {
        log.debug("REST request to update Niveau : {}", niveauDTO);
        if (niveauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NiveauDTO result = niveauService.save(niveauDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, niveauDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /niveaus} : get all the niveaus.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of niveaus in body.
     */
    @GetMapping("/niveaus")
    public ResponseEntity<List<NiveauDTO>> getAllNiveaus(Pageable pageable) {
        log.debug("REST request to get a page of Niveaus");
        Page<NiveauDTO> page = niveauService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /niveaus/:id} : get the "id" niveau.
     *
     * @param id the id of the niveauDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the niveauDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/niveaus/{id}")
    public ResponseEntity<NiveauDTO> getNiveau(@PathVariable Long id) {
        log.debug("REST request to get Niveau : {}", id);
        Optional<NiveauDTO> niveauDTO = niveauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(niveauDTO);
    }

    /**
     * {@code DELETE  /niveaus/:id} : delete the "id" niveau.
     *
     * @param id the id of the niveauDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/niveaus/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        log.debug("REST request to delete Niveau : {}", id);
        niveauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
