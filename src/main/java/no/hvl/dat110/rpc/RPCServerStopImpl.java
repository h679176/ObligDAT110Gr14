package no.hvl.dat110.rpc;

public class RPCServerStopImpl extends RPCRemoteImpl {

	private RPCServer rpcserver; // Feltvariabel for RPCServer

	public RPCServerStopImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid, rpcserver);
		this.rpcserver = rpcserver; // Initialiser feltvariabelen
	}

	// RPC server-side implementation of the built-in stop RPC method
	public byte[] invoke(byte[] param) {

		RPCUtils.unmarshallVoid(param);

		byte[] returnval = RPCUtils.marshallVoid();

		stop();

		return returnval;
	}

	public void stop() {
		System.out.println("RPC server executing stop");

		// Stopper serveren ved å kalle stop-metoden i RPCServer
		rpcserver.stop();
	}
}
