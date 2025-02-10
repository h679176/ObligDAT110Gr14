package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCClient;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}

	public void write(String message) {
		// Marshalling request
		byte[] request = RPCUtils.marshallString(message);

		// Perform RPC call
		byte[] response = rpcclient.call((byte) Common.WRITE_RPCID, request);

		// Unmarshalling response (void)
		RPCUtils.unmarshallVoid(response);
	}
}
