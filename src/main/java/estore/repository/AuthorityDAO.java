package estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.model.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
}
