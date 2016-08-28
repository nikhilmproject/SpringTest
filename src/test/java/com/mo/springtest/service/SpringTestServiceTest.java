/**
 * 
 */
package com.mo.springtest.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mo.springtest.Application;
import com.mo.springtest.domain.Category;
import com.mo.springtest.domain.Product;
import com.mo.springtest.domain.ProductRepository;
import com.mo.springtest.util.ItemizedBill;

import static org.mockito.Mockito.when;

/**
 * @author Admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class SpringTestServiceTest {

	@InjectMocks
	SpringTestService service;
	
	@Mock
    private ProductRepository productRepository;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void testServiceCheckoutSuccess() throws Exception {
		
		Product product1 = new Product("XYZ", 100, new Category("CatXYZ", "10%"));
		Product product2 = new Product("ABC", 100, new Category("CatABC", "10%"));
		
		List<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);
		
		String expectedReport = new ItemizedBill().generateBill(products);
		when(productRepository.findOne(new Long(1))).thenReturn(product1);
		when(productRepository.findOne(new Long(2))).thenReturn(product2);
		
		String actualReport = service.checkout("1,2");
		Assert.assertEquals(expectedReport, actualReport);
		
	}
	
	@Test
	public void testBillNotGeneratedForUnavailableProducts() throws Exception {
		
		String expectedReport = "";
		when(productRepository.findOne(new Long(1))).thenReturn(null);
		when(productRepository.findOne(new Long(2))).thenReturn(null);
		String actualReport = service.checkout("1,2");
		Assert.assertEquals(expectedReport, actualReport);
		
	}
	
	@Test
	public void testBillForMixOfAvailableAndUnAvailableProducts() throws Exception {
		
		Product product1 = new Product("XYZ", 100, new Category("CatXYZ", "10%"));
		Product product2 = new Product("ABC", 100, new Category("CatABC", "10%"));
		
		List<Product> products = new ArrayList<Product>();
		products.add(product1);
//		products.add(product2); Product 2 is not available
		
		String expectedReport = new ItemizedBill().generateBill(products);
		when(productRepository.findOne(new Long(1))).thenReturn(product1);
		when(productRepository.findOne(new Long(2))).thenReturn(null);
		
		String actualReport = service.checkout("1,2");
		Assert.assertEquals(expectedReport, actualReport);
		
	}

	

}
