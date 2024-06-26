package hungnt2004110032.repository.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hungnt2004110032.admin.bean.AccountFilter;
import hungnt2004110032.model.Account;
import hungnt2004110032.model.Authority;
import hungnt2004110032.model.Role;
import hungnt2004110032.repository.AccountDAO;
import hungnt2004110032.repository.AuthorityDAO;
import hungnt2004110032.repository.RoleDAO;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDAO dao;
	
	@Autowired
	AuthorityDAO authorityDao;
	
	@Autowired
	RoleDAO roleDao;

	@Override
	public Account getByUsername(String username) {
		return dao.getById(username);
	}

	@Override
	public void deleteByUsername(String username) {
		dao.deleteById(username);
	}

	@Transactional //giao dịch đơn
	@Override
	public void create(Account item, List<String> roleIds) {
		List<Authority> authorities = new ArrayList<>();
		for(String roleId: roleIds) { 
			Role role = roleDao.getById(roleId); // đọc các role từ database ra
			Authority authority = new Authority(null, item, role);
			authorities.add(authority);
		} // chuyển đổi list các mã roles thành các authority
		item.setAuthorities(authorities); // sét authorities vào item(accuont)
		dao.save(item); // lưa Account
		authorityDao.saveAll(authorities); // continue save authorities
	}

	@Transactional
	@Override
	public void update(Account item, List<String> roleIds) {
		authorityDao.deleteAll(dao.getById(item.getUsername()).getAuthorities()); // xóa tất cả vai trò hiện có của tài khoản đó.
		dao.save(item);
		if(!roleIds.isEmpty()) {
			List<Authority> authorities = roleIds.stream().map(rid -> {
				Role role = roleDao.getById(rid);
				Authority authority = new Authority(null, item, role);
				return authority;
			}).collect(Collectors.toList()); // code đoạn 'if' đổi toàn bộ các vai trò sang authorities 
			authorityDao.saveAll(authorities); // lưu vào database
			item.setAuthorities(authorities); // sét authorities vào item(Account) vừa đc update
		}
	}
	
	@Override
	public Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable) {
		String keyword = "%"+filter.getKeyword()+"%";
		// no role
		if(filter.getRole() == 2) { 
			if(filter.getActivated() == 2) {
				return dao.findAccountByKeyword(keyword, pageable);
			}
			return dao.findAccountByKeywordAndActivated(keyword, filter.getActivated() == 1, pageable);
		}
		// customer
		if(filter.getRole() == 0) { 
			if(filter.getActivated() == 2) {
				return dao.findCustomerByKeyword(keyword, pageable);
			}
			return dao.findCustomerByKeywordAndActivated(keyword, filter.getActivated() == 1, pageable);
		}
		// master
		if(filter.getActivated() == 2) {
			return dao.findMasterByKeyword(keyword, pageable);
		}
		return dao.findMasterByKeywordAndActivated(keyword, filter.getActivated() == 1, pageable);
	}

	@Override
	public boolean existByUsername(String username) {
		// TODO Auto-generated method stub
		return dao.existsById(username);
	}
}