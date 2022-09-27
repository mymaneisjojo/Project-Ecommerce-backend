package com.example.vmo1.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.*;
import com.example.vmo1.model.request.ImageDto;
import com.example.vmo1.model.request.ProductRequest;
import com.example.vmo1.model.response.ProductResponse;
import com.example.vmo1.repository.ImageRepository;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.security.service.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private ImageRepository mockImageRepository;
    @Mock
    private Cloudinary mockCloudinary;

    @InjectMocks
    private ProductServiceImpl productServiceImplUnderTest;

    @Test
    public void testSave() {
        // Setup
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        shop2.setName("name");
        shop2.setAddress("address");
        shop2.setBanner("banner");
        shop2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        final ProductRequest metaData = new ProductRequest(0L, "name", 0.0f, 0, false, new Category(0L, "name", false,
                Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image),
                                shop1, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop2, Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)));
        final MultipartFile[] files = new MultipartFile[]{};
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setImageUrl("imageUrl");
        final Product product1 = new Product();
        product1.setId(0L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.set_deleted(false);
        product1.setLstImg(Arrays.asList(new Image()));
        final Shop shop3 = new Shop();
        shop3.setId(0L);
        product1.setShop(shop3);
        image1.setProduct(product1);
        final Shop shop4 = new Shop();
        shop4.setId(0L);
        shop4.setName("name");
        shop4.setAddress("address");
        shop4.setBanner("banner");
        shop4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.set_deleted(false);
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setAccount(account2);
        final Shop shop5 = new Shop();
        shop5.setId(0L);
        shop5.setName("name");
        shop5.setAddress("address");
        shop5.setBanner("banner");
        shop5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.set_deleted(false);
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("phone");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.setAccount(account3);
        final ProductRequest expectedResult = new ProductRequest(0L, "name", 0.0f, 0, false,
                new Category(0L, "name", false, Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                                Arrays.asList(image1), shop4, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop5, Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)));

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Configure ProductRepository.save(...).
        final Image image2 = new Image();
        image2.setId(0L);
        image2.setFileName("fileName");
        image2.setFileType("fileType");
        image2.setImageUrl("imageUrl");
        final Product product3 = new Product();
        product3.setId(0L);
        product3.setName("name");
        product3.setQuantity(0);
        product3.setPrice(0.0f);
        product3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.set_deleted(false);
        product3.setLstImg(Arrays.asList(new Image()));
        final Shop shop6 = new Shop();
        shop6.setId(0L);
        product3.setShop(shop6);
        image2.setProduct(product3);
        final Shop shop7 = new Shop();
        shop7.setId(0L);
        shop7.setName("name");
        shop7.setAddress("address");
        shop7.setBanner("banner");
        shop7.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.set_deleted(false);
        final Account account4 = new Account();
        account4.setId(0L);
        account4.setFullname("fullname");
        account4.setUsername("username");
        account4.setPassword("password");
        account4.setEmail("email");
        account4.setPhone("phone");
        account4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.setAccount(account4);
        final Product product2 = new Product(0L, "name", 0, 0.0f,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image2), shop7,
                new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                Arrays.asList(new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        when(mockProductRepository.save(
                new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(new Image()),
                        new Shop(), new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                        new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                        Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        false)))))).thenReturn(product2);

        // Configure ImageRepository.saveAll(...).
        final Image image3 = new Image();
        image3.setId(0L);
        image3.setFileName("fileName");
        image3.setFileType("fileType");
        image3.setImageUrl("imageUrl");
        final Product product4 = new Product();
        product4.setId(0L);
        product4.setName("name");
        product4.setQuantity(0);
        product4.setPrice(0.0f);
        product4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product4.set_deleted(false);
        product4.setLstImg(Arrays.asList(new Image()));
        final Shop shop8 = new Shop();
        shop8.setId(0L);
        product4.setShop(shop8);
        image3.setProduct(product4);
        final List<Image> images = Arrays.asList(image3);
        when(mockImageRepository.saveAll(Arrays.asList(new Image()))).thenReturn(images);

        // Run the test
        final ProductRequest result = productServiceImplUnderTest.save(metaData, files);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockImageRepository).saveAll(Arrays.asList(new Image()));
    }

    @Test
    public void testUpload() throws Exception {
        // Setup
        final MultipartFile multipartFile = null;

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Run the test
        final Map result = productServiceImplUnderTest.upload(null);

        // Verify the results
    }

    @Test
    public void testUpload_ThrowsIOException() {
        // Setup
        final MultipartFile multipartFile = null;

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Run the test
        assertThrows(IOException.class, () -> productServiceImplUnderTest.upload(multipartFile));
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        shop2.setName("name");
        shop2.setAddress("address");
        shop2.setBanner("banner");
        shop2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        final ProductRequest metaData = new ProductRequest(0L, "name", 0.0f, 0, false, new Category(0L, "name", false,
                Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image),
                                shop1, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop2, Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)));
        final MultipartFile[] files = new MultipartFile[]{};
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setImageUrl("imageUrl");
        final Product product1 = new Product();
        product1.setId(0L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.set_deleted(false);
        product1.setLstImg(Arrays.asList(new Image()));
        final Shop shop3 = new Shop();
        shop3.setId(0L);
        product1.setShop(shop3);
        image1.setProduct(product1);
        final Shop shop4 = new Shop();
        shop4.setId(0L);
        shop4.setName("name");
        shop4.setAddress("address");
        shop4.setBanner("banner");
        shop4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.set_deleted(false);
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setAccount(account2);
        final Shop shop5 = new Shop();
        shop5.setId(0L);
        shop5.setName("name");
        shop5.setAddress("address");
        shop5.setBanner("banner");
        shop5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.set_deleted(false);
        final Account account3 = new Account();
        account3.setId(0L);
        account3.setFullname("fullname");
        account3.setUsername("username");
        account3.setPassword("password");
        account3.setEmail("email");
        account3.setPhone("phone");
        account3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop5.setAccount(account3);
        final ProductRequest expectedResult = new ProductRequest(0L, "name", 0.0f, 0, false,
                new Category(0L, "name", false, Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
                                Arrays.asList(image1), shop4, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop5, Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)));

        // Configure ProductRepository.findById(...).
        final Image image2 = new Image();
        image2.setId(0L);
        image2.setFileName("fileName");
        image2.setFileType("fileType");
        image2.setImageUrl("imageUrl");
        final Product product3 = new Product();
        product3.setId(0L);
        product3.setName("name");
        product3.setQuantity(0);
        product3.setPrice(0.0f);
        product3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.set_deleted(false);
        product3.setLstImg(Arrays.asList(new Image()));
        final Shop shop6 = new Shop();
        shop6.setId(0L);
        product3.setShop(shop6);
        image2.setProduct(product3);
        final Shop shop7 = new Shop();
        shop7.setId(0L);
        shop7.setName("name");
        shop7.setAddress("address");
        shop7.setBanner("banner");
        shop7.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.set_deleted(false);
        final Account account4 = new Account();
        account4.setId(0L);
        account4.setFullname("fullname");
        account4.setUsername("username");
        account4.setPassword("password");
        account4.setEmail("email");
        account4.setPhone("phone");
        account4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop7.setAccount(account4);
        final Optional<Product> product2 = Optional.of(
                new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image2), shop7,
                        new Category(0L, "name", false, Arrays.asList(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(
                        Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                        new HashSet<>(Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)))));
        when(mockProductRepository.findById(0L)).thenReturn(product2);

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Configure ProductRepository.save(...).
        final Image image3 = new Image();
        image3.setId(0L);
        image3.setFileName("fileName");
        image3.setFileType("fileType");
        image3.setImageUrl("imageUrl");
        final Product product5 = new Product();
        product5.setId(0L);
        product5.setName("name");
        product5.setQuantity(0);
        product5.setPrice(0.0f);
        product5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product5.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product5.set_deleted(false);
        product5.setLstImg(Arrays.asList(new Image()));
        final Shop shop8 = new Shop();
        shop8.setId(0L);
        product5.setShop(shop8);
        image3.setProduct(product5);
        final Shop shop9 = new Shop();
        shop9.setId(0L);
        shop9.setName("name");
        shop9.setAddress("address");
        shop9.setBanner("banner");
        shop9.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop9.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop9.set_deleted(false);
        final Account account5 = new Account();
        account5.setId(0L);
        account5.setFullname("fullname");
        account5.setUsername("username");
        account5.setPassword("password");
        account5.setEmail("email");
        account5.setPhone("phone");
        account5.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop9.setAccount(account5);
        final Product product4 = new Product(0L, "name", 0, 0.0f,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image3), shop9,
                new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                Arrays.asList(new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        when(mockProductRepository.save(
                new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(new Image()),
                        new Shop(), new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                        new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                        Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        false)))))).thenReturn(product4);

        // Configure ImageRepository.saveAll(...).
        final Image image4 = new Image();
        image4.setId(0L);
        image4.setFileName("fileName");
        image4.setFileType("fileType");
        image4.setImageUrl("imageUrl");
        final Product product6 = new Product();
        product6.setId(0L);
        product6.setName("name");
        product6.setQuantity(0);
        product6.setPrice(0.0f);
        product6.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product6.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product6.set_deleted(false);
        product6.setLstImg(Arrays.asList(new Image()));
        final Shop shop10 = new Shop();
        shop10.setId(0L);
        product6.setShop(shop10);
        image4.setProduct(product6);
        final List<Image> images = Arrays.asList(image4);
        when(mockImageRepository.saveAll(Arrays.asList(new Image()))).thenReturn(images);

        // Run the test
        final ProductRequest result = productServiceImplUnderTest.updateProduct(metaData, 0L, files);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockImageRepository).deleteImagesByProduct(0L);
        verify(mockImageRepository).saveAll(Arrays.asList(new Image()));
    }

    @Test
    public void testUpdateProduct_ProductRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        shop2.setName("name");
        shop2.setAddress("address");
        shop2.setBanner("banner");
        shop2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        final ProductRequest metaData = new ProductRequest(0L, "name", 0.0f, 0, false, new Category(0L, "name", false,
                Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image),
                                shop1, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop2, Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)));
        final MultipartFile[] files = new MultipartFile[]{};
        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class,
                () -> productServiceImplUnderTest.updateProduct(metaData, 0L, files));
    }

    @Test
    public void testGetAllProduct() {
        // Setup
        final CustomUserDetails customUserDetails = new CustomUserDetails(0L, "username", "email", "password",
                Arrays.asList());
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        shop2.setName("name");
        shop2.setAddress("address");
        shop2.setBanner("banner");
        shop2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        final ProductResponse expectedResult = new ProductResponse(Arrays.asList(
                new ProductRequest(0L, "name", 0.0f, 0, false, new Category(0L, "name", false, Arrays.asList(
                        new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image),
                                shop1, null, new HashSet<>(Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                                new HashSet<>(Arrays.asList(new Size(0, "name", false,
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false),
                        Arrays.asList(new ImageDto(0L, "fileName", "fileType", "imageUrl")), shop2, Arrays.asList(
                        new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)), Arrays.asList(
                        new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)))), 0, 0, 0L, 0,
                false);

        // Configure ProductRepository.findAllByShopId(...).
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setImageUrl("imageUrl");
        final Product product1 = new Product();
        product1.setId(0L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.set_deleted(false);
        product1.setLstImg(Arrays.asList(new Image()));
        final Shop shop3 = new Shop();
        shop3.setId(0L);
        product1.setShop(shop3);
        image1.setProduct(product1);
        final Shop shop4 = new Shop();
        shop4.setId(0L);
        shop4.setName("name");
        shop4.setAddress("address");
        shop4.setBanner("banner");
        shop4.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.set_deleted(false);
        final Account account2 = new Account();
        account2.setId(0L);
        account2.setFullname("fullname");
        account2.setUsername("username");
        account2.setPassword("password");
        account2.setEmail("email");
        account2.setPhone("phone");
        account2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop4.setAccount(account2);
        final Page<Product> products = new PageImpl<>(Arrays.asList(
                new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image1), shop4,
                        new Category(0L, "name", false, Arrays.asList(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(
                        Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                        new HashSet<>(Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))))));
        when(mockProductRepository.findAllByShopId(any(Pageable.class), eq(0L))).thenReturn(products);

        // Run the test
        final ProductResponse result = productServiceImplUnderTest.getAllProduct(customUserDetails, 0, 5);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetAllProduct_ProductRepositoryReturnsNoItems() {
        // Setup
        final CustomUserDetails customUserDetails = new CustomUserDetails(0L, "username", "email", "password",
                Arrays.asList());
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        shop2.setName("name");
        shop2.setAddress("address");
        shop2.setBanner("banner");
        shop2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        final ProductResponse expectedResult = new ProductResponse(Arrays.asList(
                ), 0, 0, 0L, 1,
                true);
        when(mockProductRepository.findAllByShopId(any(Pageable.class), eq(0L)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final ProductResponse result = productServiceImplUnderTest.getAllProduct(customUserDetails, 0, 5);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindById() {
        // Setup
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.set_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image.setProduct(product);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Product expectedResult = new Product(0L, "name", 0, 0.0f,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image), shop1,
                new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                Arrays.asList(new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));

        // Configure ProductRepository.findById(...).
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setImageUrl("imageUrl");
        final Product product2 = new Product();
        product2.setId(0L);
        product2.setName("name");
        product2.setQuantity(0);
        product2.setPrice(0.0f);
        product2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product2.set_deleted(false);
        product2.setLstImg(Arrays.asList(new Image()));
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        product2.setShop(shop2);
        image1.setProduct(product2);
        final Shop shop3 = new Shop();
        shop3.setId(0L);
        shop3.setName("name");
        shop3.setAddress("address");
        shop3.setBanner("banner");
        shop3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.setAccount(account1);
        final Optional<Product> product1 = Optional.of(
                new Product(0L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image1), shop3,
                        new Category(0L, "name", false, Arrays.asList(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(
                        Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                        new HashSet<>(Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)))));
        when(mockProductRepository.findById(0L)).thenReturn(product1);

        // Run the test
        final Product result = productServiceImplUnderTest.findById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindById_ProductRepositoryReturnsAbsent() {
        // Setup
        when(mockProductRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> productServiceImplUnderTest.findById(0L));
    }

    @Test
    public void testDeleteProduct() {
        // Setup
        // Configure ProductRepository.findById(...).
        final Image image = new Image();
        image.setId(0L);
        image.setFileName("fileName");
        image.setFileType("fileType");
        image.setImageUrl("imageUrl");
        final Product product1 = new Product();
        product1.setId(1L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.set_deleted(false);
        product1.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product1.setShop(shop);
        image.setProduct(product1);
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        shop1.setName("name");
        shop1.setAddress("address");
        shop1.setBanner("banner");
        shop1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("username");
        account.setPassword("password");
        account.setEmail("email");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Optional<Product> product = Optional.of(
                new Product(1L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image), shop1,
                        new Category(0L, "name", false, Arrays.asList(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(
                        Arrays.asList(
                                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))),
                        new HashSet<>(Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)))));
        when(mockProductRepository.findById(1L)).thenReturn(product);

        // Configure ProductRepository.save(...).
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setImageUrl("imageUrl");
        final Product product3 = new Product();
        product3.setId(1L);
        product3.setName("name");
        product3.setQuantity(0);
        product3.setPrice(0.0f);
        product3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product3.set_deleted(false);
        product3.setLstImg(Arrays.asList(new Image()));
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        product3.setShop(shop2);
        image1.setProduct(product3);
        final Shop shop3 = new Shop();
        shop3.setId(0L);
        shop3.setName("name");
        shop3.setAddress("address");
        shop3.setBanner("banner");
        shop3.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop3.setAccount(account1);
        final Product product2 = new Product(1L, "name", 0, 0.0f,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(image1), shop3,
                new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                Arrays.asList(new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))));
        when(mockProductRepository.save(
                new Product(1L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(new Image()),
                        new Shop(), new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                        new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                        Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        false)))))).thenReturn(product2);

        // Run the test
        productServiceImplUnderTest.deleteProduct(product1.getId());

        // Verify the results
        verify(mockProductRepository).save(
                new Product(1L, "name", 0, 0.0f, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, Arrays.asList(new Image()),
                        new Shop(), new Category(0L, "name", false, Arrays.asList(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false), new HashSet<>(Arrays.asList(
                        new Color(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false))), new HashSet<>(
                        Arrays.asList(
                                new Size(0, "name", false, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false)))));
    }

    @Test
    public void testDeleteProduct_ProductRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockProductRepository.findById(1L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> productServiceImplUnderTest.deleteProduct(1L));
    }
}
