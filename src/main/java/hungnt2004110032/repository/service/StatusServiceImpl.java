package hungnt2004110032.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hungnt2004110032.model.Status;
import hungnt2004110032.repository.StatusDAO;

@Service
public class StatusServiceImpl implements StatusService {
	@Autowired
	StatusDAO dao;

	@Override
	public List<Status> findAll() {
		return dao.findAll();
	}
}
