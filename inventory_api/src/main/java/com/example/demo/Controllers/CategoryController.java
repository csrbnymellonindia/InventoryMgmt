package com.example.demo.Controllers;

import com.example.demo.DTO.IdObject;
import com.example.demo.entities.Category;
import com.example.demo.entities.History;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.BorrowedAssetInterfaceRepo;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private BorrowedAssetInterfaceRepo borrowedAssetInterfaceRepo;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping(value="/all")
    public List<Category> getAllCategories(){
        return categoryRepository.getAll();
    }

    //    Category : categoryId, categoryName,
    @PostMapping(value="/new")
    public ResponseEntity<String> createNewCategory(@RequestBody Category category){

        try {
            if(categoryRepository.existsById(category.getCategoryId())){
                return ResponseEntity.status(400).body("CategoryID exists");
            }
            categoryRepository.save(category);
        }catch(Exception e){
            return ResponseEntity.status(400).body("Error Connecting To The Database.");
        }
        return ResponseEntity.ok("Category Created Successfully");


    }

    @PostMapping(value="/update")
    public ResponseEntity<String> updateCategory(@RequestBody Category category){
        try {
            categoryRepository.save(category);
        }catch(Exception e){
            return ResponseEntity.status(400).body("Error Connecting To The Database.");
        }
        return ResponseEntity.ok("Category Updated Successfully");
    }

    @PostMapping(value="/delete")
    public ResponseEntity<String> deleteCategory(@RequestBody IdObject object){
        try {
            // delete all assets under this category
            // delete all borrowed assets under borrowed table
            List<Integer> allAssetIds=assetRepository.getAllAssetIdsUnderThisCategory(object.getId());
            for(Integer asset_id:allAssetIds){
                borrowedAssetInterfaceRepo.deleteBorrowedAssetsUnderThisAsset(asset_id);
                assetRepository.deleteById(asset_id);
            }
            categoryRepository.deleteById(object.getId());
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(400).body("Error Connecting To The Database.");
        }
        History historyObject=new History();
        historyObject.setAsset_name(null);
        historyObject.setQuantity(null);
        historyObject.setDate(LocalDate.now());
        historyObject.setAsset_id(null);
        historyObject.setMessage("Category was removed : "+object.getId().toString());
        historyObject.setUser_id(null);
        historyObject.setUser_name("Admin");
        historyRepository.save(historyObject);
        return ResponseEntity.ok("Category Deleted Successfully");
    }



}
