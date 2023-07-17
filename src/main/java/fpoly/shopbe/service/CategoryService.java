package fpoly.shopbe.service;

import fpoly.shopbe.domain.Category;
import fpoly.shopbe.exception.CategoryException;
import fpoly.shopbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService{
    @Autowired
    private CategoryRepository dao;

    public List<Category> findByNameStartsWith(String name, Pageable pageable) {
        return dao.findByNameStartsWith(name, pageable);
    }

    public Page<Category> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    public  Category  save(Category entity) {
        return dao.save(entity);
    }

    public Category findById(Long id) {
        Optional<Category> existed = dao.findById(id);

        if(existed.isEmpty()){
            throw new CategoryException("Category id "+ id + " không tồn tại");
        }

        return existed.get();
    }

    public boolean existsById(Long aLong) {
        return dao.existsById(aLong);
    }

    public long count() {
        return dao.count();
    }

    public void deleteById(Long id) {
        Category c = findById(id);
        dao.delete(c);
    }

    public Iterable<Category> findAll() {
        return dao.findAll();
    }

    public Category update(Long id, Category entity) {


        try {
            Category exidtedCategory = findById(id);

            exidtedCategory.setName(entity.getName());
            exidtedCategory.setStatus(entity.getStatus());
            return dao.save(exidtedCategory);
        }catch (Exception e) {
            throw new CategoryException("Cập nhật không thành công");
        }
    }
}
