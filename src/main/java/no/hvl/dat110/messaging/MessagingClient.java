package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.Socket;

public class MessagingClient {

	// name/IP address of the messaging server
	private String server;

	// server port on which the messaging server is listening
	private int port;

	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	// setup of a messaging connection to a messaging server
	public MessageConnection connect() {

		Socket clientSocket;
		MessageConnection connection = null;

		try {
			// Forsøk å koble til serveren
			clientSocket = new Socket(server, port);
			connection = new MessageConnection(clientSocket);
			System.out.println("Connected to server " + server + " on port " + port);

		} catch (IOException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}

		return connection;
	}
}
