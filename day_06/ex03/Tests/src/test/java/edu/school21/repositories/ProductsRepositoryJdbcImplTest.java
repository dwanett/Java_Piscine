package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {

	private ProductsRepositoryJdbcImpl productsRepositoryJdbc;
	private Connection connection;

	final List<Product> EXPECTED_FIND_ALL_PRODUCTS;
	{
		EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>();
		EXPECTED_FIND_ALL_PRODUCTS.add(new Product(0L, "product_one", 100));
		EXPECTED_FIND_ALL_PRODUCTS.add(new Product(1L, "product_two", 250));
		EXPECTED_FIND_ALL_PRODUCTS.add(new Product(2L, "product_three", 500));
		EXPECTED_FIND_ALL_PRODUCTS.add(new Product(3L, "product_four", 125));
		EXPECTED_FIND_ALL_PRODUCTS.add(new Product(4L, "product_five", 300));
	}
	final Product EXPECTED_FIND_BY_ID_PRODUCT = EXPECTED_FIND_ALL_PRODUCTS.get(3);
	final Product EXPECTED_UPDATED_PRODUCT = EXPECTED_FIND_ALL_PRODUCTS.get(1);
	final Product EXPECTED_SAVE_PRODUCT = new Product(-1, "product_six", 104);

	@BeforeEach
	public void init() throws SQLException {
		EmbeddedDatabaseBuilder dataSource = new EmbeddedDatabaseBuilder();
		DataSource ds = dataSource
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
		connection = ds.getConnection();
		productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
	}

	@AfterEach
	public void shutdownConnection() throws SQLException {
		connection.close();
	}

	@Test
	public void testFindAll(){
		Assertions.assertIterableEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepositoryJdbc.findAll());
	}

	@Test
	public void testValidFindById(){
		Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT
				, productsRepositoryJdbc.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId()).orElse(null));
	}

	@ParameterizedTest
	@ValueSource(longs = { -1, -3,})
	public void testInvalidFindById(Long id){
		Assertions.assertFalse(productsRepositoryJdbc.findById(id).isPresent());
	}

	@Test
	public void testUpdate(){
		productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
		Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT
				, productsRepositoryJdbc.findById(EXPECTED_UPDATED_PRODUCT.getId()).orElse(null));
	}

	@Test
	public void testSave(){
		productsRepositoryJdbc.save(EXPECTED_SAVE_PRODUCT);
		Assertions.assertTrue(productsRepositoryJdbc.findById(EXPECTED_SAVE_PRODUCT.getId()).isPresent());
	}

	@ParameterizedTest
	@ValueSource(longs = { 1, 2, 3, 4 })
	public void testDelete(Long id){
		Assertions.assertTrue(productsRepositoryJdbc.findById(id).isPresent());
		productsRepositoryJdbc.delete(id);
		Assertions.assertFalse(productsRepositoryJdbc.findById(id).isPresent());
	}
}
