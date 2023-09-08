package hungnt2004110032.repository.service;

import java.util.List;

import hungnt2004110032.model.Category;

public interface CategoryService {

	List<Category> findAll();

	Category getById(Integer id);

	void create(Category item);

	void update(Category item);

	void deleteById(Integer id);
}
