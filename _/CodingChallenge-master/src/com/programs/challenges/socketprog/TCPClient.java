package com.programs.challenges.socketprog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) {
		String sentence;
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("TCP Client Running...");
		try (Socket clientSocket = new Socket("localhost", 6789)) {
			
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));		
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
				while ((modifiedSentence = inFromServer.readLine()) != null) {
					System.out.println("FROM SERVER: " + modifiedSentence);
					System.out.println(clientSocket);
					sentence = inFromUser.readLine();
					outToServer.writeBytes(sentence + '\n');
					
 
                }
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
