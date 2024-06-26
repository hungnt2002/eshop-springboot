package hungnt2004110032.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hungnt2004110032.model.Role;
import hungnt2004110032.repository.RoleDAO;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDAO dao;

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}
}
