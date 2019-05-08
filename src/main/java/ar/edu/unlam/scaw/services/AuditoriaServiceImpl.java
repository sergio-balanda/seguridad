package ar.edu.unlam.scaw.services;

public class AuditoriaServiceImpl implements AuditoriaService {

	@Override
	public String devolverPaginaDeAcuerdoAlRolDelUsuario(Integer intRol) {
		switch (intRol) {
		case 1:
			return "historial";
		case 2:
			return "historialUsuario";
		default:
			return "index";
		}
	}

}
