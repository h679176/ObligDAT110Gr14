package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RPCUtils {

	public static byte[] encapsulate(byte rpcid, byte[] payload) {

		// Allokerer plass til 1 byte for rpcid + payload lengde
		byte[] rpcmsg = new byte[1 + payload.length];

		// Setter rpcid som første byte
		rpcmsg[0] = rpcid;

		// Kopierer payload inn fra indeks 1
		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}

	public static byte[] decapsulate(byte[] rpcmsg) {

		// Ekstraher payload fra byte-arrayet fra indeks 1
		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
	}

	public static byte[] marshallString(String str) {

		// Konverterer String til byte-array
		return str.getBytes();
	}

	public static String unmarshallString(byte[] data) {

		// Konverterer byte-array tilbake til String
		return new String(data);
	}

	public static byte[] marshallVoid() {

		// Returnerer en tom byte-array for void
		return new byte[0];
	}

	public static void unmarshallVoid(byte[] data) {

		// Ingen operasjon nødvendig for unmarshalling av void
	}

	public static byte[] marshallBoolean(boolean b) {

		// Konverterer boolean til 1 byte
		byte[] encoded = new byte[1];
		encoded[0] = (byte) (b ? 1 : 0);
		return encoded;
	}

	public static boolean unmarshallBoolean(byte[] data) {

		// Konverterer byte tilbake til boolean
		return (data[0] > 0);
	}

	public static byte[] marshallInteger(int x) {

		// Bruk ByteBuffer til å konvertere int til 4-byte array
		return ByteBuffer.allocate(4).putInt(x).array();
	}

	public static int unmarshallInteger(byte[] data) {

		// Bruk ByteBuffer til å konvertere byte-array tilbake til int
		return ByteBuffer.wrap(data).getInt();
	}
}
