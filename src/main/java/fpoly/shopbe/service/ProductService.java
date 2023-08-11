package fpoly.shopbe.service;

import fpoly.shopbe.DTO.ProductBriefDto;
import fpoly.shopbe.DTO.ProductDto;
import fpoly.shopbe.domain.Category;
import fpoly.shopbe.domain.Product;
import fpoly.shopbe.domain.ProductImage;
import fpoly.shopbe.exception.ProductException;
import fpoly.shopbe.repository.ProductImageRepository;
import fpoly.shopbe.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository service;

    @Autowired
    private ProductImageRepository imageService;

    @Autowired
    private FileStorageService file;

    @Transactional(rollbackOn = Exception.class)
    public ProductDto insertProduct(ProductDto dto){
        Product entity = new Product();
        BeanUtils.copyProperties(dto,entity);

        var category = new Category();
        category.setId(dto.getCategoryId());

        entity.setCategory(category);

        if(dto.getImage() != null){
            ProductImage img = new ProductImage();
            BeanUtils.copyProperties(dto.getImage(), img);
            var saveImg = imageService.save(img);
            BeanUtils.copyProperties(saveImg, dto.getImage());
            entity.setImage(saveImg);

        }

        if(dto.getImages() != null && dto.getImages().size()>0){
            var imgList = saveProductImages(dto);
            entity.setImages(imgList);
        }
        System.out.println(entity.getImage().getId());
        var savedProduct = service.save(entity);
        dto.setId(savedProduct.getId());

        return dto;

    }
    @Transactional(rollbackOn = Exception.class)
    public ProductDto updateProduct(Long id, ProductDto dto){
        var found = service.findById(id).orElseThrow(() -> new ProductException("Product not found"));
        String ignoreFields[] = new String[]{"createDate", "image", "images", "viewCount"};
        BeanUtils.copyProperties(dto, found, ignoreFields);

        if(dto.getImage() != null && dto.getImage().getId() != null && found.getImage().getId() != dto.getImage().getId()){
            ProductImage img = new ProductImage();

            BeanUtils.copyProperties(dto.getImage(), img);

            imageService.save(img);
            found.setImage(img);
        }
        var cate = new Category();
        cate.setId(dto.getCategoryId());
        found.setCategory(cate);

        if(dto.getImages().size()>0){
            var toDeleteFile = new ArrayList<ProductImage>();
            found.getImages().stream().map(item ->{
                var existed = dto.getImages().stream().anyMatch(img ->img.getId() == item.getId());

                if(!existed) toDeleteFile.add(item);

                return toDeleteFile;
            });

            if(toDeleteFile.size()>0){
                toDeleteFile.stream().forEach(item->{
                    file.deleteProductImageFile(item.getFileName());
                    imageService.delete(item);
                });
            }

            var imgList = dto.getImages().stream().map(item->{
                ProductImage img = new ProductImage();
                BeanUtils.copyProperties(item, img);

                return img;
            }).collect(Collectors.toSet());
            found.setImages(imgList);
        }
        var saveEntity = service.save(found);
        dto.setId(saveEntity.getId());

        return dto;
    }
    @Transactional(rollbackOn = Exception.class)
    public void deleteproduct(Long id){
        var found = service.findById(id).orElseThrow(()-> new ProductException("Product not found"));

        if(found.getImage() != null){
            file.deleteProductImageFile(found.getImage().getFileName());
            imageService.delete(found.getImage());
        }

        if(found.getImages().size()>0){
            found.getImages().stream().forEach(item->{
                file.deleteProductImageFile(item.getFileName());
                imageService.delete(item);
            });
        }

        service.delete(found);
    }


    public Set<ProductImage> saveProductImages(ProductDto  dto) {
        var entitylist = new HashSet<ProductImage>();

        var newList = dto.getImages().stream().map(item->{
            ProductImage img = new ProductImage();
            BeanUtils.copyProperties(item,img);

            var saveImg = imageService.save(img);
            item.setId(saveImg.getId());

            entitylist.add(saveImg);

            return item;
        }).collect(Collectors.toList());

        dto.setImages(newList);
        return entitylist;
    }

    public Page<ProductBriefDto> getProductByName(String name, Pageable pageable){
        var list = service.findByNameContainsIgnoreCase(name, pageable);

        var newList = list.getContent().stream().map(item ->{
            ProductBriefDto dto = new ProductBriefDto();
            BeanUtils.copyProperties(item, dto);

            dto.setCategoryName(item.getCategory().getName());
            dto.setImageFileName(item.getImage().getFileName());

            return dto;
        }).collect(Collectors.toList());

        var newPage = new PageImpl<ProductBriefDto>(newList, list.getPageable(), list.getTotalPages());

        return newPage;
    }

    public List findAll(){return (List) service.findAll();}

    public Product findById(Long id){return service.findById(id).get();}

}
