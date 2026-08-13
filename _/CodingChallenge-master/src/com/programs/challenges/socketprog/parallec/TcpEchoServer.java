package com.programs.challenges.socketprog.parallec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpEchoServer {

	protected static final Logger logger = LoggerFactory.getLogger(TcpEchoServer.class);

	private ServerSocket serverSocket;
	private int port = 10081;
	Map<Integer, String> data;
	private boolean idle;

	public TcpEchoServer(boolean idle) {
		try {

			this.idle = idle;
			this.serverSocket = new ServerSocket(port);
		} catch (Exception ex) {
			System.err.println("Could not listen on port: " + port);
		}
	}

	public static void main(String[] args) {
		try {
			TcpEchoServer server = new TcpEchoServer(false);
			server.serve();

		} catch (Exception e) {
			System.err.println("Couldn't start server:\n" + e);
		}
	}

	public void stop() {
		try {

			serverSocket.close();
		} catch (Exception e) {
			logger.error("error in stop server socket ", e);
		}
	}

	public void serve() throws IOException {
		Socket clientSocket;
		
		try {
			while (true) {

				System.out.println("TCP Echo Server Started on port " + port + " . \nWaiting for connection.....");
				clientSocket = serverSocket.accept();
				logger.info("Client Connection successful");
				logger.info("Waiting for input.....");

				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
				//outStream.writeObject(data);
				String inputLine;
				// just read 1 single line then auto close
				if ((inputLine = in.readLine()) != null) {
					logger.info("Server: " + inputLine);

					String msg = "L: " + inputLine + " AT_TCP_SERVER  ";
					out.println(msg);
					logger.info(msg);

				}
				// whether to close after a single request.
				// the interrupt is critical otherwise cannot easily shutdown
				if (!this.idle) {
					out.close();
					clientSocket.close();
					in.close();
				} else {
					Thread.sleep(1000L);
				}

			}

		} catch (IOException | InterruptedException e) {
			logger.error("Exception in echo server. " + "\nExpected when shutdown. {}", e.getLocalizedMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();

		}

	}
	
	private void createMap(){
		data = new HashMap<>();
		data.put(1, "Rahul");
		data.put(2, "Sumit");
		data.put(3, "Pranshu");
	}

	public ServerSocket getServer() {
		return serverSocket;
	}
}
