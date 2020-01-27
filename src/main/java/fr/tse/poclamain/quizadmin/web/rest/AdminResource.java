package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.service.AdminService;
import fr.tse.poclamain.quizadmin.web.rest.errors.BadRequestAlertException;
import fr.tse.poclamain.quizadmin.service.dto.AdminDTO;

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
 * REST controller for managing {@link fr.tse.poclamain.quizadmin.domain.Admin}.
 */
@RestController
@RequestMapping("/api")
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    private static final String ENTITY_NAME = "admin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminService adminService;

    public AdminResource(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * {@code POST  /admins} : Create a new admin.
     *
     * @param adminDTO the adminDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminDTO, or with status {@code 400 (Bad Request)} if the admin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admins")
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) throws URISyntaxException {
        log.debug("REST request to save Admin : {}", adminDTO);
        if (adminDTO.getId() != null) {
            throw new BadRequestAlertException("A new admin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminDTO result = adminService.save(adminDTO);
        return ResponseEntity.created(new URI("/api/admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admins} : Updates an existing admin.
     *
     * @param adminDTO the adminDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminDTO,
     * or with status {@code 400 (Bad Request)} if the adminDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admins")
    public ResponseEntity<AdminDTO> updateAdmin(@RequestBody AdminDTO adminDTO) throws URISyntaxException {
        log.debug("REST request to update Admin : {}", adminDTO);
        if (adminDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminDTO result = adminService.save(adminDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adminDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     */
    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins(Pageable pageable) {
        log.debug("REST request to get a page of Admins");
        Page<AdminDTO> page = adminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admins/:id} : get the "id" admin.
     *
     * @param id the id of the adminDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id) {
        log.debug("REST request to get Admin : {}", id);
        Optional<AdminDTO> adminDTO = adminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminDTO);
    }

    /**
     * {@code DELETE  /admins/:id} : delete the "id" admin.
     *
     * @param id the id of the adminDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        log.debug("REST request to delete Admin : {}", id);
        adminService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
