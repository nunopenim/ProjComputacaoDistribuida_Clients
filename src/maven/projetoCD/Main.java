package maven.projetoCD;

import java.util.Scanner;
import java.util.regex.Pattern;

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
        		else if ("1".equals(selector)) {
        			System.out.println(SOAPUtils.listAllVotingStuff());
        		}
        		else if ("5".equals(selector)) {
        			System.out.println("Numero total de votos: " + SOAPUtils.totalVotos());
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
        		else if ("3".equals(selector)) {
        			if (SOAPUtils.jaVotou(loggeduser)) {
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