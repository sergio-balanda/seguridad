package ar.edu.unlam.scaw.beans;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.services.AuditoriaService;
import ar.edu.unlam.scaw.services.UsuarioService;

@ManagedBean(name = "usuarioBean", eager = true)
@RequestScoped
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private String email = null;
	private String password = null;
	private String texto = null;
	private String estado = null;
	private Integer rol = null;
	private String error = null;

	// Spring Inject
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
	UsuarioService usuarioService = (UsuarioService) context.getBean("usuarioService");
	AuditoriaService auditoriaService = (AuditoriaService) context.getBean("auditoriaService");

	FacesContext con = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest) con.getExternalContext().getRequest();

	public UsuarioBean() {
		super();
		this.email = null;
		this.password = null;
		this.texto = null;
		this.estado = null;
		this.rol = null;
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

	// lista todos los usuarios
	public List<Usuario> getListaDeUsuarios() {
		List<Usuario> list = usuarioService.getUsuarios();
		return list;
	}

	// guarda usuarios con rol usuario
	public String guardarUsuario() {
		Usuario nuevoUsuario = UsuarioBean();
		if(usuarioService.validaUsuarioEmail(nuevoUsuario)==true && usuarioService.validaUsuarioPassword(nuevoUsuario)==true) {
			error=null;
			usuarioService.guardarUsuario(nuevoUsuario);
			Usuario usuarioDb = usuarioService.buscarUsuarioPorEmailyContraseña(nuevoUsuario.getEmail(),
					nuevoUsuario.getPassword());
			if (usuarioDb != null) {
				String accion = "Usuario "+ usuarioDb.getEmail() + " registrado.";
				auditoriaService.registrarAuditoria(usuarioDb,accion);
			}
			return "index";
		}
		error="Email o Contraseña no validos";
		return "registro";

	}

	// LOGIN
	public String login() {
		Usuario usuarioLog = usuarioService.buscarUsuarioPorEmailyContraseña(this.email, this.password);
		if (usuarioLog == null) {
			error = "Email o Contraseña incorrecto";
			return "index";
		}
		error = null;
		HttpSession httpSession = request.getSession(false);
		httpSession = request.getSession(false);
		httpSession.setAttribute("email", usuarioLog.getEmail());
		httpSession.setAttribute("rol", usuarioLog.getRol());
		httpSession.setAttribute("id", usuarioLog.getId());
		String accion = "Usuario "+ usuarioLog.getEmail() + " se logueo.";
		auditoriaService.registrarAuditoria(usuarioLog,accion);
		return "home";
	}

	public void verificarSesion() throws IOException {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email") == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}

	// si no tiene rol 1 (admmin), redirige
	public void verificarRol() throws IOException {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email") != null) {
			Object rol = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rol");

			if ((Integer) rol != 1) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
			} else {
				// System.out.println("sin permisos");
			}
		}
	}

	// logout, probar como funciona
	public String logout() {
		// originalmente ponia esto de abajo, pero invalida todo hsqldb
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		HttpSession httpSession = request.getSession(false);
		httpSession = request.getSession(false);
		httpSession.setAttribute("email", "");
		httpSession.setAttribute("rol", "");
		httpSession.setAttribute("id", "");
		httpSession.removeAttribute("email");// nuevos, en prueba
		httpSession.removeAttribute("rol");// nuevos, en prueba
		httpSession.removeAttribute("id");// nuevos, en prueba

		this.id = null;
		this.email = null;
		this.password = null;
		this.texto = null;
		this.estado = null;
		this.rol = null;
		this.error = null;

		return "index";
	}

	// modificar
	public String modificarUsuario() {
		Object stringId = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("id");
		Integer idSession = (Integer) stringId;
		String cambioDeTexto = request.getParameter("myForm:texto");
		String cambioDePassword = request.getParameter("myForm:password");
		usuarioService.usuarioModificaPasswordyTexto(cambioDeTexto, cambioDePassword, idSession);
		Usuario usuarioDb = usuarioService.buscarUsuarioPorId(idSession);
		if (usuarioDb != null) {
			String accion = "Usuario "+ usuarioDb.getEmail() + " realizo modificaciones en campo texto y password.";
			auditoriaService.registrarAuditoria(usuarioDb,accion);
		}
		return "home";
	}

	public String verUsuarios() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Integer idUser = (Integer) session.getAttribute("rol");

		if (idUser == 1) {
			return "TodosLosUsuarios";
		}

		return "home";
	}

	// Habilitar Deshabilitar
	public void habilitarDeshabilitar() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String idString = params.get("habilitarDeshabilitar");
		Integer idInteger = Integer.parseInt(idString);

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Integer idRol = (Integer) session.getAttribute("rol");
		if (idRol == 1) {
			Usuario usuario = usuarioService.buscarUsuarioPorId(idInteger);
			Usuario nuevoEstado = usuarioService.cambiarEstado(usuario);
			usuarioService.usuarioModificacion(idInteger, usuario.getEmail(), usuario.getTexto(),
					nuevoEstado.getEstado(), usuario.getPassword(), idRol);
			String accion = "Se desactivo usuario "+ usuario.getEmail();
			auditoriaService.registrarAuditoria(usuario,accion);
		}

	}

	public Usuario verUsuario() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Integer idSession = (Integer) session.getAttribute("id");

		return usuarioService.buscarUsuarioPorId(idSession);
	}

	// get y set
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
