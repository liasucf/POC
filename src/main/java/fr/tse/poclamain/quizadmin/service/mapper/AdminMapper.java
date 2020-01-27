package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.AdminDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admin} and its DTO {@link AdminDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdminMapper extends EntityMapper<AdminDTO, Admin> {


    @Mapping(target = "quizs", ignore = true)
    @Mapping(target = "removeQuizs", ignore = true)
    Admin toEntity(AdminDTO adminDTO);

    default Admin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Admin admin = new Admin();
        admin.setId(id);
        return admin;
    }
}
