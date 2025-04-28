package br.com.pj2.back.entrypoint.api.mapper;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.entrypoint.api.dto.PointRequest;
import br.com.pj2.back.entrypoint.api.dto.PointResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointDtoMapper {
    PointDtoMapper INSTANCE = Mappers.getMapper(PointDtoMapper.class);

    PointDomain toDomain(PointRequest request);
    PointResponse toResponse(PointDomain domain);

    List<PointResponse>  toListResponse(List<PointDomain> domainList);


}