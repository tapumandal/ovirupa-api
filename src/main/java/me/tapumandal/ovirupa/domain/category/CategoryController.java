package me.tapumandal.ovirupa.domain.category;

import me.tapumandal.ovirupa.util.ControllerHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/category")
public class CategoryController extends ControllerHelper<Category> {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(path = "/create")
    public ResponseEntity<CategoryDto> createCategory(@ModelAttribute Category c, HttpServletRequest request) {

        storeUserDetails(request);

        Category category = categoryService.create(c);

        if (category != null) {
            return ResponseEntity.ok(convertToDto(category));
        } else{
            return (ResponseEntity<CategoryDto>) ResponseEntity.badRequest();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> getCompany(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);

        Category category = categoryService.getById(id);

        if (category != null) {
            return ResponseEntity.ok(convertToDto(category));
        } else{
            return (ResponseEntity<CategoryDto>) ResponseEntity.badRequest();
        }
    }

    @GetMapping(path = "/list")
    public ResponseEntity getAll(HttpServletRequest request) {

        storeUserDetails(request);

        List<Category> categories = categoryService.getAll();

        List<CategoryDto> responseEntity =  categories.stream().map(this::convertToDto).collect(Collectors.toList());

        return ResponseEntity.ok(responseEntity);
    }


    @PostMapping(path = "/update")
    public ResponseEntity update(@RequestBody Category entity, HttpServletRequest request) {

        storeUserDetails(request);

        Category category = categoryService.update(entity);

        if (category != null) {
            return ResponseEntity.ok(convertToDto(category));
        } else{
            return (ResponseEntity<CategoryDto>) ResponseEntity.badRequest();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") int id, HttpServletRequest request) {

        storeUserDetails(request);
        categoryService.deleteById(id);
        return (ResponseEntity) ResponseEntity.ok();
    }



    private CategoryDto convertToDto(Category entity) {
        CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
        return dto;
    }

}