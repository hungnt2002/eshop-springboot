package hungnt2004110032.repository.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hungnt2004110032.model.Product;

public interface ProductService {

	Product getById(Integer id);

	void create(Product item);

	void deleteById(Integer id);

	List<Product> findAll();

	void update(Product item);

	Page<Product> findByCategoryId(Integer cid, Pageable pageable);

	Page<Product> findByKeywords(String keywords, Pageable unpaged);

	Page<Product> findByDiscount(Pageable unpaged);

	Page<Product> findByFavorite(Pageable unpaged);

	Page<Product> findByLatest(Pageable pageable);
	
	Page<Product> findBySpecial(Pageable pageable);

	List<Product> findByUsername(String username);

	Page<Product> findByList(Pageable pageable);



	
}
