package hungnt2004110032.repository.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hungnt2004110032.admin.bean.AccountFilter;
import hungnt2004110032.model.Account;

public interface AccountService {

	Page<Account> findPageByFilter(AccountFilter filter, Pageable pageable);

	Account getByUsername(String username);

	void deleteByUsername(String username);

	void create(Account item, List<String> roleIds);

	void update(Account item, List<String> roleIds);

	boolean existByUsername(String username);
	
}
