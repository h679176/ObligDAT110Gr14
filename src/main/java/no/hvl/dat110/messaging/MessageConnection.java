package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageConnection {

	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	public MessageConnection(Socket socket) {
		this.socket = socket;

		try {
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Sender en melding
	public void send(Message message) {
		try {
			byte[] data = message.getData();
			outputStream.writeInt(data.length);
			outputStream.write(data);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Mottar en melding
	public Message receive() {
		try {
			int length = inputStream.readInt();
			byte[] data = new byte[length];
			inputStream.readFully(data);
			return new Message(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Lukker tilkoblingen
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
