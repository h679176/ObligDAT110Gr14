package no.hvl.dat110.rpc;

import java.util.HashMap;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;

	// HashMap for å registrere RPC-metoder
	private HashMap<Byte, RPCRemoteImpl> services;

	public RPCServer(int port) {
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte, RPCRemoteImpl>();
	}

	public void run() {

		// Stop-metoden er innebygd i serveren
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
		services.put(RPCCommon.RPIDSTOP, rpcstop);

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		connection = msgserver.accept();

		System.out.println("RPC SERVER ACCEPTED");

		boolean stop = false;

		while (!stop) {

			byte rpcid = 0;
			Message requestmsg, replymsg;

			// Motta en melding fra klienten
			requestmsg = connection.receive();

			// Hent RPC-ID fra forespørselen og decapsuler parameterne
			byte[] requestData = requestmsg.getData();
			rpcid = requestData[0];  // RPC-ID er første byte

			byte[] param = RPCUtils.decapsulate(requestData);

			// Se etter korrekt RPC-metode basert på rpcid
			RPCRemoteImpl rpcImpl = services.get(rpcid);

			if (rpcImpl != null) {
				// Kall metoden og få resultatet
				byte[] result = rpcImpl.invoke(param);

				// Kapsle inn resultatet og sende tilbake til klienten
				byte[] replyData = RPCUtils.encapsulate(rpcid, result);
				replymsg = new Message(replyData);
				connection.send(replymsg);
			} else {
				System.out.println("Ukjent RPC-metode-ID: " + rpcid);
			}

			// Stop server hvis det var stop-metoden som ble kalt
			if (rpcid == RPCCommon.RPIDSTOP) {
				stop = true;
			}
		}
	}

	// Registrer RPC-metoder på serveren
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}

	public void stop() {
		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}

		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
	}
}
