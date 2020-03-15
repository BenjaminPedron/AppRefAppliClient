package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Le client se connecte à un serveur dont le protocole est 
 * menu-choix-question-réponse client-réponse service
 * la réponse est saisie au clavier en String
 **/
class Application {
	
		private final static int PORT_SERVICE = 3000;
		private final static String HOST = "localhost";
	
	public static void main(String[] args) {
		while(true) {
			Socket s = null;		
			try {
				/* On créé la socket allant questionner le serveur */
				s = new Socket(HOST, PORT_SERVICE);

				BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
				PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
				BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
				
				System.out.println("\n\nConnecté au serveur " + s.getInetAddress() + ":"+ s.getPort());
				
				String line;

				line = sin.readLine();
				
				System.out.println(line.replaceAll("##", "\n"));
				sout.println(clavier.readLine());

				while(true) {
					try {
						line = sin.readLine();
						if(line.equals("succes") || line.equals("fail")) {
							if(!line.equals("succes"))
								System.out.println(line.replaceAll("##", "\n"));
							break;
						}
						/* Si ca commence par &&, ça veut dire que le serveur n'attend pas de réponse tout de suite */
						if(line.charAt(0) == '&' && line.charAt(1) == '&') {
							System.out.println(line.replaceAll("##", "\n").replaceAll("&&", ""));
						}
						else {
							System.out.println(line.replaceAll("##", "\n"));
							sout.println(clavier.readLine());
						}
					}catch (Exception e) {
					System.out.println("Problème : Vérifiez vos entrées, assurez-vous que votre serveur ftp est ouvert pour certains services !");
					break;
				}
				}}
				
			
			catch (IOException e) { 
				System.err.println("Fin de la connexion"); 
			}
			try { 
				if (s != null) s.close(); 
			} catch (IOException e2) {}		
			}
	}
}
