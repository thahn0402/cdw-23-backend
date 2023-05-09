package nlu.cdw23backend.dao;

import nlu.cdw23backend.entity.Cart;
import nlu.cdw23backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {
    public List<Cart> findByUser(User user);
}
