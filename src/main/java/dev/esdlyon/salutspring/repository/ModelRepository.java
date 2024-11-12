package dev.esdlyon.salutspring.repository;

import dev.esdlyon.salutspring.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {

}
