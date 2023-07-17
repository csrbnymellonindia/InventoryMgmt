package com.example.demo.repository;

import com.example.demo.entities.BorrowedAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BorrowedAssetInterfaceRepo extends JpaRepository<BorrowedAssetEntity,Integer> {
    @Query(value = "Select count(*) from asset_borrowed where asset_id = :assetID and user_id = :userID", nativeQuery = true)
    Integer checkRecordCount(Integer assetID, Integer userID);
    @Modifying
    @Transactional
    @Query(value = "Update asset_borrowed set bor_quantity = bor_quantity + :assetQuantity where asset_id = :assetID and user_id=:userID", nativeQuery = true)
    void updateBorrowedAssetRecord(Integer assetID, Integer userID, Integer assetQuantity);
    @Query(value = "Select * from asset_borrowed where asset_id = :assetID and user_id = :userID", nativeQuery = true)
    BorrowedAssetEntity getBorrowRecord(Integer assetID, Integer userID);
    @Modifying
    @Transactional
    @Query(value = "DELETE from asset_borrowed WHERE user_id = :userId and asset_id = :assetId", nativeQuery = true)
    void deleteBorrowedAssetEntity(Integer assetId, Integer userId);
    @Query(value = "SELECT * from asset_borrowed where user_id = :userId",nativeQuery = true)
    List<BorrowedAssetEntity> getBorrowRecordsUser(Integer userId);

    @Modifying
    @Transactional
    @Query(value="DELETE from asset_borrowed WHERE asset_id = :asset_id",nativeQuery=true)
    void deleteBorrowedAssetsUnderThisAsset(Integer asset_id);




}
