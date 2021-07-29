package org.cmsc495.bpo.services;

import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BasicUserDetailsService implements UserDetailsService {
    protected static final Logger log = LoggerFactory.getLogger(BasicUserDetailsService.class);

	private BasicUserRepository repo;

	@Autowired
    public BasicUserDetailsService(BasicUserRepository repo) {
		this.repo = repo;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("Loading Profile for USER [{}]", username);
		try {
			BasicUser user = repo.retrieve(username);
				user.setAccountNonExpired(true);
				user.setAccountNonLocked(true);
				user.setCredentialsNonExpired(true);
				user.setEnabled(true);
			return user;

		} catch (Exception e) {
			log.warn("BASIC USER [{}] not retrieved. Reason: {}", username, e.getLocalizedMessage());
			throw new UsernameNotFoundException(e.getLocalizedMessage());
		}
	}
}
