package com.kf.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.kf.model.Chat;
import com.kf.model.Login;

/**
 * 
 * @author Samuel
 *
 */
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

		updateClientChat();

		while (true) {
			try {
				String[] message = (dis.readUTF()).split("~");

				switch (message[0]) {
				case "message":
					if (message[1].trim().length() > 0) {
						chat.addMessage(message[1].trim());
						updateClientChat();
					}
					break;
				case "login":
					Login login = new Login(message[1], message[2], 0);

					break;

				default:
					break;
				}
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

	private void updateClientChat() {

		for (int i = 0; i < chat.getClientList().size(); i++) {
			if (chat.getClient(i).isConnected()) {
				try {
					DataOutputStream dos = new DataOutputStream(chat.getClient(i).getOutputStream());
					Gson gson = new Gson();
					String jsonOut = gson.toJson(chat);
					dos.writeUTF(jsonOut);
				} catch (IOException e) {
					System.out.println("Error al crear flujo de salida");
				}
			}
		}
	}
}
