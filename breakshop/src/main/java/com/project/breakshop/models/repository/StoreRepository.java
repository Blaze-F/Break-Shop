package com.project.breakshop.models.repository;

import com.project.breakshop.models.entity.Store;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByUserId(long userId);
    Optional<Store> getByUserIdAndId(long userId, long storeId);
    boolean existByIdAndUserId(long id, long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Store SET openStatus = 'closed' where id = :id")
    void closeMyStore(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("UPDATE Store SET openStatus = 'open' where id = :id")
    void openMyStore(@Param("id") long id);


    @Query("SELECT s FROM Store s WHERE s.storeCategorySet.id = :id")
    List<Store> findByStoreCategoryId(@Param("id") long id);

    @Query("SELECT s FROM Store s WHERE s.storeCategorySet.id = :categoryId AND s.address = :address")
    List<Store> findStoreListByCategoryAndAddress(@Param("categoryId") long categoryId, @Param("address") String address);

    List<Store> findByUserEmail(String email);
}

