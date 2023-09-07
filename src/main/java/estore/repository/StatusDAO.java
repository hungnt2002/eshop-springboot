package estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.model.Status;

public interface StatusDAO extends JpaRepository<Status, Integer>{
}
