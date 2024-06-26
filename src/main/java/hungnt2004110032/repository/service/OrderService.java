package hungnt2004110032.repository.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hungnt2004110032.model.Order;
import hungnt2004110032.service.cart.CartService;

public interface OrderService {

	Order getById(Long id);

	void create(Order item);

	void update(Order item);

	void deleteById(Long id);

	Page<Order> findPageByStatusId(Integer statusId, Pageable pageable);

	void create(Order order, CartService cartService);

	List<Order> findByUsername(String username);
}
