package ar.edu.unlam.scaw.daos;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ar.edu.unlam.scaw.entities.Auditoria;
import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.entities.Auditoria;

public class AuditoriaDaoImpl implements AuditoriaDao {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public AuditoriaDaoImpl() {
		super();
	}

	@Override
	public List<Auditoria> todasLasAuditorias() {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM AUDITORIA";
		List<Auditoria> auditorias = jdbcTemplate.query(sql, params, new AuditoriaMap());
//		 for(Auditoria a :auditorias) { 
//			 System.out.println( "la auditoria es  " +a.getAccion() +" "+a.getActualizado() ); 
//			 }
		return auditorias;
	}

	@Override
	public List<Auditoria> auditoriasDeUnUsuario(Integer id) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String sql = "SELECT * FROM AUDITORIA WHERE USUARIO = '"+id+"'";
			List<Auditoria> auditorias = jdbcTemplate.query(sql, params, new AuditoriaMap());
			return auditorias;
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage()+" "+e.getCause());
			return null;
		}

	}
	

	@Override
	public void registrarAuditoria(Auditoria auditoria) {
		try {
			System.out.println("usario del dao "+ auditoria.getAccion()+" "+auditoria.getIdUsuario()+" "+auditoria.getCreado());

	 		String sql= "INSERT INTO AUDITORIA(accion,creado,actualizado,usuario) VALUES  "
	 				+ "(:accion,:creado,:actualizado,:usuario)";
	 		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("accion",auditoria.getAccion().toString());
			params.put("creado", auditoria.getCreado());
			params.put("actualizado", auditoria.getActualizado());
			params.put("usuario", auditoria.getIdUsuario());
			
			System.out.println("sql = "+sql + "values " + auditoria.getAccion()+" "+auditoria.getActualizado() + " id " +auditoria.getId() + " usuer "+ auditoria.getIdUsuario());
			jdbcTemplate.update(sql, params);
		} catch (Exception e) {
			System.out.println("error "+e.getMessage());
			e.printStackTrace();
		}
		
	}

	// geteresult
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final class AuditoriaMap implements RowMapper<Auditoria> {
		// @Override
		public Auditoria mapRow(ResultSet result, int rowNum) throws SQLException {
			Auditoria auditoria = new Auditoria();
			auditoria.setId(result.getInt("id"));
			auditoria.setAccion(result.getString("accion"));
			auditoria.setCreado(result.getString("creado"));
			auditoria.setActualizado(result.getString("actualizado"));
			auditoria.setIdUsuario(result.getInt("usuario"));
			return auditoria;
		}

	}


}
