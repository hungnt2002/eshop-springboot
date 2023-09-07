package estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.model.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{
	
}
