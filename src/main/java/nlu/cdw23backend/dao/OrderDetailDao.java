package nlu.cdw23backend.dao;

import nlu.cdw23backend.entity.OrderDetail;
import nlu.cdw23backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(User user);
}
