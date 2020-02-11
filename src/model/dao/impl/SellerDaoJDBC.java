package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer Id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ? ");

			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Department dp = instantiateDepartment(rs);

				Seller s = instantiateSeller(rs, dp);

				return s;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		return null;

	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setDepartment(dp);
		
		return s;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {

		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "  
					+ "FROM seller INNER JOIN department "  
					+ "ON seller.DepartmentId = department.Id "  
					+ "WHERE DepartmentId = ? "  
					+ "ORDER BY Name");

			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map  = new HashMap<>();
			
			while (rs.next()) {

				Department dp = map.get(rs.getInt("DepartmentId"));
				
				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				
			
				Seller s = instantiateSeller(rs, dp);
				list.add(s);

			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		
		
		
		
		
	}

}
