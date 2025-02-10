package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

public class DisplayImpl extends RPCRemoteImpl {

	public DisplayImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid, rpcserver);
	}

	@Override
	public void write(String message) {
		System.out.println("DISPLAY: " + message);
	}

	@Override
	public byte[] invoke(byte[] param) {
		// Unmarshall parametere (her en String)
		String message = RPCUtils.unmarshallString(param);

		// Kall på den lokale `write`-metoden
		write(message);

		// Marshall tom respons
		return RPCUtils.marshallVoid();
	}
}
