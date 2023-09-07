package estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.model.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
}
