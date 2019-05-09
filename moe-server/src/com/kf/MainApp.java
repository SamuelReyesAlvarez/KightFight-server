package com.kf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import com.kf.controller.ServerThread;
import com.kf.model.Chat;

public class MainApp {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws IOException {

		int port = 54321;

		ServerSocket server = new ServerSocket(port);

		System.out.println(sdf.format(new Timestamp(System.currentTimeMillis())) + " --> Servidor iniciado");

		LinkedList<Socket> clientList = new LinkedList<Socket>();

		Chat chat = new Chat(clientList);

		while (true) {
			Socket client = new Socket();

			client = server.accept();

			chat.addClient(client);

			ServerThread thread = new ServerThread(client, chat);

			thread.start();
		}
	}
}
