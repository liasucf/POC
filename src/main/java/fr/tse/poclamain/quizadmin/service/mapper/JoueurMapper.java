package fr.tse.poclamain.quizadmin.service.mapper;

import fr.tse.poclamain.quizadmin.domain.*;
import fr.tse.poclamain.quizadmin.service.dto.JoueurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Joueur} and its DTO {@link JoueurDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReponseMapper.class})
public interface JoueurMapper extends EntityMapper<JoueurDTO, Joueur> {


    @Mapping(target = "removeReponses", ignore = true)

    default Joueur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Joueur joueur = new Joueur();
        joueur.setId(id);
        return joueur;
    }
}
