package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingClient;

public class RPCClient {

	private MessagingClient msgclient;
	private MessageConnection connection;

	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server, port);
	}

	public void connect() {
		// Koble til serveren via meldingsklient
		connection = msgclient.connect();
	}

	public void disconnect() {
		// Frakoble forbindelsen ved å lukke meldingsforbindelsen
		if (connection != null) {
			connection.close();
		}
	}

	/*
     Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

     rpcid is the identifier on the server side of the method to be called
     param is the marshalled parameter of the method to be called
    */
	public byte[] call(byte rpcid, byte[] param) {

		byte[] returnval = null;

		try {
			// Encapsulation: Kombiner RPC-ID og parameter til en melding
			byte[] rpcRequest = RPCUtils.encapsulate(rpcid, param);

			// Send meldingen til serveren
			connection.send(new Message(rpcRequest));

			// Motta svar fra serveren
			Message response = connection.receive();

			// Decapsulation: Hent ut payload fra svaret
			returnval = RPCUtils.decapsulate(response.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnval;
	}
}
