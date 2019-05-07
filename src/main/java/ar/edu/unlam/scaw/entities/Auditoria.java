package ar.edu.unlam.scaw.entities;

import java.io.Serializable;
import java.util.Date;

public class Auditoria{

	private Integer id;
	private String accion;
	private Date creado;
	private Date actualizado;
	//TODO:: Relacion usuario

	public Auditoria() {

	}
}
