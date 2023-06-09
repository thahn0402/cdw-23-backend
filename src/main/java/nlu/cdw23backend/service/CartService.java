package nlu.cdw23backend.service;

import nlu.cdw23backend.configuration.JwtRequestFilter;
import nlu.cdw23backend.dao.CartDao;
import nlu.cdw23backend.dao.ProductDao;
import nlu.cdw23backend.dao.UserDao;
import nlu.cdw23backend.entity.Cart;
import nlu.cdw23backend.entity.Product;
import nlu.cdw23backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).get();

        String currentUser = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if (currentUser != null) {
            user = userDao.findById(currentUser).get();
        }

        List<Cart> carts = cartDao.findByUser(user);
        List<Cart> filteredList = carts.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());

        if (filteredList.size() > 0) {
            return null;
        }

        if (product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartDao.save(cart);
        }
        return null;
    }

    public void deleteCartItem(Integer cartId){
        cartDao.deleteById(cartId);
    }

    public List<Cart> getCartDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();
        return cartDao.findByUser(user);
    }

}
