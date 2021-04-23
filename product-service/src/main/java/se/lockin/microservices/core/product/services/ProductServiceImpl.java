package se.lockin.microservices.core.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import se.lockin.api.core.product.Product;
import se.lockin.api.core.product.ProductService;
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
		return new Product(productId, String.format("name-%s", productId), 123,
				serviceUtil.getServiceAddress());
	}

}
