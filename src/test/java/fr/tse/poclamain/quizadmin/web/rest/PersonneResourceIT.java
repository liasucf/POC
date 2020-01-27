package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.QuizAdminApp;
import fr.tse.poclamain.quizadmin.domain.Personne;
import fr.tse.poclamain.quizadmin.repository.PersonneRepository;
import fr.tse.poclamain.quizadmin.service.PersonneService;
import fr.tse.poclamain.quizadmin.service.dto.PersonneDTO;
import fr.tse.poclamain.quizadmin.service.mapper.PersonneMapper;
import fr.tse.poclamain.quizadmin.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.tse.poclamain.quizadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@SpringBootTest(classes = QuizAdminApp.class)
public class PersonneResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PersonneMapper personneMapper;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPersonneMockMvc;

    private Personne personne;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonneResource personneResource = new PersonneResource(personneService);
        this.restPersonneMockMvc = MockMvcBuilders.standaloneSetup(personneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createEntity(EntityManager em) {
        Personne personne = new Personne()
            .nom(DEFAULT_NOM);
        return personne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity(EntityManager em) {
        Personne personne = new Personne()
            .nom(UPDATED_NOM);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personne = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne
        PersonneDTO personneDTO = personneMapper.toDto(personne);
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createPersonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne with an existing ID
        personne.setId(1L);
        PersonneDTO personneDTO = personneMapper.toDto(personne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc.perform(post("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get all the personneList
        restPersonneMockMvc.perform(get("/api/personnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        // Disconnect from session so that the updates on updatedPersonne are not directly saved in db
        em.detach(updatedPersonne);
        updatedPersonne
            .nom(UPDATED_NOM);
        PersonneDTO personneDTO = personneMapper.toDto(updatedPersonne);

        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Create the Personne
        PersonneDTO personneDTO = personneMapper.toDto(personne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc.perform(put("/api/personnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc.perform(delete("/api/personnes/{id}", personne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
