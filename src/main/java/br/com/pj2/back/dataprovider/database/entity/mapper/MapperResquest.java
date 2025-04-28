package br.com.pj2.back.dataprovider.database.entity.mapper;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.dataprovider.database.entity.PointEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MapperResquest {
   public static PointDomain toDomain(PointEntity entity){
       PointDomain domain = new PointDomain();
       BeanUtils.copyProperties(entity, domain);
       return domain;
   }
   public static PointEntity toEntity(PointDomain domain){
       PointEntity entity = new PointEntity();
       entity.setId(domain.getId());
       entity.setDescription(domain.getDescription());
       entity.setEndMonitoring(domain.getEndMonitoring());
       entity.setStartMonitoring(domain.getStartMonitoring());
       return entity;
   }

   public static List<PointDomain> toListDomain(List<PointEntity> entityList){
       return entityList.stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
    }

}
