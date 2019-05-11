package com.kf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.kf.controller.ServerThread;
import com.kf.model.Chat;

/**
 * 
 * @author Samuel
 *
 * @version 1.1.1 (2019/05/11)
 *
 */
public class MainApp {

	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("\t\tHH:mm:ss");

	private static SessionFactory sessionFactory;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		int port = 54321;

		ServerSocket server = new ServerSocket(port);
		System.out
				.println(DATETIME_FORMAT.format(new Timestamp(System.currentTimeMillis())) + " --> Servidor iniciado");

		openHibernateSession();
		System.out.println(
				TIME_FORMAT.format(new Timestamp(System.currentTimeMillis())) + " --> Sesion Hibernate abierta");

		LinkedList<Socket> clientList = new LinkedList<Socket>();
		System.out.println(
				TIME_FORMAT.format(new Timestamp(System.currentTimeMillis())) + " --> Lista de clientes creada");

		Chat chat = new Chat(clientList);
		System.out.println(TIME_FORMAT.format(new Timestamp(System.currentTimeMillis())) + " --> Chat abierto");

		while (true) {
			Socket client = new Socket();
			client = server.accept();

			chat.addClient(client);
			System.out.println(TIME_FORMAT.format(new Timestamp(System.currentTimeMillis())) + " --> Cliente "
					+ client.getInetAddress() + " unido al chat");

			ServerThread thread = new ServerThread(client, chat);
			thread.start();
		}
	}

	public static void openHibernateSession() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static void closeHibernateSession() {
		sessionFactory.close();
	}
}
