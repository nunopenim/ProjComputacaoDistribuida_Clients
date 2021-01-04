package maven.projetoCD;

import java.util.Scanner;

public class Main {
	
	static String loggeduser;
	
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