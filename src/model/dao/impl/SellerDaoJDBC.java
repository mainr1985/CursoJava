package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		PreparedStatement st = null;
		try{
			st = conn.prepareStatement(" INSERT INTO seller "
						   + "(Name,Email,BirthDate,BaseSalary,DepartmentId) "
						   + " VALUES "
						   + " (?,?,?,?,?) ",
						   Statement.RETURN_GENERATED_KEYS); //retorna o id do novo vendedor inserido.

			st.setString(1,obj.getName());
			st.setString(2,obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4,obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();	
			
				while(rs.next()){
					int id = rs.getInt(1); //pega o Id gerado
					obj.setId(id); //popula o objeto com o novo id gerado	
				} 
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException ("Erro na inserção!");
			}
		}
		catch(SQLException e){
			throw new DbException (e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}		
	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		try{
			st = conn.prepareStatement(" UPDATE seller "
						   + " SET "
						   + "	Name = ?, "
						   + "  Email = ?, "
						   + "  BirthDate = ?, "
						   + "  BaseSalary = ?, "
						   + "  DepartmentId = ? "
						   + " WHERE Id = ? "); //retorna o id do novo vendedor inserido.

			st.setString(1,obj.getName());
			st.setString(2,obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4,obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();
			
		}	
		catch(SQLException e){
			throw new DbException (e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}				
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try{
			st = conn.prepareStatement(" DELETE FROM seller WHERE Id = ? " );
						   

			st.setInt(1,id);
			
			st.executeUpdate();
		}
		
		catch(SQLException e){
			throw new DbException (e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}		
		
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st= conn.prepareStatement (	"   SELECT seller.*, department.Name as DepName "+
										"   FROM seller "+
												" INNER JOIN department ON seller.DepartmentId = department.Id "										
										+ " ORDER BY Name ");
			
			rs = st.executeQuery();
			List<Seller> list = new ArrayList();

			Map<Integer,Department> map = new HashMap(); //guarda departamentos nesse caso

			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId")); //reaproveita um departamento se já existir.
				
				if (dep==null) { 
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); 
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