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

//classe que implementa o sellerdao
public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	//cria uma dependência da conexão já criada anteriormente num objeto que pode ser usado aqui dentro.
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		
		
	}

	@Override
	public void update(Seller obj) {
		
		
	}

	@Override
	public void deleteById(Integer id) {
		
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st= conn.prepareStatement (	" SELECT seller.*, department.Name as DepName "+
										" FROM seller "+
												" INNER JOIN department ON seller.DepartmentId = department.Id "
										+ " WHERE seller.Id = ? ");
			
			st.setInt(1, id); //o id que entra como parâmetro na função. Explica quem é o id que a query quer, e que é um dado de tipo Integer
			rs = st.executeQuery();
			
			//percorrendo o resultset - testando se traz algum resultado. O resultset inicialmente aponta pra posição 0, que não possui dados. Só tem dado a partir da posição 1.
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;		
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;		
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); //nome da coluna no BD
		dep.setName(rs.getString("DepName"));		
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st= conn.prepareStatement (	"   SELECT seller.*, department.Name as DepName "+
										"   FROM seller "+
												" INNER JOIN department ON seller.DepartmentId = department.Id "
										+ " WHERE DepartmentId = ? "
										+ " ORDER BY Name ");
			
			st.setInt(1, department.getId()); //o id que entra como parâmetro na função. Explica quem é o id que a query quer, e que é um dado de tipo Integer
			rs = st.executeQuery();
			List<Seller> list = new ArrayList();

			//estrutura chave-valor, a chave é um integer (id do departamento) e o valor é um objeto do tipo Department. Está sendo usado Map pra garantir não repetição de departamentos
			Map<Integer,Department> map = new HashMap(); //guarda departamentos nesse caso

			//percorrendo o resultset - testando se traz algum resultado. O resultset inicialmente aponta pra posição 0, que não possui dados. Só tem dado a partir da posição 1.
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep==null) { //só instancia o departamento se for nulo
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //guarda o departamento no map
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);		
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
	}	
}