package com.example.demo.repository;

import com.example.demo.entities.Category;
import com.example.demo.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssetRepository extends JpaRepository<Inventory,Integer> {
    @Query(value="SELECT * FROM Inventory WHERE category_category_id = :id",nativeQuery = true)
    List<Inventory> getAll(Integer id);

    @Query(value="SELECT asset_id FROM Inventory WHERE category_category_id=:category_id",nativeQuery=true)
    List<Integer> getAllAssetIdsUnderThisCategory(Integer category_id);


    @Query(value = "Select quantity from inventory where asset_id = :assetID" , nativeQuery = true)
    Integer getAssetQuantity(Integer assetID);

    @Query(value = "Select bor_quantity from inventory where asset_id = :assetID", nativeQuery = true)
    Integer getAssetBorrowQuantity(Integer assetID);
    @Modifying
    @Transactional
    @Query(value = "UPDATE inventory set bor_quantity = bor_quantity + :assetQuantity where asset_id = :assetID ", nativeQuery = true)
    void updateAssetBorQuantity(Integer assetID, Integer assetQuantity);

    @Query(value="SELECT ASSET_NAME FROM INVENTORY WHERE ASSET_ID =:asset_id",nativeQuery = true)
    public String getAssetName(Integer asset_id);



}
