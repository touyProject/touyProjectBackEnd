package dev.ioexception.touyProjectBackEnd.repository.categoryRepository;

import dev.ioexception.touyProjectBackEnd.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
