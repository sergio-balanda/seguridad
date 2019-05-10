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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getActualizado() {
		return actualizado;
	}

	public void setActualizado(Date actualizado) {
		this.actualizado = actualizado;
	}
	
	
}
