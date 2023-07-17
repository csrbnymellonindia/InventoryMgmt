package com.example.demo.Controllers;

import com.example.demo.DTO.AssetDTO;
import com.example.demo.DTO.IdObject;
import com.example.demo.DTO.InventoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.entities.History;
import com.example.demo.entities.Inventory;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.BorrowedAssetInterfaceRepo;
import com.example.demo.repository.HistoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BorrowedAssetInterfaceRepo borrowedAssetInterfaceRepo;

    @Autowired
    private ModelMapper mapper;
    @GetMapping(value="/all")
    public List<Inventory> getAllAssets(@RequestBody IdObject object){
        try{
            return assetRepository.getAll(object.getId());
        }
        catch(Exception e){
            ResponseEntity.status(400).body("Invalid Request");
            return Collections.emptyList();
        }
    }

    @PostMapping(value="/new")
//    InventoryDTO: assetId, assetName, categoryId, quantity, bor_quantity, minQuantity
    public ResponseEntity<String> createNewAsset(@RequestBody InventoryDTO inventorydto){
        Inventory inventoryObject= mapper.map(inventorydto, Inventory.class);
        try {
            if(assetRepository.existsById(inventoryObject.getAssetId())){
                return ResponseEntity.status(400).body("AssetID exists");
            }
            assetRepository.save(inventoryObject);
            History historyObject=new History();
            historyObject.setAsset_name(inventoryObject.getAssetName());
            historyObject.setQuantity(inventoryObject.getQuantity());
            historyObject.setDate(LocalDate.now());
            historyObject.setAsset_id(inventoryObject.getAssetId());
            historyObject.setMessage("Added Asset : "+inventoryObject.getQuantity().toString());
            historyObject.setUser_id(null);
            historyObject.setUser_name("ADMIN");
            historyRepository.save(historyObject);
            inventoryObject.setBorQuantity(0);

        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(404).body("Error Connecting To The Database.");
        }
        return ResponseEntity.status(201).body("Asset Created Successfully");

    }


    @PostMapping(value="/update")
    public ResponseEntity<String> updateAsset(@RequestBody InventoryDTO inventoryDTO){
        Inventory inventoryObject= mapper.map(inventoryDTO, Inventory.class);
        try {
            assetRepository.save(inventoryObject);
            Integer present_quantity=assetRepository.findById(inventoryObject.getAssetId()).get().getQuantity();
            if(!present_quantity.equals(inventoryObject.getQuantity())){
                History historyObject=new History();
                historyObject.setAsset_name(inventoryObject.getAssetName());
                historyObject.setQuantity(inventoryObject.getQuantity());
                historyObject.setDate(LocalDate.now());
                historyObject.setAsset_id(inventoryObject.getAssetId());
                historyObject.setMessage("Updated Asset Quantity, Current Quantity : "+inventoryObject.getQuantity().toString());
                historyObject.setUser_id(null);
                historyObject.setUser_name("ADMIN");
                historyRepository.save(historyObject);
            }

        }catch(Exception e){
            return ResponseEntity.status(400).body("Error Connecting To The Database.");
        }
        return ResponseEntity.ok("Asset Information Updated Successfully");
    }
    @PostMapping(value="/remove")
    public ResponseEntity<String> removeAsset(@RequestBody IdObject object){
        try {
            String asset_name = assetRepository.getAssetName(object.getId());
            borrowedAssetInterfaceRepo.deleteBorrowedAssetsUnderThisAsset(object.getId());
            assetRepository.deleteById(object.getId());
            History historyObject=new History();
            historyObject.setAsset_name(asset_name);
            historyObject.setQuantity(null);
            historyObject.setDate(LocalDate.now());
            historyObject.setAsset_id(object.getId());
            historyObject.setMessage("Asset Removed : ");
            historyObject.setUser_id(null);
            historyObject.setUser_name("ADMIN");
            historyRepository.save(historyObject);

        }catch(Exception e){
            return ResponseEntity.status(400).body("Error Connecting To The Database.");
        }
        return ResponseEntity.ok("Asset Deleted Successfully");

    }



}
