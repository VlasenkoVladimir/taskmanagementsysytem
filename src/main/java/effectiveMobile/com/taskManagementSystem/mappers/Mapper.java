package effectiveMobile.com.taskManagementSystem.mappers;


import effectiveMobile.com.taskManagementSystem.domain.GenericModel;
import effectiveMobile.com.taskManagementSystem.dto.GenericDto;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDto> {

    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}
