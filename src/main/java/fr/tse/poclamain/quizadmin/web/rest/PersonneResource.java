package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.PersonneService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.PersonneDTO;

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
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Personne}.
 */
@RestController
@RequestMapping("/api")
public class PersonneResource {

    private final Logger log = LoggerFactory.getLogger(PersonneResource.class);

    private static final String ENTITY_NAME = "personne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonneService personneService;

    public PersonneResource(PersonneService personneService) {
        this.personneService = personneService;
    }

    /**
     * {@code POST  /personnes} : Create a new personne.
     *
     * @param personneDTO the personneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personneDTO, or with status {@code 400 (Bad Request)} if the personne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personnes")
    public ResponseEntity<PersonneDTO> createPersonne(@RequestBody PersonneDTO personneDTO) throws URISyntaxException {
        log.debug("REST request to save Personne : {}", personneDTO);
        if (personneDTO.getId() != null) {
            throw new BadRequestAlertException("A new personne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonneDTO result = personneService.save(personneDTO);
        return ResponseEntity.created(new URI("/api/personnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnes} : Updates an existing personne.
     *
     * @param personneDTO the personneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personneDTO,
     * or with status {@code 400 (Bad Request)} if the personneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personnes")
    public ResponseEntity<PersonneDTO> updatePersonne(@RequestBody PersonneDTO personneDTO) throws URISyntaxException {
        log.debug("REST request to update Personne : {}", personneDTO);
        if (personneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonneDTO result = personneService.save(personneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personnes} : get all the personnes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnes in body.
     */
    @GetMapping("/personnes")
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes(Pageable pageable) {
        log.debug("REST request to get a page of Personnes");
        Page<PersonneDTO> page = personneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personnes/:id} : get the "id" personne.
     *
     * @param id the id of the personneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personnes/{id}")
    public ResponseEntity<PersonneDTO> getPersonne(@PathVariable Long id) {
        log.debug("REST request to get Personne : {}", id);
        Optional<PersonneDTO> personneDTO = personneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personneDTO);
    }

    /**
     * {@code DELETE  /personnes/:id} : delete the "id" personne.
     *
     * @param id the id of the personneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personnes/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        log.debug("REST request to delete Personne : {}", id);
        personneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
