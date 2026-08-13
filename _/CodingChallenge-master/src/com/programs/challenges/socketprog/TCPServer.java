package com.programs.challenges.socketprog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private static Socket connectionSocket;

	public static void main(String[] args) {
		System.out.println("Starting Server....");
		String clientSentence;
		String capitalizedSentence;
		try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
			while (true) {
				connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

}
