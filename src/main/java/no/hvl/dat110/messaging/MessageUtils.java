package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";


	public static byte[] encapsulate(Message message) {

		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data = message.getData();

		// TODO - START
		segment[0] = (byte) data.length;

		System.arraycopy(data, 0, segment, 1, data.length);
		// TODO - END


		return segment;
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;

		// TODO - START

		int length = segment[0] & 0xFF;

		// Kopier data fra segmentet
		byte[] data = Arrays.copyOfRange(segment, 1, 1 + length);

		// Opprett Message-objekt med hentet data
		message = new Message(data);
		// TODO - END


		return message;
	}
}
