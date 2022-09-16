package com.example.vmo1.controller;

import com.example.vmo1.model.request.ProductDto;
import com.example.vmo1.model.response.MessageResponse;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.repository.ImageRepository;
import com.example.vmo1.service.ImageService;
import com.example.vmo1.service.ProductService;
import com.example.vmo1.validation.annotation.ValidFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductService productService;
    @Value("${project.image}")
    private String path;


    @PostMapping("/add")
    public ResponseEntity<?> uploadMultipleFiles(@Validated @ValidFile @RequestPart("files") MultipartFile[] files,@Valid @RequestPart ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productDto, files));
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.imageService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestPart ProductDto productDto, @PathVariable(name= "id") long id, MultipartFile[] files){
        ProductDto productResponse = productService.updateProduct(productDto, id, files);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ProductResponse getAllProducts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
            return productService.getAllProduct(pageNo, pageSize);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok(new MessageResponse("Product have been delete!!!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    //    @PostMapping("/upload")
//    public ResponseEntity<UploadFileResponse> fileUpload(@RequestParam("file") MultipartFile file, @RequestBody Product product) {
//        String filename = null;
//        try{
//             filename = imageService.upload(file, path);
//
//
//        } catch (IOException e){
//            e.printStackTrace();
//            return new ResponseEntity<>(new UploadFileResponse(null, "Image is not upload success"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(new UploadFileResponse(filename, "Image is success"), HttpStatus.OK);
//    }
}
