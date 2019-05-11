package ar.edu.unlam.scaw.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.UpdatableSqlQuery;

import ar.edu.unlam.scaw.configs.HsqlDataSource;
import ar.edu.unlam.scaw.daos.UsuarioDao;
import ar.edu.unlam.scaw.entities.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public UsuarioDaoImpl() {
		super();
	}

	@Override
	public List<Usuario> getUsuarios() {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM USUARIO";
		List<Usuario> lista = jdbcTemplate.query(sql, params, new UserMapper());

//		 for(Usuario e :lista) { System.out.println( "" +e.getEmail() +""+
//		 e.getPassword()+""+e.getRol()+""+e.getEstado()); }

		return lista;
	}

	public void guardarUsuario(Usuario usuario) {
		String sql = "INSERT INTO USUARIO (EMAIL,PASSWORD,TEXTO,ESTADO,ROL) "
				+ "VALUES  (:email,:password,:texto,:estado,:rol)";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", usuario.getEmail());
		params.put("password", usuario.getPassword());
		params.put("texto", usuario.getTexto());
		usuario.setEstado("habilitado");
		usuario.setRol(2);// TODO:: POR DEFECTO SE CREA COMO USUARIO
		params.put("rol", usuario.getRol());
		params.put("estado", usuario.getEstado());

		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<Usuario> buscarUsuarioPorEmailyContraseña(String email, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM USUARIO WHERE EMAIL = '" + email + "' AND PASSWORD ='" + password + "' LIMIT 1";
		// getConnection();
		// prepare Statement
		List<Usuario> lista = jdbcTemplate.query(sql, params, new UserMapper());
		return lista;
	}

	@Override
	public void usuarioModificacion(Integer id, String email, String texto, String estado, String password,
			Integer rol) {
		String idString = id.toString();
		String query = "UPDATE USUARIO SET TEXTO ='" + texto + "', PASSWORD = '" + password + "', ESTADO = '" + estado
				+ "'  WHERE ID =" + idString;
		Map<String, Object> params = new HashMap<String, Object>();
		jdbcTemplate.update(query, params);
	}

	// geteresult
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final class UserMapper implements RowMapper<Usuario> {

		// @Override
		public Usuario mapRow(ResultSet result, int rowNum) throws SQLException {
			Usuario usuario = new Usuario();
			usuario.setId(result.getInt("id"));
			usuario.setEmail(result.getString("email"));
			usuario.setTexto(result.getString("texto"));
			usuario.setEstado(result.getString("estado"));
			usuario.setPassword(result.getString("password"));
			usuario.setRol(result.getInt("rol"));			
			return usuario;
		}

	}

}
