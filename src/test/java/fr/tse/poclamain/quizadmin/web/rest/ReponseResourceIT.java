package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.QuizAdminApp;
import fr.tse.poclamain.quizadmin.domain.Reponse;
import fr.tse.poclamain.quizadmin.repository.ReponseRepository;
import fr.tse.poclamain.quizadmin.service.ReponseService;
import fr.tse.poclamain.quizadmin.service.dto.ReponseDTO;
import fr.tse.poclamain.quizadmin.service.mapper.ReponseMapper;
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
 * Integration tests for the {@link ReponseResource} REST controller.
 */
@SpringBootTest(classes = QuizAdminApp.class)
public class ReponseResourceIT {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TRUE = false;
    private static final Boolean UPDATED_IS_TRUE = true;

    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private ReponseMapper reponseMapper;

    @Autowired
    private ReponseService reponseService;

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

    private MockMvc restReponseMockMvc;

    private Reponse reponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReponseResource reponseResource = new ReponseResource(reponseService);
        this.restReponseMockMvc = MockMvcBuilders.standaloneSetup(reponseResource)
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
    public static Reponse createEntity(EntityManager em) {
        Reponse reponse = new Reponse()
            .intitule(DEFAULT_INTITULE)
            .isTrue(DEFAULT_IS_TRUE);
        return reponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reponse createUpdatedEntity(EntityManager em) {
        Reponse reponse = new Reponse()
            .intitule(UPDATED_INTITULE)
            .isTrue(UPDATED_IS_TRUE);
        return reponse;
    }

    @BeforeEach
    public void initTest() {
        reponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createReponse() throws Exception {
        int databaseSizeBeforeCreate = reponseRepository.findAll().size();

        // Create the Reponse
        ReponseDTO reponseDTO = reponseMapper.toDto(reponse);
        restReponseMockMvc.perform(post("/api/reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reponseDTO)))
            .andExpect(status().isCreated());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeCreate + 1);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testReponse.isIsTrue()).isEqualTo(DEFAULT_IS_TRUE);
    }

    @Test
    @Transactional
    public void createReponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reponseRepository.findAll().size();

        // Create the Reponse with an existing ID
        reponse.setId(1L);
        ReponseDTO reponseDTO = reponseMapper.toDto(reponse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReponseMockMvc.perform(post("/api/reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReponses() throws Exception {
        // Initialize the database
        reponseRepository.saveAndFlush(reponse);

        // Get all the reponseList
        restReponseMockMvc.perform(get("/api/reponses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].isTrue").value(hasItem(DEFAULT_IS_TRUE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getReponse() throws Exception {
        // Initialize the database
        reponseRepository.saveAndFlush(reponse);

        // Get the reponse
        restReponseMockMvc.perform(get("/api/reponses/{id}", reponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reponse.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.isTrue").value(DEFAULT_IS_TRUE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReponse() throws Exception {
        // Get the reponse
        restReponseMockMvc.perform(get("/api/reponses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReponse() throws Exception {
        // Initialize the database
        reponseRepository.saveAndFlush(reponse);

        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();

        // Update the reponse
        Reponse updatedReponse = reponseRepository.findById(reponse.getId()).get();
        // Disconnect from session so that the updates on updatedReponse are not directly saved in db
        em.detach(updatedReponse);
        updatedReponse
            .intitule(UPDATED_INTITULE)
            .isTrue(UPDATED_IS_TRUE);
        ReponseDTO reponseDTO = reponseMapper.toDto(updatedReponse);

        restReponseMockMvc.perform(put("/api/reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reponseDTO)))
            .andExpect(status().isOk());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
        Reponse testReponse = reponseList.get(reponseList.size() - 1);
        assertThat(testReponse.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testReponse.isIsTrue()).isEqualTo(UPDATED_IS_TRUE);
    }

    @Test
    @Transactional
    public void updateNonExistingReponse() throws Exception {
        int databaseSizeBeforeUpdate = reponseRepository.findAll().size();

        // Create the Reponse
        ReponseDTO reponseDTO = reponseMapper.toDto(reponse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReponseMockMvc.perform(put("/api/reponses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reponse in the database
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReponse() throws Exception {
        // Initialize the database
        reponseRepository.saveAndFlush(reponse);

        int databaseSizeBeforeDelete = reponseRepository.findAll().size();

        // Delete the reponse
        restReponseMockMvc.perform(delete("/api/reponses/{id}", reponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reponse> reponseList = reponseRepository.findAll();
        assertThat(reponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
