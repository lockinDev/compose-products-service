package se.lockin.microservices.core.recommendation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.lockin.api.core.recommendation.Recommendation;
import se.lockin.api.core.recommendation.RecommendationService;
import se.lockin.microservices.core.recommendation.model.RecommendationMapper;
import se.lockin.microservices.core.recommendation.persistence.RecommendationEntity;
import se.lockin.microservices.core.recommendation.persistence.RecommendationRepository;
import se.lockin.util.exceptions.InvalidInputException;
import se.lockin.util.http.ServiceUtil;

import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository repository;

    private final RecommendationMapper mapper;

    private final ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Recommendation createRecommendation(Recommendation body) {
        
        if (body.getProductId() < 1) throw new InvalidInputException("Invalid productId: " + body.getProductId());
    	
        RecommendationEntity entity = mapper.apiToEntity(body);
        Mono<Recommendation> newEntity = repository.save(entity)
            .log()
            .onErrorMap(
                DuplicateKeyException.class,
                ex -> new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Recommendation Id:" + body.getRecommendationId()))
            .map(e -> mapper.entityToApi(e));

        return newEntity.block();
    	
    }

    @Override
    public Flux<Recommendation> getRecommendations(int productId) {

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        return repository.findByProductId(productId)
            .log()
            .map(e -> mapper.entityToApi(e))
            .map(e -> {e.setServiceAddress(serviceUtil.getServiceAddress()); return e;});
    }

    @Override
    public void deleteRecommendations(int productId) {
    	 if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

         LOG.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
         repository.deleteAll(repository.findByProductId(productId)).block();
    }
}
