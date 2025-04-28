package br.com.pj2.back.dataprovider.database.entity.mapper;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.dataprovider.database.entity.PointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    PointDomain toDomain(PointEntity entity);
    PointEntity toEntity(PointDomain domain);

    List<PointDomain> toDomain(List<PointEntity> entity);
}