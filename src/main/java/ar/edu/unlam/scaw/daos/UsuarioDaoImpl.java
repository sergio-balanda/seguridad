package ar.edu.unlam.scaw.daos;

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
		List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());

		/*
		 * for(Usuario e :lista) { System.out.println( "" +e.getNombre() +""+
		 * e.getContrasena()+""+e.getTipo()+""+e.getEstaAprobado()); }
		 */

		return lista;
	}

	public void guardarUsuario(Usuario usuario) {
		String sql = "INSERT INTO USUARIO (EMAIL,PASSWORD,TEXTO,ESTADO,ROL) "
				+ "VALUES  (:email,:password,:texto,:estado,:rol)";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", usuario.getEmail());
		params.put("password", usuario.getPassword());
		params.put("texto", usuario.getTexto());
		usuario.setEstado("Habilitado");
		usuario.setRol(2);// TODO:: POR DEFECTO SE CREA COMO USUARIO
		params.put("rol", usuario.getRol());
		params.put("estado", usuario.getEstado());

		jdbcTemplate.update(sql, params);
	}

	// geters
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final class UsuarioMapper implements RowMapper<Usuario> {

		// @Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			Usuario usuario = new Usuario();
			usuario.setId(rs.getInt("id"));
			usuario.setEmail(rs.getString("email"));
			usuario.setTexto(rs.getString("texto"));
			usuario.setEstado(rs.getString("estado"));
			usuario.setRol(rs.getInt("rol"));
			return usuario;
		}

	}

}