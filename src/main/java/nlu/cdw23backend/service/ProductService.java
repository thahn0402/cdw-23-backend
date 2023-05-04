package nlu.cdw23backend.service;

import nlu.cdw23backend.dao.ProductDao;
import nlu.cdw23backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 2);
        return (List<Product>) productDao.findAll(pageable);
    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public Product getProductDetailsById(Integer productId) {
        return productDao.findById(productId).get();
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout) {
            //going to buy a single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        } else {
            //going to checkout entire cart
        }
        return new ArrayList<>();
    }
}
