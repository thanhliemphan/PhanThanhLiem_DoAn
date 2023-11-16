package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query("update User c set c.enabled = true where c.id = ?1")
    @Modifying
    public void enabled(long id);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    @Query("select u from User u where (u.username like %?1% or u.firstName like %?1% or u.lastName like %?1%)")
    List<User> searchCustomerByKeyword(String keyword);
}
