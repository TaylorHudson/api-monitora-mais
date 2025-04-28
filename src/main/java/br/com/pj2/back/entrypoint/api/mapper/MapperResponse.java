package br.com.pj2.back.entrypoint.api.mapper;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.entrypoint.api.dto.PointRequest;
import br.com.pj2.back.entrypoint.api.dto.PointResponse;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MapperResponse {

    public static PointResponse toResponse(PointDomain domain){
        PointResponse response = new PointResponse();
        BeanUtils.copyProperties(domain, response);
        return response;
    }

    public static PointDomain toDomain(PointRequest request){
        PointDomain domain = new PointDomain();
        BeanUtils.copyProperties(request, domain);
        return domain;
    }

    public static List<PointResponse> toListResponse(List<PointDomain> domainList){
        return domainList.stream().map(domain -> toResponse(domain)).collect(Collectors.toList());
    }
}
