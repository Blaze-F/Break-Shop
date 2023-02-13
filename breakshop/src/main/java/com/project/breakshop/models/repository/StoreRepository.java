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
    boolean existsByIdAndUserId(long id, long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Store SET openStatus = 'closed' where id = :id")
    void closeMyStore(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("UPDATE Store SET openStatus = 'open' where id = :id")
    void openMyStore(@Param("id") long id);



    List<Store> findStoreByStoreCategoryId(@Param("id") Long id);


    @Query("SELECT s FROM Store s WHERE s.storeCategory.id = :categoryId AND s.address = :address")
    List<Store> findStoreByStoreCategoryIdAndAddress(@Param("categoryId") long categoryId, @Param("address") String address);

    List<Store> findByUserEmail(String email);

    boolean existsByIdAndUserEmail(long id, String email);
}

