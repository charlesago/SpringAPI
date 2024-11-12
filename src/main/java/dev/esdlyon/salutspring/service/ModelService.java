package dev.esdlyon.salutspring.service;

import dev.esdlyon.salutspring.model.Brand;
import dev.esdlyon.salutspring.model.Model;
import dev.esdlyon.salutspring.repository.BrandRepository;
import dev.esdlyon.salutspring.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }

    public Model save(Model model) {
        return modelRepository.save(model);
    }

    public void deleteById(Long id) {
        modelRepository.deleteById(id);
    }
}
