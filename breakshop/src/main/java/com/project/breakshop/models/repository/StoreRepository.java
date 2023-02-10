package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByUserId(long userId);
    Optional<Store> getByUserIdAndId(long userId, long storeId);
    boolean existByIdAndUserId(long id, long userId);

    @Query(value = "UPDATE STORE SET open_status = 'closed' where id = :id")

    void closeMyStore(long id);

    @Query(value = "UPDATE STORE SET open_status = 'open' where id = :id")
    void openMyStore(long id);

    List<Store> findByStoreCategoryId(long id);
}
