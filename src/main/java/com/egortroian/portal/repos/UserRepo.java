package com.egortroian.portal.repos;

import com.egortroian.portal.domain.Message;
import com.egortroian.portal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
