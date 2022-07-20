
package com.jekyloco.repository;

import com.jekyloco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
//<User, Long>前者代表要查哪个表，后者代表id的类型
//继承JpaRepository之后，会默认使用SimpleJpaRepository这个实现类作为UserRepository接口的实体bean
//继承JpaSpecificationExecutor是为了实现动态查询
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return /
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return /
     */
    User findByPhone(String phone);

    /**
     * 根据Id删除
     *
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
}