package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.QuizAdminApp;
import fr.tse.poclamain.quizadmin.domain.Joueur;
import fr.tse.poclamain.quizadmin.repository.JoueurRepository;
import fr.tse.poclamain.quizadmin.service.JoueurService;
import fr.tse.poclamain.quizadmin.service.dto.JoueurDTO;
import fr.tse.poclamain.quizadmin.service.mapper.JoueurMapper;
import fr.tse.poclamain.quizadmin.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static fr.tse.poclamain.quizadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JoueurResource} REST controller.
 */
@SpringBootTest(classes = QuizAdminApp.class)
public class JoueurResourceIT {

    @Autowired
    private JoueurRepository joueurRepository;

    @Mock
    private JoueurRepository joueurRepositoryMock;

    @Autowired
    private JoueurMapper joueurMapper;

    @Mock
    private JoueurService joueurServiceMock;

    @Autowired
    private JoueurService joueurService;

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

    private MockMvc restJoueurMockMvc;

    private Joueur joueur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JoueurResource joueurResource = new JoueurResource(joueurService);
        this.restJoueurMockMvc = MockMvcBuilders.standaloneSetup(joueurResource)
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
    public static Joueur createEntity(EntityManager em) {
        Joueur joueur = new Joueur();
        return joueur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Joueur createUpdatedEntity(EntityManager em) {
        Joueur joueur = new Joueur();
        return joueur;
    }

    @BeforeEach
    public void initTest() {
        joueur = createEntity(em);
    }

    @Test
    @Transactional
    public void createJoueur() throws Exception {
        int databaseSizeBeforeCreate = joueurRepository.findAll().size();

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);
        restJoueurMockMvc.perform(post("/api/joueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isCreated());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeCreate + 1);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
    }

    @Test
    @Transactional
    public void createJoueurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = joueurRepository.findAll().size();

        // Create the Joueur with an existing ID
        joueur.setId(1L);
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJoueurMockMvc.perform(post("/api/joueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJoueurs() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        // Get all the joueurList
        restJoueurMockMvc.perform(get("/api/joueurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(joueur.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllJoueursWithEagerRelationshipsIsEnabled() throws Exception {
        JoueurResource joueurResource = new JoueurResource(joueurServiceMock);
        when(joueurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restJoueurMockMvc = MockMvcBuilders.standaloneSetup(joueurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restJoueurMockMvc.perform(get("/api/joueurs?eagerload=true"))
        .andExpect(status().isOk());

        verify(joueurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllJoueursWithEagerRelationshipsIsNotEnabled() throws Exception {
        JoueurResource joueurResource = new JoueurResource(joueurServiceMock);
            when(joueurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restJoueurMockMvc = MockMvcBuilders.standaloneSetup(joueurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restJoueurMockMvc.perform(get("/api/joueurs?eagerload=true"))
        .andExpect(status().isOk());

            verify(joueurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        // Get the joueur
        restJoueurMockMvc.perform(get("/api/joueurs/{id}", joueur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(joueur.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJoueur() throws Exception {
        // Get the joueur
        restJoueurMockMvc.perform(get("/api/joueurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();

        // Update the joueur
        Joueur updatedJoueur = joueurRepository.findById(joueur.getId()).get();
        // Disconnect from session so that the updates on updatedJoueur are not directly saved in db
        em.detach(updatedJoueur);
        JoueurDTO joueurDTO = joueurMapper.toDto(updatedJoueur);

        restJoueurMockMvc.perform(put("/api/joueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isOk());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
        Joueur testJoueur = joueurList.get(joueurList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingJoueur() throws Exception {
        int databaseSizeBeforeUpdate = joueurRepository.findAll().size();

        // Create the Joueur
        JoueurDTO joueurDTO = joueurMapper.toDto(joueur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJoueurMockMvc.perform(put("/api/joueurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(joueurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Joueur in the database
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJoueur() throws Exception {
        // Initialize the database
        joueurRepository.saveAndFlush(joueur);

        int databaseSizeBeforeDelete = joueurRepository.findAll().size();

        // Delete the joueur
        restJoueurMockMvc.perform(delete("/api/joueurs/{id}", joueur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Joueur> joueurList = joueurRepository.findAll();
        assertThat(joueurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
