package com.moe.controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.moe.modelo.Chat;

public class HiloServidor extends Thread {

	DataInputStream entrada;
	Socket cliente;
	Chat chat;

	public HiloServidor(Socket cliente, Chat chat) {
		this.cliente = cliente;
		this.chat = chat;

		try {
			entrada = new DataInputStream(cliente.getInputStream());
		} catch (IOException e) {
			System.out.println("Error al crear flujo de entrada");
		}
	}

	public void run() {
		System.out.println("Clientes conectados: " + chat.getListaClientes().size());

		actualizarChatClientes(chat.getMensajes());

		while (true) {
			try {
				String mensaje = entrada.readUTF();

				if ("*".equals(mensaje.trim())) {
					System.out.println("Clientes conectados: " + chat.getListaClientes().size());
					break;
				}

				chat.addMensaje(mensaje);

				actualizarChatClientes(chat.getMensajes());
			} catch (IOException e) {
				System.out.println("Error al recibir mensaje de cliente");
				break;
			}
		}

		try {
			cliente.close();
		} catch (IOException e) {
			System.out.println("Error al cerrar la conexion con el cliente: " + cliente.getInetAddress());
		}
	}

	private void actualizarChatClientes(String mensajes) {

		for (int i = 0; i < chat.getListaClientes().size(); i++) {
			if (chat.getCliente(i).isConnected()) {
				try {
					DataOutputStream salida = new DataOutputStream(chat.getCliente(i).getOutputStream());
					salida.writeUTF(mensajes);
				} catch (IOException e) {
					System.out.println("Error al crear flujo de salida");
				}
			}
		}
	}
}
