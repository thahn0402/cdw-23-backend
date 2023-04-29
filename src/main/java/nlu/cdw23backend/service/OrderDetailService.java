package nlu.cdw23backend.service;

import nlu.cdw23backend.configuration.JwtRequestFilter;
import nlu.cdw23backend.dao.OrderDetailDao;
import nlu.cdw23backend.dao.ProductDao;
import nlu.cdw23backend.dao.UserDao;
import nlu.cdw23backend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACE = "Placed";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o : productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACE,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user

            );

            orderDetailDao.save(orderDetail);
        }
    }
}