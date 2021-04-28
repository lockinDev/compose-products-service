package se.lockin.microservices.core.recommendation.model;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import se.lockin.api.core.recommendation.Recommendation;
import se.lockin.microservices.core.recommendation.persistence.RecommendationEntity;
import se.lockin.microservices.core.recommendation.services.RecommendationServiceImpl;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN , uses=RecommendationServiceImpl.class , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface RecommendationMapper {

    @Mappings({
        @Mapping(target = "rate", source="entity.rating"),
        @Mapping(target = "serviceAddress", ignore = true)
    })
    Recommendation entityToApi(RecommendationEntity entity);

    @Mappings({
        @Mapping(target = "rating", source="api.rate"),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    RecommendationEntity apiToEntity(Recommendation api);

    List<Recommendation> entityListToApiList(List<RecommendationEntity> entity);
    List<RecommendationEntity> apiListToEntityList(List<Recommendation> api);
}