package com.example.vmo1.service.impl;

import com.example.vmo1.model.entity.Image;
import com.example.vmo1.model.entity.Product;
import com.example.vmo1.model.entity.Shop;
import com.example.vmo1.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @Mock
    private ImageRepository mockImageRepository;

    @InjectMocks
    private ImageServiceImpl imageServiceImplUnderTest;

    @Test
    public void testSaveImage() {
        // Setup
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setFileDownloadUri("fileDownloadUri");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setIs_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image1.setProduct(product);
        final List<Image> image = Arrays.asList(image1);
        final Image image2 = new Image();
        image2.setId(0L);
        image2.setFileName("fileName");
        image2.setFileType("fileType");
        image2.setFileDownloadUri("fileDownloadUri");
        final Product product1 = new Product();
        product1.setId(0L);
        product1.setName("name");
        product1.setQuantity(0);
        product1.setPrice(0.0f);
        product1.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product1.setIs_deleted(false);
        product1.setLstImg(Arrays.asList(new Image()));
        final Shop shop1 = new Shop();
        shop1.setId(0L);
        product1.setShop(shop1);
        image2.setProduct(product1);
        final List<Image> expectedResult = Arrays.asList(image2);

        // Configure ImageRepository.saveAll(...).
        final Image image3 = new Image();
        image3.setId(0L);
        image3.setFileName("fileName");
        image3.setFileType("fileType");
        image3.setFileDownloadUri("fileDownloadUri");
        final Product product2 = new Product();
        product2.setId(0L);
        product2.setName("name");
        product2.setQuantity(0);
        product2.setPrice(0.0f);
        product2.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product2.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product2.setIs_deleted(false);
        product2.setLstImg(Arrays.asList(new Image()));
        final Shop shop2 = new Shop();
        shop2.setId(0L);
        product2.setShop(shop2);
        image3.setProduct(product2);
        final List<Image> images = Arrays.asList(image3);
        when(mockImageRepository.saveAll(Arrays.asList(image3))).thenReturn(images);

        // Run the test
        final List<Image> result = imageServiceImplUnderTest.saveImage(image);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSaveImage_ImageRepositoryReturnsNoItems() {
        // Setup
        final Image image1 = new Image();
        image1.setId(0L);
        image1.setFileName("fileName");
        image1.setFileType("fileType");
        image1.setFileDownloadUri("fileDownloadUri");
        final Product product = new Product();
        product.setId(0L);
        product.setName("name");
        product.setQuantity(0);
        product.setPrice(0.0f);
        product.setCreated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setUpdated_at(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        product.setIs_deleted(false);
        product.setLstImg(Arrays.asList(new Image()));
        final Shop shop = new Shop();
        shop.setId(0L);
        product.setShop(shop);
        image1.setProduct(product);
        final List<Image> image = Arrays.asList(image1);
        when(mockImageRepository.saveAll(Arrays.asList(new Image()))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Image> result = imageServiceImplUnderTest.saveImage(image);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetResource() throws Exception {
        // Setup
        // Run the test
        final InputStream result = imageServiceImplUnderTest.getResource("path", "filename");

        // Verify the results
    }

    @Test
    public void testGetResource_ThrowsFileNotFoundException() {
        // Setup
        // Run the test
        assertThrows(FileNotFoundException.class, () -> imageServiceImplUnderTest.getResource("path", "filename"));
    }
}
