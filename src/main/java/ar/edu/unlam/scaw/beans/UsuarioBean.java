package ar.edu.unlam.scaw.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.jsf.FacesContextUtils;

import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.services.UsuarioService;
import ar.edu.unlam.scaw.services.UsuarioServiceImpl;

@ManagedBean(name = "usuarioBean", eager = true)
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private String email = null;
	private String password = null;
	private String texto = null;
	private String estado = null;
	private Integer rol = null;

	// Spring Inject
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
	UsuarioService usuarioService = (UsuarioService) context.getBean("usuarioService");

	public UsuarioBean() {
		super();
		this.email = null;
		this.password = null;
		this.texto = null;
		this.estado = null;
		this.rol = null;
	}

	// lista todos los usuarios
	public List<Usuario> getListaDeUsuarios() {

		List<Usuario> list = usuarioService.getUsuarios();
		return list;
	}

	public Usuario UsuarioBean() {
		// getter setter
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
		usuario.setPassword(this.password);
		usuario.setTexto(this.texto);
		usuario.setEstado(this.estado);
		usuario.setRol(this.rol);
		return usuario;
	}

	// guarda usuarios con rol usuario
	public String guardarUsuario() {
		Usuario nuevoUsuario = UsuarioBean();
		usuarioService.guardarUsuario(nuevoUsuario);
		System.out.println("/****************************************************/");
		System.out.println(nuevoUsuario);
		System.out.println("/****************************************************/");
		return "TodosLosUsuarios";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

}
