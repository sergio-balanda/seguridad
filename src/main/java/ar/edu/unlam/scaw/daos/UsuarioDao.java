package ar.edu.unlam.scaw.daos;

import java.util.List;

import ar.edu.unlam.scaw.entities.Usuario;

public interface UsuarioDao {

	public List<Usuario> getUsuarios();

	public void guardarUsuario(Usuario usuario);

}
