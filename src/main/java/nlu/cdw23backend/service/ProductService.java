package nlu.cdw23backend.service;

import nlu.cdw23backend.configuration.JwtRequestFilter;
import nlu.cdw23backend.dao.CartDao;
import nlu.cdw23backend.dao.ProductDao;
import nlu.cdw23backend.dao.UserDao;
import nlu.cdw23backend.entity.Cart;
import nlu.cdw23backend.entity.Product;
import nlu.cdw23backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 12);

        if (searchKey.equals("")) {
            return (List<Product>) productDao.findAll(pageable);
        } else {
            return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
        }

    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public Product getProductDetailsById(Integer productId) {
        return productDao.findById(productId).get();
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout && productId != 0) {
            //mua mot san pham
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        } else {
            //checkout gio hang
            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();
            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }
    }
}
