package com.kf.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.kf.model.Chat;

public class ServerThread extends Thread {

	DataInputStream dis;
	Socket client;
	Chat chat;

	public ServerThread(Socket client, Chat chat) {
		this.client = client;
		this.chat = chat;

		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			System.out.println("Error al crear flujo de entrada");
		}
	}

	public void run() {
		System.out.println("Clientes conectados: " + chat.getClientList().size());

		updateClientChat(chat.getMessages());

		while (true) {
			try {
				String message = dis.readUTF();

				if ("*".equals(message.trim())) {
					System.out.println("Clientes conectados: " + chat.getClientList().size());
					break;
				}

				chat.addMessage(message);

				updateClientChat(chat.getMessages());
			} catch (IOException e) {
				System.out.println("Error al recibir mensaje de cliente");
				break;
			}
		}

		try {
			client.close();
		} catch (IOException e) {
			System.out.println("Error al cerrar la conexion con el cliente: " + client.getInetAddress());
		}
	}

	private void updateClientChat(String messages) {

		for (int i = 0; i < chat.getClientList().size(); i++) {
			if (chat.getClient(i).isConnected()) {
				try {
					DataOutputStream dos = new DataOutputStream(chat.getClient(i).getOutputStream());
					dos.writeUTF(messages);
				} catch (IOException e) {
					System.out.println("Error al crear flujo de salida");
				}
			}
		}
	}
}
