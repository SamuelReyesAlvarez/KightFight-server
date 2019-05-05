package com.moe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import com.moe.controlador.HiloServidor;
import com.moe.modelo.Chat;

public class Inicio {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws IOException {

		int puerto = 54321;

		ServerSocket servidor = new ServerSocket(puerto);

		System.out.println(sdf.format(new Timestamp(System.currentTimeMillis())) + " --> Servidor iniciado");

		LinkedList<Socket> listaClientes = new LinkedList<Socket>();

		Chat chat = new Chat(listaClientes);

		while (true) {
			Socket cliente = new Socket();

			cliente = servidor.accept();

			chat.addCliente(cliente);

			HiloServidor hilo = new HiloServidor(cliente, chat);

			hilo.start();
		}
	}
}
