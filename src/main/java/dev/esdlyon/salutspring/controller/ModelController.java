package dev.esdlyon.salutspring.controller;


import dev.esdlyon.salutspring.model.Brand;
import dev.esdlyon.salutspring.model.Model;
import dev.esdlyon.salutspring.service.BrandService;
import dev.esdlyon.salutspring.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/series")
public class ModelController {

    private final BrandService brandService;
    private final ModelService modelService;

    @Autowired
    public ModelController( BrandService brandService, ModelService modelService) {
        this.brandService = brandService;
        this.modelService = modelService;
    }

    @GetMapping("/showall")
    public List<Model> getAllModels() {
        return modelService.findAll();
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable Long id) {
        return modelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        if (modelService.findById(id).isPresent()) {
            modelService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/create")
    public ResponseEntity<Model> createModel(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        Long brandId = ((Number) request.get("brand_id")).longValue();

        Brand brand = brandService.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Model model = new Model();
        model.setName(name);
        model.setBrand(brand);
        Model savedModel = modelService.save(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedModel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @RequestBody Map<String, Object> modelDetails) {
        return modelService.findById(id)
                .map(existingModel -> {
                    String name = (String) modelDetails.get("name");
                    existingModel.setName(name);

                    Long brandId = ((Number) modelDetails.get("brand_id")).longValue();
                    Brand brand = brandService.findById(brandId)
                            .orElseThrow(() -> new RuntimeException("Brand not found"));
                    existingModel.setBrand(brand);

                    Model updatedModel = modelService.save(existingModel);
                    return ResponseEntity.ok(updatedModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
