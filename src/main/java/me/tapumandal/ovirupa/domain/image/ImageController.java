package me.tapumandal.ovirupa.domain.image;

import com.google.gson.Gson;
import me.tapumandal.ovirupa.service.FileStorageService;
import me.tapumandal.ovirupa.util.CommonResponseArray;
import me.tapumandal.ovirupa.util.CommonResponseSingle;
import me.tapumandal.ovirupa.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class ImageController extends ControllerHelper<Image> {


    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ImageService imageService;

    @GetMapping("/public/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        System.out.println("IMAGE CONTROLLER: downloadFile");
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        if(resource == null) {
            return null;
        }
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping(path = "/image/delete/{name}")
    public CommonResponseSingle<Image> deleteImage(@PathVariable("name") String imageName, HttpServletRequest request) {
        System.out.println("IMAGE CONTROLLER: deleteImage");

        System.out.println("DELETE IMAGE C:"+imageName);
        storeUserDetails(request);

        if (imageService.deleteImageByName(imageName)) {
            return response(true, HttpStatus.OK, "Image by name " + imageName + " is deleted", (Image) null);
        } else{
            return response(false, HttpStatus.NOT_FOUND, "Image not found or deleted", (Image) null);
        }
    }

    @GetMapping(path = "/image/blog/list/{value}")
    public ResponseEntity<List<Image>> getAll(@PathVariable("value") String value, HttpServletRequest request) {
        System.out.println("IMAGE CONTROLLER: getAll");

        storeUserDetails(request);
        List<Image> images = imageService.getAllByImageBlogIdentifire(value);

        if (!images.isEmpty()) {
            return ResponseEntity.ok(images);
        } else if (images.isEmpty()) {
            return ResponseEntity.ok(images);
        } else {
            return ResponseEntity.ok(null);
        }

    }


    @PostMapping(path = "/images/blog/create")
    public ResponseEntity<CommonResponseArray> createProduct(@ModelAttribute ImageDto imageDto, HttpServletRequest request) {
        System.out.println("IMAGE CONTROLLER: createProduct");
        System.out.println("ImageDto: "+new Gson().toJson(imageDto));

        System.out.println(new Gson().toJson(imageDto));
        storeUserDetails(request);

        List<Image> images = imageService.create(imageDto);

        if (images != null) {
            return ResponseEntity.ok(response(true, HttpStatus.CREATED, "New product inserted successfully", images));
        } else if (images == null) {
            return ResponseEntity.ok(response(false, HttpStatus.BAD_REQUEST, "Something is wrong please contact", (List<Image>) null));
        }
        return ResponseEntity.ok(response(false, HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong with the application", (List<Image>) null));
    }

}