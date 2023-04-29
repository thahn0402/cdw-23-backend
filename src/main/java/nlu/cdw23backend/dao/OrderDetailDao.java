package nlu.cdw23backend.dao;

import nlu.cdw23backend.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
}
