package nlu.cdw23backend.service;

import nlu.cdw23backend.dao.ProductDao;
import nlu.cdw23backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }
}
