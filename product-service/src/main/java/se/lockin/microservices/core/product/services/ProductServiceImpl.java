package se.lockin.microservices.core.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import se.lockin.api.core.product.Product;
import se.lockin.api.core.product.ProductService;
import se.lockin.util.exceptions.InvalidInputException;
import se.lockin.util.exceptions.NotFoundException;
import se.lockin.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {
	
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	private ServiceUtil serviceUtil;
	
	@Autowired
	public ProductServiceImpl (ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Override
	public Product getProduct(int productId) {
		
		if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        if (productId == 13) throw new NotFoundException("No product found for productId: " + productId);
		
		return new Product(productId, String.format("name-%s", productId), 123,
				serviceUtil.getServiceAddress());
	}

}
