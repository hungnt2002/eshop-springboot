package estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import estore.model.Role;

public interface RoleDAO extends JpaRepository<Role, String>{

}
