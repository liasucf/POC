package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.JoueurService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.JoueurDTO;

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
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Joueur}.
 */
@RestController
@RequestMapping("/api")
public class JoueurResource {

    private final Logger log = LoggerFactory.getLogger(JoueurResource.class);

    private static final String ENTITY_NAME = "joueur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JoueurService joueurService;

    public JoueurResource(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    /**
     * {@code POST  /joueurs} : Create a new joueur.
     *
     * @param joueurDTO the joueurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new joueurDTO, or with status {@code 400 (Bad Request)} if the joueur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/joueurs")
    public ResponseEntity<JoueurDTO> createJoueur(@RequestBody JoueurDTO joueurDTO) throws URISyntaxException {
        log.debug("REST request to save Joueur : {}", joueurDTO);
        if (joueurDTO.getId() != null) {
            throw new BadRequestAlertException("A new joueur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JoueurDTO result = joueurService.save(joueurDTO);
        return ResponseEntity.created(new URI("/api/joueurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /joueurs} : Updates an existing joueur.
     *
     * @param joueurDTO the joueurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated joueurDTO,
     * or with status {@code 400 (Bad Request)} if the joueurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the joueurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/joueurs")
    public ResponseEntity<JoueurDTO> updateJoueur(@RequestBody JoueurDTO joueurDTO) throws URISyntaxException {
        log.debug("REST request to update Joueur : {}", joueurDTO);
        if (joueurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JoueurDTO result = joueurService.save(joueurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, joueurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /joueurs} : get all the joueurs.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of joueurs in body.
     */
    @GetMapping("/joueurs")
    public ResponseEntity<List<JoueurDTO>> getAllJoueurs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Joueurs");
        Page<JoueurDTO> page;
        if (eagerload) {
            page = joueurService.findAllWithEagerRelationships(pageable);
        } else {
            page = joueurService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /joueurs/:id} : get the "id" joueur.
     *
     * @param id the id of the joueurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the joueurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/joueurs/{id}")
    public ResponseEntity<JoueurDTO> getJoueur(@PathVariable Long id) {
        log.debug("REST request to get Joueur : {}", id);
        Optional<JoueurDTO> joueurDTO = joueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(joueurDTO);
    }

    /**
     * {@code DELETE  /joueurs/:id} : delete the "id" joueur.
     *
     * @param id the id of the joueurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/joueurs/{id}")
    public ResponseEntity<Void> deleteJoueur(@PathVariable Long id) {
        log.debug("REST request to delete Joueur : {}", id);
        joueurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
