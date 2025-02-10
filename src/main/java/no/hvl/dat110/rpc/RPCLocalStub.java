package no.hvl.dat110.rpc;

public abstract class RPCLocalStub {

	protected RPCClient rpcclient;

	public RPCLocalStub(RPCClient rpcclient) {
		this.rpcclient = rpcclient;
	}

	public void connect() {
		rpcclient.connect();
	}

	public void disconnect() {
		rpcclient.disconnect();
	}

	// Gir tilgang til call-metoden for subklasser
	protected byte[] call(byte rpcid, byte[] param) {
		return rpcclient.call(rpcid, param);
	}
}
