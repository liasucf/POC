package fr.tse.poclamain.quizadmin.service.impl;

import fr.tse.poclamain.quizadmin.service.AdminService;
import fr.tse.poclamain.quizadmin.domain.Admin;
import fr.tse.poclamain.quizadmin.repository.AdminRepository;
import fr.tse.poclamain.quizadmin.service.dto.AdminDTO;
import fr.tse.poclamain.quizadmin.service.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    /**
     * Save a admin.
     *
     * @param adminDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        log.debug("Request to save Admin : {}", adminDTO);
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    /**
     * Get all the admins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Admins");
        return adminRepository.findAll(pageable)
            .map(adminMapper::toDto);
    }


    /**
     * Get one admin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdminDTO> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id)
            .map(adminMapper::toDto);
    }

    /**
     * Delete the admin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
