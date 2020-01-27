package fr.tse.poclamain.quizadmin.web.rest;

import fr.tse.poclamain.quizadmin.QuizAdminApp;
import fr.tse.poclamain.quizadmin.domain.Theme;
import fr.tse.poclamain.quizadmin.repository.ThemeRepository;
import fr.tse.poclamain.quizadmin.service.ThemeService;
import fr.tse.poclamain.quizadmin.service.dto.ThemeDTO;
import fr.tse.poclamain.quizadmin.service.mapper.ThemeMapper;
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
 * Integration tests for the {@link ThemeResource} REST controller.
 */
@SpringBootTest(classes = QuizAdminApp.class)
public class ThemeResourceIT {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeService themeService;

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

    private MockMvc restThemeMockMvc;

    private Theme theme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThemeResource themeResource = new ThemeResource(themeService);
        this.restThemeMockMvc = MockMvcBuilders.standaloneSetup(themeResource)
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
    public static Theme createEntity(EntityManager em) {
        Theme theme = new Theme()
            .intitule(DEFAULT_INTITULE)
            .couleur(DEFAULT_COULEUR);
        return theme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createUpdatedEntity(EntityManager em) {
        Theme theme = new Theme()
            .intitule(UPDATED_INTITULE)
            .couleur(UPDATED_COULEUR);
        return theme;
    }

    @BeforeEach
    public void initTest() {
        theme = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheme() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate + 1);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testTheme.getCouleur()).isEqualTo(DEFAULT_COULEUR);
    }

    @Test
    @Transactional
    public void createThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme with an existing ID
        theme.setId(1L);
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThemes() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR)));
    }
    
    @Test
    @Transactional
    public void getTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", theme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(theme.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR));
    }

    @Test
    @Transactional
    public void getNonExistingTheme() throws Exception {
        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme
        Theme updatedTheme = themeRepository.findById(theme.getId()).get();
        // Disconnect from session so that the updates on updatedTheme are not directly saved in db
        em.detach(updatedTheme);
        updatedTheme
            .intitule(UPDATED_INTITULE)
            .couleur(UPDATED_COULEUR);
        ThemeDTO themeDTO = themeMapper.toDto(updatedTheme);

        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testTheme.getCouleur()).isEqualTo(UPDATED_COULEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Delete the theme
        restThemeMockMvc.perform(delete("/api/themes/{id}", theme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
