package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

	private final static String SELECT_ALL_QUERY_TEMPLATE = "SELECT * FROM products";
	private final static String SELECT_QUERY_TEMPLATE = "SELECT * FROM products WHERE id=?";

	private final static String INSERT_QUERY_TEMPLATE = "INSERT INTO products(name, price) VALUES (?, ?)";

	private final static String UPDATE_QUERY_TEMPLATE = "UPDATE products SET name=?, price=? WHERE id=?";

	private final static String DELETE_QUERY_TEMPLATE = "DELETE FROM products WHERE id=?;";
	private Connection dataSource;

	public ProductsRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> findAll() {
		List<Product> result = new ArrayList<>();
		try {
			PreparedStatement pst = dataSource.prepareStatement(SELECT_ALL_QUERY_TEMPLATE);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getLong("id")
						, rs.getString("name"), rs.getLong("price"));
				result.add(product);
			}
			pst.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	@Override
	public Optional<Product> findById(Long id) {
		Product ret = null;
		try {
			PreparedStatement pst = dataSource.prepareStatement(SELECT_QUERY_TEMPLATE);
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				ret = new Product(rs.getLong("id")
						, rs.getString("name"), rs.getLong("price"));
			}
			pst.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return Optional.ofNullable(ret);
	}

	@Override
	public void update(Product product) {
		try {
			PreparedStatement pst = dataSource.prepareStatement(UPDATE_QUERY_TEMPLATE);
			pst.setString(1, product.getName());
			pst.setLong(2, product.getPrice());
			pst.setLong(3, product.getId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void save(Product product) {
		try {
			PreparedStatement pst = dataSource.prepareStatement(INSERT_QUERY_TEMPLATE);
			pst.setString(1, product.getName());
			pst.setLong(2, product.getPrice());
			pst.execute();
			ResultSet rs = dataSource.createStatement().executeQuery("CALL IDENTITY();");
			if (rs.next()) {
				product.setId(rs.getLong(1));
			}
			pst.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void delete(Long id) {
		try {
			PreparedStatement pst = dataSource.prepareStatement(DELETE_QUERY_TEMPLATE);
			pst.setLong(1, id);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
