package ar.edu.unlam.scaw.services;

import java.util.List;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlam.scaw.daos.UsuarioDao;
import ar.edu.unlam.scaw.daos.UsuarioDaoImpl;
import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.services.UsuarioService;
import junit.framework.Assert;

public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDao usuarioDao;

	// UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();

	@Override
	public List<Usuario> getUsuarios() {

		return usuarioDao.getUsuarios();
	}
	
	@Override
	public Usuario buscarUsuarioPorId(Integer id) {
		// TODO Auto-generated method stub
		List<Usuario> usuarios = usuarioDao.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getId().equals(id)) {
				System.out.println("password " + usuario.getPassword());
				return usuario;
			}
		}
		return null;
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		usuarioDao.guardarUsuario(usuario);

	}

	@Override
	public void usuarioModificacion(Integer id, String email, String texto, String estado, String password,
			Integer rol) {
		usuarioDao.usuarioModificacion(id, email, texto, estado, password, rol);

	}

	public Usuario cambiarEstado(Usuario usuario) {
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setEstado("habilitado");
		if (usuario.getEstado().equals(nuevoUsuario.getEstado())) {
			nuevoUsuario.setEstado("deshabilitado");
		}else {
			nuevoUsuario.setEstado("habilitado");
		}
		
		return nuevoUsuario;
	}

	@Override
	public Usuario buscarUsuarioPorEmailyContraseña(String email, String password) {
		try {
			List<Usuario> listaUsuarios = usuarioDao.buscarUsuarioPorEmailyContraseña(email, password);
			Usuario usuario = listaUsuarios.get(0);
			// if(usuario.getEstado()=="habilitado") {
			return listaUsuarios.get(0);
			// }else {
			// return null;
			// }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

}