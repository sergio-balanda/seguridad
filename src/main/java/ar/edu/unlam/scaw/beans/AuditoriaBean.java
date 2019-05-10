package ar.edu.unlam.scaw.beans;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.edu.unlam.scaw.entities.Auditoria;
import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.services.AuditoriaService;
import ar.edu.unlam.scaw.services.UsuarioService;

@ManagedBean(name = "auditoriaBean", eager = true)
@RequestScoped
@SessionScoped
public class AuditoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private String accion = null;
	private Date creado = null;
	private Date actualizado = null;

	public AuditoriaBean() {
		super();
		this.accion = null;
		this.creado = null;
		this.actualizado = null;
	}

	public Auditoria AuditoriaBean() {
		// getter setter
		Auditoria auditoria = new Auditoria();
		auditoria.setAccion(this.accion);
		auditoria.setCreado(this.creado);
		auditoria.setActualizado(this.actualizado);
		return auditoria;
	}

	// Spring Inject
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
	AuditoriaService auditoriaService = (AuditoriaService) context.getBean("auditoriaService");
	
	private FacesContext contextSession = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) contextSession.getExternalContext().getSession(true);
	
	
	public String historial() {
		String idRol = session.getAttribute("rol").toString();
		Integer intRol = Integer.parseInt(idRol);
		String url = auditoriaService.devolverPaginaDeAcuerdoAlRolDelUsuario(intRol); 
		return url;
	}

}
