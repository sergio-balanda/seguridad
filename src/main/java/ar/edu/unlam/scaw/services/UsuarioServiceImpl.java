package ar.edu.unlam.scaw.services;

import java.util.List;
import java.util.Properties;

import javax.enterprise.inject.New;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlam.scaw.daos.UsuarioDao;
import ar.edu.unlam.scaw.daos.UsuarioDaoImpl;
import ar.edu.unlam.scaw.entities.Usuario;
import ar.edu.unlam.scaw.services.UsuarioService;
import junit.framework.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		} else {
			nuevoUsuario.setEstado("habilitado");
		}
		return nuevoUsuario;
	}

	@Override
	public Usuario buscarUsuarioPorEmailyContraseña(String email, String password) {
		try {
			List<Usuario> listaUsuarios = usuarioDao.buscarUsuarioPorEmailyContraseña(email, password);
			Usuario usuario = listaUsuarios.get(0);
			Usuario usuarioEquals = new Usuario();
			usuarioEquals.setEstado("habilitado");
			if (usuarioEquals.getEstado().equals(usuario.getEstado())) {
				return listaUsuarios.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@Override
	public void usuarioModificaPasswordyTexto(String texto, String password, Integer id) {
		// TODO Auto-generated method stub
		Usuario usuario = buscarUsuarioPorId(id);
		if (password != "") {
			usuarioModificacion(id, usuario.getEmail(), texto, usuario.getEstado(), password, usuario.getRol());
		} else {
			usuarioModificacion(id, usuario.getEmail(), texto, usuario.getEstado(), usuario.getPassword(),
					usuario.getRol());
		}

	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		// TODO Auto-generated method stub
		List<Usuario> usuarios = usuarioDao.getUsuarios();
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equals(email)) {
				return usuario;
			}
		}
		return null;
	}
	
	public String enviarEmail(String email) {
		try {
			Usuario usuario = buscarUsuarioPorEmail(email);
			if(usuario==null) {
				return "Usuario no encontrado";
			}
			else {
				// El correo gmail de envío
				String correoEnvia = "*************";//su cuenta, habilitada para reciber correos de apps
				String claveCorreo = "*************";//su password
				// La configuración para enviar correo
				Properties properties = new Properties();
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.port", "587");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.user", correoEnvia);
				properties.put("mail.password", claveCorreo);

				// Obtener la sesion
				Session session = Session.getInstance(properties, null);

				try {
					// Crear el cuerpo del mensaje
					MimeMessage mimeMessage = new MimeMessage(session);

					// Agregar quien envía el correo
					mimeMessage.setFrom(new InternetAddress(correoEnvia, "SCAW"));

					// Los destinatarios
					InternetAddress[] internetAddresses = { new InternetAddress(email) };

					// Agregar los destinatarios al mensaje
					mimeMessage.setRecipients(Message.RecipientType.TO, internetAddresses);

					// Agregar el asunto al correo
					mimeMessage.setSubject("Scaw a Enviando Correo.");

					// Creo la parte del mensaje
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					mimeBodyPart.setText("Pedido de recuperacion de contraseña. Su contraseña es: "+usuario.getPassword());

					// Crear el multipart para agregar la parte del mensaje anterior
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(mimeBodyPart);

					// Agregar el multipart al cuerpo del mensaje
					mimeMessage.setContent(multipart);

					// Enviar el mensaje
					Transport transport = session.getTransport("smtp");
					transport.connect(correoEnvia, claveCorreo);
					transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
					transport.close();
					return "Se ha enviado un correo a su cuenta.";

				} catch (Exception ex) {
					//System.out.println("/////////"+ex.getMessage());
					ex.printStackTrace();
					return "Error!!!";
				}
			}
		
		} catch (Exception e) {
			return "Error!!!";
		}		
		
	}
	
	
	public String send() {
		return "Error!!!";
	}
	
	@Override
	public boolean validaUsuarioEmail(Usuario usuario) {
		Pattern EMAIL_PATTERN = Pattern.compile("[A-Za-z]+@[a-z]+\\.[a-z]+");
		String email = (String) usuario.getEmail();
		Matcher mather = EMAIL_PATTERN.matcher(email);
		if(!mather.find()){
			return false;
		}
		return true;
	}

	@Override
	public boolean validaUsuarioPassword(Usuario usuario) {
		if(usuario.getPassword().length()<2) {
			return false;
		}else {
			return true;
		}
	}

}