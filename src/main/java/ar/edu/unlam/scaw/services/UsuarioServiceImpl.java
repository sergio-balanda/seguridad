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
	public void guardarUsuario(Usuario usuario) {
		usuarioDao.guardarUsuario(usuario);

	}

	@Override
	public Usuario buscarUsuarioPorEmailyContraseña(String email, String password) {
		try {
			List<Usuario> listaUsuarios = usuarioDao.buscarUsuarioPorEmailyContraseña(email, password);
			Usuario usuario = listaUsuarios.get(0);
			//if(usuario.getEstado()=="habilitado") {
				return listaUsuarios.get(0);
			//}else {
				//return null;
			//}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

}