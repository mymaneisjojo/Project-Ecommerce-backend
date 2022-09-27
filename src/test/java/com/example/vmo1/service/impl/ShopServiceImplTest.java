package com.example.vmo1.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.vmo1.commons.exceptions.ResourceNotFoundException;
import com.example.vmo1.model.entity.Account;
import com.example.vmo1.model.entity.Category;
import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.entity.Shop;
import com.example.vmo1.model.request.ShopDto;
import com.example.vmo1.model.response.*;
import com.example.vmo1.repository.ProductRepository;
import com.example.vmo1.repository.ShopRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceImplTest {

    @Mock
    private ShopRepository mockShopRepository;
    @Mock
    private Cloudinary mockCloudinary;
    @Mock
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ShopServiceImpl shopServiceImplUnderTest;

    @Test
    public void testAdd() throws Exception {
        // Setup
        final ProductDtoToResponse productDtoToResponse = new ProductDtoToResponse();
        productDtoToResponse.setId(0L);
        productDtoToResponse.setName("name");
        productDtoToResponse.setPrice(0.0f);
        productDtoToResponse.setQuantity(0);
        productDtoToResponse.set_deleted(false);
        final Category category = new Category();
        category.setId(0L);
        category.setName("name");
        category.setStatus(false);
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category.setLstPro(Arrays.asList(product));
        productDtoToResponse.setCategory(category);
        final ShopDto metaData = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse));
        final MultipartFile file = null;
        final ShopDtoToResponse expectedResult = new ShopDtoToResponse("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false);

        // Configure ShopRepository.findByAccountId(...).
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
        account.setUsername("accountName");
        account.setPassword("password");
        account.setEmail("accountEmail");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Optional<Shop> shop = Optional.of(shop1);
        when(mockShopRepository.findByAccountId(0L)).thenReturn(shop);

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Configure ShopRepository.save(...).
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
        account1.setUsername("accountName");
        account1.setPassword("password");
        account1.setEmail("accountEmail");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        when(mockShopRepository.save(new Shop())).thenReturn(shop2);

        // Run the test
        final ShopDtoToResponse result = shopServiceImplUnderTest.add(metaData, file);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockShopRepository).save(new Shop());
    }

    @Test
    public void testAdd_ShopRepositoryFindByAccountIdReturnsAbsent() throws Exception {
        // Setup
        final ProductDtoToResponse productDtoToResponse = new ProductDtoToResponse();
        productDtoToResponse.setId(0L);
        productDtoToResponse.setName("name");
        productDtoToResponse.setPrice(0.0f);
        productDtoToResponse.setQuantity(0);
        productDtoToResponse.set_deleted(false);
        final Category category = new Category();
        category.setId(0L);
        category.setName("name");
        category.setStatus(false);
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category.setLstPro(Arrays.asList(product));
        productDtoToResponse.setCategory(category);
        final ShopDto metaData = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse));
        final MultipartFile file = null;
        final ShopDtoToResponse expectedResult = new ShopDtoToResponse("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false);
        when(mockShopRepository.findByAccountId(0L)).thenReturn(Optional.empty());

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Configure ShopRepository.save(...).
        final Shop shop = new Shop();
        shop.setId(0L);
        shop.setName("name");
        shop.setAddress("address");
        shop.setBanner("banner");
        shop.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("accountName");
        account.setPassword("password");
        account.setEmail("accountEmail");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.setAccount(account);
        when(mockShopRepository.save(new Shop())).thenReturn(shop);

        // Run the test
        final ShopDtoToResponse result = shopServiceImplUnderTest.add(metaData, file);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(mockShopRepository).save(new Shop());
    }

    @Test
    public void testAdd_ThrowsIOException() {
        // Setup
        final ProductDtoToResponse productDtoToResponse = new ProductDtoToResponse();
        productDtoToResponse.setId(0L);
        productDtoToResponse.setName("name");
        productDtoToResponse.setPrice(0.0f);
        productDtoToResponse.setQuantity(0);
        productDtoToResponse.set_deleted(false);
        final Category category = new Category();
        category.setId(0L);
        category.setName("name");
        category.setStatus(false);
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category.setLstPro(Arrays.asList(product));
        productDtoToResponse.setCategory(category);
        final ShopDto metaData = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse));
        final MultipartFile file = null;

        // Configure ShopRepository.findByAccountId(...).
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
        account.setUsername("accountName");
        account.setPassword("password");
        account.setEmail("accountEmail");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Optional<Shop> shop = Optional.of(shop1);
        when(mockShopRepository.findByAccountId(0L)).thenReturn(shop);

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Configure ShopRepository.save(...).
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
        account1.setUsername("accountName");
        account1.setPassword("password");
        account1.setEmail("accountEmail");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        when(mockShopRepository.save(new Shop())).thenReturn(shop2);

        // Run the test
        assertThrows(IOException.class, () -> shopServiceImplUnderTest.add(metaData, file));
        verify(mockShopRepository).save(new Shop());
    }

    @Test
    public void testUpload() throws Exception {
        // Setup
        final MultipartFile multipartFile = null;

        // Configure Cloudinary.uploader(...).
        final Uploader uploader = new Uploader(new Cloudinary(new HashMap<>()), null);
        when(mockCloudinary.uploader()).thenReturn(uploader);

        // Run the test
        final Map result = shopServiceImplUnderTest.upload(multipartFile);

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
        assertThrows(IOException.class, () -> shopServiceImplUnderTest.upload(multipartFile));
    }

    @Test
    public void testUpdate() {
        // Setup
        final ProductDtoToResponse productDtoToResponse = new ProductDtoToResponse();
        productDtoToResponse.setId(0L);
        productDtoToResponse.setName("name");
        productDtoToResponse.setPrice(0.0f);
        productDtoToResponse.setQuantity(0);
        productDtoToResponse.set_deleted(false);
        final Category category = new Category();
        category.setId(0L);
        category.setName("name");
        category.setStatus(false);
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category.setLstPro(Arrays.asList(product));
        productDtoToResponse.setCategory(category);
        final ShopDto metaData = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse));
        final ProductDtoToResponse productDtoToResponse1 = new ProductDtoToResponse();
        productDtoToResponse1.setId(0L);
        productDtoToResponse1.setName("name");
        productDtoToResponse1.setPrice(0.0f);
        productDtoToResponse1.setQuantity(0);
        productDtoToResponse1.set_deleted(false);
        final Category category1 = new Category();
        category1.setId(0L);
        category1.setName("name");
        category1.setStatus(false);
        final Product product1 = new Product();
        product1.setId(0L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category1.setLstPro(Arrays.asList(product1));
        productDtoToResponse1.setCategory(category1);
        final ShopDto expectedResult = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse1));

        // Configure ShopRepository.findById(...).
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
        account.setUsername("accountName");
        account.setPassword("password");
        account.setEmail("accountEmail");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop1.setAccount(account);
        final Optional<Shop> shop = Optional.of(shop1);
        when(mockShopRepository.findById(0L)).thenReturn(shop);

        // Configure ShopRepository.save(...).
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
        account1.setUsername("accountName");
        account1.setPassword("password");
        account1.setEmail("accountEmail");
        account1.setPhone("phone");
        account1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop2.setAccount(account1);
        when(mockShopRepository.save(new Shop())).thenReturn(shop2);

        // Run the test
        final ShopDto result = shopServiceImplUnderTest.update(metaData, 0L, null);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testUpdate_ShopRepositoryFindByIdReturnsAbsent() {
        // Setup
        final ProductDtoToResponse productDtoToResponse = new ProductDtoToResponse();
        productDtoToResponse.setId(0L);
        productDtoToResponse.setName("name");
        productDtoToResponse.setPrice(0.0f);
        productDtoToResponse.setQuantity(0);
        productDtoToResponse.set_deleted(false);
        final Category category = new Category();
        category.setId(0L);
        category.setName("name");
        category.setStatus(false);
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        category.setLstPro(Arrays.asList(product));
        productDtoToResponse.setCategory(category);
        final ShopDto metaData = new ShopDto("name", "banner", "address",
                new AccountDtoToResponse(0L, "username", "fullname", "email", "phone", false, false,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()),
                Arrays.asList(productDtoToResponse));
        when(mockShopRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> shopServiceImplUnderTest.update(metaData, 0L, null));
    }

    @Test
    public void testGetAllShop() {
        // Setup
        final ShopResponse expectedResult = new ShopResponse();
        final ShopDto shopDto = new ShopDto();
        shopDto.setName("name");
        shopDto.setBanner("banner");
        shopDto.setAddress("address");
        final AccountDtoToResponse account = new AccountDtoToResponse();
        account.setId(0L);
        account.setUsername("username");
        account.setFullname("fullname");
        account.setEmail("email");
        account.setPhone("phone");
        shopDto.setAccount(account);
        expectedResult.setContent(Arrays.asList(shopDto));
        expectedResult.setPageNo(0);
        expectedResult.setPageSize(1);
        expectedResult.setTotalElements(1L);
        expectedResult.setTotalPages(1);
        expectedResult.setLast(true);

        // Configure ShopRepository.findAll(...).
        final Shop shop = new Shop();
        shop.setId(0L);
        shop.setName("name");
        shop.setAddress("address");
        shop.setBanner("banner");
        shop.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.set_deleted(false);
        final Account account1 = new Account();
        account1.setId(0L);
        account1.setFullname("fullname");
        account1.setUsername("username");
        account1.setPassword("password");
        account1.setEmail("email");
        account1.setPhone("phone");
        account1.setCreated_at(null);
        shop.setAccount(account1);
        final Page<Shop> shops = new PageImpl<>(Arrays.asList(shop));
        when(mockShopRepository.findAll(any(Pageable.class))).thenReturn(shops);

        // Run the test
        final ShopResponse result = shopServiceImplUnderTest.getAllShop(0, 5);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetAllShop_ShopRepositoryReturnsNoItems() {
        // Setup
        final ShopResponse expectedResult = new ShopResponse();
        final ShopDto shopDto = new ShopDto();
        shopDto.setName("name");
        shopDto.setBanner("banner");
        shopDto.setAddress("address");
        final AccountDtoToResponse account = new AccountDtoToResponse();
        account.setId(0L);
        account.setUsername("username");
        account.setFullname("fullname");
        account.setEmail("email");
        account.setPhone("phone");
        shopDto.setAccount(account);
        expectedResult.setContent(Arrays.asList());
        expectedResult.setPageNo(0);
        expectedResult.setPageSize(0);
        expectedResult.setTotalElements(0L);
        expectedResult.setTotalPages(1);
        expectedResult.setLast(true);

        when(mockShopRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final ShopResponse result = shopServiceImplUnderTest.getAllShop(0, 5);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testStatistProduct() {
        // Setup
        final StatisticResponse expectedResult = new StatisticResponse(0,
                Arrays.asList(new CountProduct("name", "accountName", "accountEmail", 0)));
        when(mockShopRepository.countShops()).thenReturn(0);

        // Configure ShopRepository.findAll(...).
        final Shop shop = new Shop();
        shop.setId(0L);
        shop.setName("name");
        shop.setAddress("address");
        shop.setBanner("banner");
        shop.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.set_deleted(false);
        final Account account = new Account();
        account.setId(0L);
        account.setFullname("fullname");
        account.setUsername("accountName");
        account.setPassword("password");
        account.setEmail("accountEmail");
        account.setPhone("phone");
        account.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        shop.setAccount(account);
        final List<Shop> shopList = Arrays.asList(shop);
        when(mockShopRepository.findAll()).thenReturn(shopList);

        when(mockProductRepository.countProductsByShopId(0L)).thenReturn(0);

        // Run the test
        final StatisticResponse result = shopServiceImplUnderTest.statistProduct();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testStatistProduct_ShopRepositoryFindAllReturnsNoItems() {
        // Setup
        final StatisticResponse expectedResult = new StatisticResponse(0,
                Arrays.asList());
        when(mockShopRepository.countShops()).thenReturn(0);
        when(mockShopRepository.findAll()).thenReturn(Collections.emptyList());
        when(mockProductRepository.countProductsByShopId(0L)).thenReturn(0);

        // Run the test
        final StatisticResponse result = shopServiceImplUnderTest.statistProduct();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
