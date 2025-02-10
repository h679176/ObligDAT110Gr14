package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller {

	private static int N = 5; // Antall iterasjoner i loopen

	public static void main(String[] args) {

		DisplayStub display;
		SensorStub sensor;

		RPCClient displayclient, sensorclient;

		System.out.println("Controller starting ...");

		// Opprett RPC-klienter for display og sensor
		displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

		// Setup stop methods for RPC
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

		// Koble til RPC-servere
		displayclient.connect();
		sensorclient.connect();

		// Opprett stub-objekter for sensor og display
		display = new DisplayStub(displayclient);
		sensor = new SensorStub(sensorclient);

		// Les verdi fra sensor og skriv til display N ganger
		for (int i = 0; i < N; i++) {
			int temp = sensor.read(); // RPC-kall til sensoren
			display.write("Temperature: " + temp); // RPC-kall til displayet

			// Simuler litt ventetid (valgfritt)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Stopper RPC-serverne
		stopdisplay.stop();
		stopsensor.stop();

		displayclient.disconnect();
		sensorclient.disconnect();

		System.out.println("Controller stopping ...");
	}
}
