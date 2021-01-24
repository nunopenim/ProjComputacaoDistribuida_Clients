package maven.projetoCD;

import java.util.Scanner;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

public class Main {
	
	static String loggeduser;
	
	static String uri = "http://localhost:8080/TomcatFrontendREST/rest/voting";
	
	public static void adminMenu() {
		System.out.println();
		System.out.println("##### REST Client #####");
		System.out.println("MENU ADMINISTRADOR");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Listar resultados da votação (%)");
		System.out.println("99 - Sair");
		System.out.println();
		
	}
	
	public static void userMenu() {
		System.out.println();
		System.out.println("##### REST Client #####");
		System.out.println("MENU USER");
		System.out.println("0  - Menu inicial");
		System.out.println("1  - Listar itens em votação");
		System.out.println("2  - Listar resultados da votação (%)");
		System.out.println("99 - Sair");
		System.out.println();
	}
	
	public static boolean[] logMeIn(String usr, String pw) { // basic
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(uri + "/login/"+ usr +"/" + pw);
		String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		boolean[] returnBool = new boolean[2];
        //System.out.println(webServiceResponse);
        if ("null".equals(response)) {
        	return null;
        }
        if ("truetrue".equals(response)) {
        	returnBool[0] = true;
        	returnBool[1] = true;
        }
        else if ("truefalse".equals(response)) {
        	returnBool[0] = true;
        	returnBool[1] = false;
        }
        else if ("falsetrue".equals(response)) {
        	returnBool[0] = false;
        	returnBool[1] = true;
        }
        else if ("falsefalse".equals(response)) {
        	returnBool[0] = false;
        	returnBool[1] = false;
        }
        else {
        	returnBool = null;
        }
        return returnBool;
	}
	
	public static String listarResultados() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(uri + "/resultadosVotacao");
		String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		String result = "";
		String dados[] = response.split(Pattern.quote("|"));
		for (String a : dados) {
			result += a + "\n";
		}
		return result;
	}
	
	public static String listarItens() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(uri + "/itensEmVotação");
		String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
		String result = "";
		String dados[] = response.split(Pattern.quote("|"));
		for (String a : dados) {
			result += a + "\n";
		}
		return result;
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
        	boolean[] data = logMeIn(uid, pwd);
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
        			System.out.println(listarItens());
        		}
        		else if ("2".equals(selector)) {
        			System.out.println(listarResultados());
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
        			System.out.println(listarItens());
        		}
        		else if ("2".equals(selector)) {
        			System.out.println(listarResultados());
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
	}
}
