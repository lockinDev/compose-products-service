package se.lockin.microservices.core.review.services;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import se.lockin.api.core.review.Review;
import se.lockin.microservices.core.review.persistence.ReviewEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN , uses=ReviewServiceImpl.class , injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface ReviewMapper {

    @Mappings({
        @Mapping(target = "serviceAddress", ignore = true)
    })
    Review entityToApi(ReviewEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    ReviewEntity apiToEntity(Review api);

    List<Review> entityListToApiList(List<ReviewEntity> entity);
    List<ReviewEntity> apiListToEntityList(List<Review> api);
}