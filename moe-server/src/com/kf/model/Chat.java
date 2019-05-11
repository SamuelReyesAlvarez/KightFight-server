package com.kf.model;

import java.net.Socket;
import java.util.LinkedList;

/**
 * 
 * @author Samuel
 *
 */
public class Chat {

	private static final int MAX_MESSAGES_HISTORY = 50;

	private LinkedList<Socket> clientList;
	private LinkedList<String> messageList;

	public Chat(LinkedList<Socket> clientList) {
		this.clientList = clientList;
		this.messageList = new LinkedList<String>();
	}

	public Chat() {
		super();
	}

	public LinkedList<Socket> getClientList() {
		return clientList;
	}

	public Socket getClient(int position) {
		return clientList.get(position);
	}

	public synchronized void addClient(Socket client) {
		this.clientList.add(client);
	}

	public synchronized void removeClient(Socket client) {
		this.clientList.remove(client);
	}

	public String getMessages() {
		StringBuilder messages = new StringBuilder();

		for (String message : messageList) {
			messages.append(message);
		}

		return messages.toString().trim();
	}

	public synchronized void addMessage(String message) {
		this.messageList.add(message);

		while (this.messageList.size() > MAX_MESSAGES_HISTORY) {
			this.messageList.remove();
		}
	}

}
