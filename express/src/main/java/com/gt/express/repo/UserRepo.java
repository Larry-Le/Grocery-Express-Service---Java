package com.gt.express.repo;



import com.gt.express.domain.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}
