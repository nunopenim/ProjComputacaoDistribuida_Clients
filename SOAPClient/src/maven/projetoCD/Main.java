package maven.projetoCD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {
	
	static String loggeduser;
	
	public static long currentTimeSeconds() {
		return Instant.now().getEpochSecond();
	}
	
	public static long minutesToSeconds(long mins) {
		return mins * 60;
	}
	
	public static void adminMenu() {
		System.out.println();
		System.out.println("##### SOAP Client #####");
		System.out.println("MENU ADMINISTRADOR");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Inicio da Sessão");
		System.out.println("3  - Duração da Sessão");
		System.out.println("4  - Tempo Restante de votação");
		System.out.println("5  - Número total de votos");
		System.out.println("6  - Listar resultados da votação (%)");
		System.out.println("7  - Item ganhador");
		System.out.println("8  - Listar utilizadores registados");
		System.out.println("9  - Listar utilizadores da sessão");
		System.out.println("10 - associar utilizador");
		System.out.println("11 - remover utilizador");
		System.out.println("99 - Sair");
		System.out.println();
		
	}
	
	public static void userMenu() {
		System.out.println();
		System.out.println("##### SOAP Client #####");
		System.out.println("MENU VOTANTE");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Tempo Restante de votação");
		System.out.println("3  - Votar");
		System.out.println("4  - Número total de votos");
		System.out.println("5  - Listar resultados da votação (%)");
		System.out.println("6  - Item ganhador");
		System.out.println("99 - Sair");
		System.out.println();
	}
	
    public static void main(String[] args) {
    	boolean invalidLogin = true;
    	boolean isAdmin = false;
    	boolean running = true;
        while(invalidLogin) {
        	Scanner sc = new Scanner(System.in);
        	System.out.print("login: ");
        	String uid = sc.nextLine();
        	System.out.print("password: ");
        	String pwd = sc.nextLine();
        	boolean[] data = SOAPUtils.authenticateFunction(uid, pwd);
        	if(data == null) {
        		System.out.println("Impossível contactar o servidor! Tente mais tarde\n");
        		sc.close();
        		return;
        	}
        	if(data[0]) {
        		isAdmin = data[1];
        		invalidLogin = false;
        		loggeduser = uid;
        		break;
        	}
        	System.out.println("Login Invalido!\n");
        }
        boolean menushown = false;
        while(running) {
        	Scanner sc = new Scanner(System.in);
        	if (isAdmin) {
        		if (!menushown) {
            		adminMenu();
            		menushown = true;
            	}
        		System.out.print("Opção? ");
        		String selector = sc.nextLine();
        		if ("0".equals(selector)) {
        			menushown = false;
        		}
        		else if ("1".equals(selector)) {
        			System.out.println(SOAPUtils.listAllVotingStuff());
        		}
        		else if ("2".equals(selector)) {
        			long minutes = 0;
        			long seconds = 0;
        			boolean valid = false;
        			System.out.println("Daqui a quantos minutos deve começar?");
        			String minuteString = sc.nextLine();
        			try {
        				minutes = Long.parseLong(minuteString);
        				valid = true;
        			}
        			catch (Exception e){
        				System.out.println("Minutos inválidos!");
        			}
        			if (valid) {
        				valid = false;
        				System.out.println("E daqui a quantos segundos?");
        				String secondString = sc.nextLine();
        				try {
        					seconds = Long.parseLong(secondString);
        					valid = true;
        				}
        				catch (Exception e) {
        					System.out.println("Segundos inválidos!");
        				}
        				if (valid) {
        					long totalTime = currentTimeSeconds() + seconds + minutesToSeconds(minutes);
        					boolean done = SOAPUtils.setStart(totalTime);
        					if (done) {
        						System.out.println("A votação irá começar daqui a " + minutes + " minutos e " + seconds + " seconds");
        					}
        					else {
        						System.out.println("Falha ao definir o inicio da votação!");
        					}
        				}
        			}
        		}
        		else if ("3".equals(selector)) {
        			long minutes = 0;
        			long seconds = 0;
        			boolean valid = false;
        			System.out.println("Qual a duração em minutos?");
        			String minuteString = sc.nextLine();
        			try {
        				minutes = Long.parseLong(minuteString);
        				valid = true;
        			}
        			catch (Exception e){
        				System.out.println("Minutos inválidos!");
        			}
        			if (valid) {
        				valid = false;
        				System.out.println("E daqui a quantos segundos?");
        				String secondString = sc.nextLine();
        				try {
        					seconds = Long.parseLong(secondString);
        					valid = true;
        				}
        				catch (Exception e) {
        					System.out.println("Segundos inválidos!");
        				}
        				if (valid) {
        					long totalLength = seconds + minutesToSeconds(minutes);
        					boolean done = SOAPUtils.setLength(totalLength);
        					if (done) {
        						System.out.println("A votação terá a duração de " + minutes + " minutos e " + seconds + " seconds");
        					}
        					else {
        						System.out.println("Falha ao definir a duração da votação!");
        					}
        				}
        			}
        		}
        		else if ("4".equals(selector)) {
        			if (!SOAPUtils.jaComecou()) {
        				long secondsUntilStart = SOAPUtils.startingTime() - currentTimeSeconds();
        				long minutesUntilStart = 0;
        				if (secondsUntilStart >= 60) {
        					minutesUntilStart = secondsUntilStart/60;
        					secondsUntilStart -= minutesUntilStart * 60;
        				}
        				System.out.println("A sessão ainda não começou! Tempo restante até começar: " + minutesUntilStart + " m " + secondsUntilStart + " s.");
        			}
        			else if (SOAPUtils.jaComecou() && !SOAPUtils.jaAcabou()) {
        				long durationSeconds = SOAPUtils.startingTime() + SOAPUtils.duration() - currentTimeSeconds();
        				long durationMinutes = 0;
        				if (durationSeconds >= 60) {
        					durationMinutes = durationSeconds/60;
        					durationSeconds -= durationMinutes * 60;
        				}
        				System.out.println("A sessão já começou! Tempo até fechar as urnas: " + durationMinutes + " m " + durationSeconds + " s.");
        			}
        			else {
        				System.out.println("A sessão já terminou!");
        			}
        		}
        		else if ("5".equals(selector)) {
        			System.out.println("Numero total de votos: " + SOAPUtils.totalVotos());
        		}
        		else if ("6".equals(selector)) {
        			System.out.println("Resultados da votação:");
        			System.out.println(SOAPUtils.listResultados());
        		}
        		else if ("7".equals(selector)) {
        			System.out.println("Item ganhador: " + SOAPUtils.itemGanhador());
        		}
        		else if ("8".equals(selector)) {
        			System.out.println("Utilizadores Registados: \n" + SOAPUtils.listAllUsers());
        		}
        		else if ("9".equals(selector)) {
        			System.out.println("Utilizadores da Sessão: \n" + SOAPUtils.listAllSessionUsers());
        		}
        		else if ("10".equals(selector)) {
        			System.out.print("Introduza o ID LDAP do Utilizador> ");
        			String ldapID = sc.nextLine();
        			boolean successAdding = SOAPUtils.add_user(ldapID);
        			if (successAdding) {
        				System.out.println("Utilizador adicionado com sucesso");
        			}
        			else {
        				System.out.println("Falha ao adicionar o utilizador");
        			}
        		}
        		else if ("11".equals(selector)) {
        			System.out.print("Introduza o ID LDAP do Utilizador> ");
        			String ldapID = sc.nextLine();
        			boolean successAdding = SOAPUtils.rm_user(ldapID);
        			if (successAdding) {
        				System.out.println("Utilizador removido com sucesso");
        			}
        			else {
        				System.out.println("Falha ao remover o utilizador");
        			}
        		}
        		else if ("99".equals(selector)) {
        			System.out.println("A sair...");
        			running = false;
        		}
        		else {
        			System.out.println("Opcção inválida!");
        		}
        	}
            else {
            	if (!menushown) {
            		userMenu();
            		menushown = true;
            	}
            	System.out.print("Opção? ");
        		String selector = sc.nextLine();
        		if ("0".equals(selector)) {
        			menushown = false;
        		}
        		else if ("1".equals(selector)) {
        			System.out.println(SOAPUtils.listAllVotingStuff());
        		}
        		else if ("2".equals(selector)) {
        			if (!SOAPUtils.jaComecou()) {
        				long secondsUntilStart = SOAPUtils.startingTime() - currentTimeSeconds();
        				long minutesUntilStart = 0;
        				if (secondsUntilStart >= 60) {
        					minutesUntilStart = secondsUntilStart/60;
        					secondsUntilStart -= minutesUntilStart * 60;
        				}
        				System.out.println("A sessão ainda não começou! Tempo restante até começar: " + minutesUntilStart + " m " + secondsUntilStart + " s.");
        			}
        			else if (SOAPUtils.jaComecou() && !SOAPUtils.jaAcabou()) {
        				long durationSeconds = SOAPUtils.startingTime() + SOAPUtils.duration() - currentTimeSeconds();
        				long durationMinutes = 0;
        				if (durationSeconds >= 60) {
        					durationMinutes = durationSeconds/60;
        					durationSeconds -= durationMinutes * 60;
        				}
        				System.out.println("A sessão já começou! Tempo até fechar as urnas: " + durationMinutes + " m " + durationSeconds + " s.");
        			}
        			else {
        				System.out.println("A sessão já terminou!");
        			}
        		}
        		else if ("3".equals(selector)) {
        			if (!SOAPUtils.jaComecou()) {
        				System.out.println("A sessão de votação ainda não começou");
        			}
        			else if (SOAPUtils.jaAcabou()) {
        				System.out.println("A sessão de votação já terminou.");
        			}
        			else if (SOAPUtils.jaVotou(loggeduser)) {
        				System.out.println("Você já votou!");
        			}
        			else {
        				System.out.print("Voto? ");
            			String voteID = sc.nextLine();
            			String item = SOAPUtils.getVotingItem(voteID);
            			if (item == null) {
            				System.out.println("Este item não existe");
            			}
            			else {
            				String[] parts = item.split(Pattern.quote("|"));
            				System.out.print("Confirma voto em " + parts[1] + " (S/N)? ");
            				String confirma = sc.nextLine();
            				if ("S".equals(confirma)) {
            					boolean voteResult = SOAPUtils.voteFunction(loggeduser, voteID);
            					if (voteResult) {
            						System.out.println("Ok, votou em " + parts[1]);
            					}
            					else {
            						System.out.println("Ocorreu um erro ao votar em " + parts[1] + ". Verifique com o administrador se a sua conta pode votar.");
            					}
            				}
            			}
        			}
        		}
        		else if ("4".equals(selector)) {
        			System.out.println("Numero total de votos: " + SOAPUtils.totalVotos());
        		}
        		else if ("5".equals(selector)) {
        			System.out.println("Resultados da votação:");
        			System.out.println(SOAPUtils.listResultados());
        		}
        		else if ("6".equals(selector)) {
        			System.out.println("Item ganhador: " + SOAPUtils.itemGanhador());
        		}
        		else if ("99".equals(selector)) {
        			System.out.println("A sair...");
        			running = false;
        		}
        		else {
        			System.out.println("Opcção inválida!");
        		}
            }
        }
        System.out.println("Cliente desconectado!");
    }
}