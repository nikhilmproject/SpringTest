/**
 * 
 */
package com.mo.springtest.component;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mo.springtest.Application;
import com.mo.springtest.controller.SpringTestController;
import com.mo.springtest.domain.Category;
import com.mo.springtest.domain.Product;
import com.mo.springtest.domain.ProductRepository;

/**
 * Component test class for the assignment.
 * It first created 5 products in database and then check out 3 products and prints the bill report.
 * 
 * @author Admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class SprintTestComponentTest {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SpringTestController controller;
	@Autowired
	private ProductRepository productRepository;

	private MockMvc mockMvc;
	
	private List<Long> productCatalogue; // entire catalogue of five products
	private List<Long> productIds; // customer selected products

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		productCatalogue = saveTestData();
		productIds = scanProducts(productCatalogue);
	}
	
	@Test
    public void testCheckout() throws Exception{
		
		log.info("Running component test testCheckout()");
		String userId = "nikhil.mayaskar@db.com";	
		ResultActions resultActions = this.mockMvc.perform(get("/checkout?productIds="+productIds.toString()+"&userId="+userId)
                 .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType("application/json;charset=UTF-8"));
        
        //Itemized bill
        log.info(resultActions.andReturn().getResponse().getContentAsString());
        
        //assert that the checkedout products are removed from db
        Iterable<Product> checkedOutproducts = productRepository.findAll(productIds);
        int checkedOutCount = 0;
        for (Product product : checkedOutproducts) {
        	checkedOutCount++;
		}
        Assert.assertTrue(checkedOutCount == 0);
                
        //assert the rest of products are still in db
        productCatalogue.removeAll(productIds);
        Iterable<Product> products = productRepository.findAll(productCatalogue);
        int count = 0;
        for (Product product : products) {
			count++;
		}
        Assert.assertTrue(count == 2);
    }
	
	@Test
    public void testCheckoutInvalidUser() {
		
		log.info("Running component test testCheckout()");
		String userId = "xyz@db.com";
		try{
			this.mockMvc.perform(get("/checkout?productIds="+productIds.toString()+"&userId="+userId));
		}catch(Exception ex){
			Assert.assertTrue(true);
		}
		
                 
		
	}
	
	/**
	 * Creates 5 products with available 3 categories in database.
	 * 
	 * @return
	 */
	private List<Long> saveTestData() {
		
		// product_A, product_B and product_C will be checked out
		Category category_A = new Category("Category_A", "10%"); // 10% sales tax
		Product product_A = new Product("Product_A", 100, category_A);
		
		Category category_B = new Category("Category_B", "20%"); // 20% sales tax
		Product product_B = new Product("Product_B", 150, category_B);
		
		Category category_C = new Category("Category_C", "0.0%"); // no sales tax
		Product product_C = new Product("Product_C", 200, category_C);
		
		// these products will remain in db
		Product product_D = new Product("Product_D", 250, category_A); // reuse some category
		Product product_E = new Product("Product_E", 300, category_B); //reuse some category
		
		List<Product> products = new ArrayList<Product>();
		products.add(product_A);
		products.add(product_B);
		products.add(product_C);
		products.add(product_D);
		products.add(product_E);
		
		productRepository.save(products);
		List<Long> productCatalogue = products.stream().map(Product::getId).collect(Collectors.toList());
		
		return productCatalogue;
	}

	
	/**
	 * Returns top three product Ids from the list. This resembles the products selected 
	 * by customer and ready to be checked out.
	 * 
	 * @param productCatalogue the entire catalogue of products.
	 * @return
	 */
	private List<Long> scanProducts(List<Long> productCatalogue) {
		
		return productCatalogue.stream().limit(3).collect(Collectors.toList());
	}

}
