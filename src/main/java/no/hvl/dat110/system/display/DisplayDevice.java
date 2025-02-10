package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;

public class DisplayDevice {

	public static void main(String[] args) {

		System.out.println("Display server starting ...");

		// Initialiser og start Display RPC-server
		RPCServer server = new RPCServer(Common.DISPLAYPORT); // Bruk korrekt konstant

		// Registrer Display-implementasjonen med riktig RPC-ID
		DisplayImpl displayImpl = new DisplayImpl((byte) Common.WRITE_RPCID, server);

		// Koble implementasjonen til serveren
		server.register((byte) Common.WRITE_RPCID, displayImpl);

		// Start serveren
		server.run();

		System.out.println("Display server stopping ...");
	}
}
