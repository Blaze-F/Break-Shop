package com.project.breakshop.models.repository;
import com.project.breakshop.models.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> getByEmail(String email);
    Optional<User> getByName(String name);
    boolean existsByEmail(String email);

    void deleteByEmail(String email);


    @Query(value = "update user set password = :#{#password} where email = :#{#email}", nativeQuery = true)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    public void updateUserPassword(@Param(value = "email") String email,@Param(value = "password") String password );



}

/*
    @Modifying 사용하는 이유
    위의 쿼리처럼 select구문이 아닌 DML(insert, update, delete) 구문을 사용할 때는 다음의 어노테이션을 추가적으로 사용해야 됩니다.
    @Modifying
    @Transactional
    먼저 @Modifying은 insert, update, delete 뿐만 아니라 DDL구문을 사용할 때도 표기를 해줘야 됩니다.
    왜냐하면 영속성 컨텍스트에 오래된 데이터를 비워주고 새로운 데이터를 읽어오기 위해서입니다.
    그리고 @Transactional은 update, delete를 할 때 표기를 해줘야 정상 실행이 됩니다.
    update구문으로 예제 코드를 만들어 보겠습니다.
*/