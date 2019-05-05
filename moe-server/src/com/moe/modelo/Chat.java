package com.moe.modelo;

import java.net.Socket;
import java.util.LinkedList;

public class Chat {

	private static final int MAX_HISTORIAL_MENSAJES = 50;

	private LinkedList<Socket> listaClientes;
	private LinkedList<String> listaMensajes;

	public Chat(LinkedList<Socket> listaClientes) {
		this.listaClientes = listaClientes;
		this.listaMensajes = new LinkedList<String>();
	}

	public Chat() {
		super();
	}

	public LinkedList<Socket> getListaClientes() {
		return listaClientes;
	}

	public Socket getCliente(int posicion) {
		return listaClientes.get(posicion);
	}

	public synchronized void addCliente(Socket cliente) {
		this.listaClientes.add(cliente);
	}

	public synchronized void removeCliente(Socket cliente) {
		this.listaClientes.remove(cliente);
	}

	public String getMensajes() {
		StringBuilder mensajes = new StringBuilder();

		for (String mensaje : listaMensajes) {
			mensajes.append(mensaje);
		}

		return mensajes.toString().trim();
	}

	public synchronized void addMensaje(String mensaje) {
		this.listaMensajes.add(mensaje);

		while (this.listaMensajes.size() > MAX_HISTORIAL_MENSAJES) {
			this.listaMensajes.remove();
		}
	}

}
