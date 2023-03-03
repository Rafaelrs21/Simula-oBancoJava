package mostrarconta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MostrarConta {
    public static void main(String[] args) {
        final String NOMEARQ = "C:\\Users\\aluno\\Desktop\\MostrarConta\\contas.txt";
        int opcao, conta;
        final int FIM = 0;
        Formatter saida;
        Scanner entrada;
        Registro[] registros = new Registro[50];
        
        entrada = abreArquivoLer(NOMEARQ);
        conta = leArquivo(entrada, registros);
        fechaArquivo(entrada);
        opcao = menu();
        while (opcao != FIM) {
            switch (opcao) {
                case 1: conta = incluir(registros, conta); 
                    break;
                case 2: alterar(registros, conta); 
                    break;
                case 3: conta = excluir(registros, conta); 
                    break;
                case 4: listarContas(registros,conta);
                    break;
            }
            opcao = menu();
        }
        saida = abreArquivoGravar(NOMEARQ);      
        gravaArquivo(saida ,registros, conta);
        fechaArquivo(saida);
      }
         
    public static int menu() {
        int opcao = 0;
        Scanner entrada = new Scanner(System.in);

        do {
            System.out.println("[1] Incluir");
            System.out.println("[2] Alterar");
            System.out.println("[3] Excluir");
            System.out.println("[4] Listar");
            System.out.println("[0] Sair");
            System.out.print("Selecione uma opção: ");
            opcao = entrada.nextInt();
            if ((opcao < 0) || (opcao > 4)) {
                System.out.println("Erro: opção inválida");
            }
        } while ((opcao < 0) || (opcao > 4));    
        return opcao;
    }

     private static int menuExtra() {
        int opcao;
        Scanner entrada = new Scanner(System.in);
         
        do {
            System.out.println("[1]Saldo Negativo");
            System.out.println("[2] Comparar saldo");			
	    System.out.println("[3] Todas as contas");	
	    System.out.println("[4] Maior saldo");
            System.out.println("[0] Sair");
	    System.out.print("Escolha uma opção: ");
            opcao = entrada.nextInt();
            if ((opcao < 0) ||(opcao > 4)) {
                System.out.println("Error: opção invalida");
            }
        } while ((opcao < 0) ||(opcao > 4));
        return opcao;
    }
    
    public static int leNumero() {
        int n = 0;
        boolean ok;
        Scanner entrada = new Scanner(System.in);
        
        do {
            try {
                System.out.print("Entre com um número: ");
                n = entrada.nextInt();
                ok = true;
            }
            catch (InputMismatchException erro) {
                System.out.println("Erro: número inválido");
                ok = false;
                entrada.nextLine();
            }
        } while (!ok);    
        return n;
    }
     
    private static int incluir(Registro[] registros, int conta) {
        String nomeConta; 
        int numeroConta;
        double contaCorrente, contaPoupanca ;
        Registro registro;
        Scanner entrada = new Scanner(System.in);
         
        numeroConta = leNumero();
        if (pesquisaConta(registros, conta, numeroConta) != -1) {
            System.out.println("Erro: conta já existe!");  
        }
        else{
            System.out.print("Insira o nome da conta: ");
            nomeConta  = entrada.nextLine();
            nomeConta = nomeConta.toUpperCase();
            System.out.println("Insira a conta corrente ");
            contaCorrente = leNumero();
            System.out.println("Insira a conta poupança ");
            contaPoupanca = leNumero();
            if(contaCorrente == 0 && contaPoupanca == 0) {
                System.out.println("Erro: Conta zerada");  
            }
            else {
                registro = new Registro(numeroConta , nomeConta , contaCorrente, contaPoupanca);
                registros[conta++] = registro;    
            }    
        }
        return conta;
    }

    private static int alterar(Registro[] registro, int conta) {
        Scanner entrada = new Scanner(System.in);
        int numeroConta; 
        String NomeConta;
        double contaCorrente, contaPoupanca;
        int pos;
        
        System.out.print("Entre com o numero da conta: ");
        numeroConta = leNumero();
        pos = pesquisaConta(registro, conta, numeroConta);
        if (pos != -1) {
            entrada.nextLine();        
            System.out.print("Entre com o nome da conta: ");
            NomeConta = entrada.nextLine();
            NomeConta = NomeConta.toUpperCase();
            System.out.print("Entre com a conta corrente: ");
            contaCorrente = entrada.nextDouble();
            System.out.print("Entre com a conta poupança: ");
            contaPoupanca = entrada.nextDouble();
            registro[pos].setnomeConta(NomeConta);
            registro[pos].setcontaCorrente(contaCorrente);
            registro[pos].setcontaPoupanca(contaPoupanca);
        }   
        else {
            System.out.println("Erro: conta não existe!");
        }
        return conta;
    } 
    
    private static int excluir(Registro[] registro, int conta) {    
        int pos, numeroConta;
        double contaCorrente, contaPoupanca;
        Scanner entrada = new Scanner(System.in);
        
        System.out.println("Insira o numero da conta que quer excluir");
        numeroConta = leNumero();
        pos = pesquisaConta(registro, conta, numeroConta);
        if (pos != -1) {
            if (registro[pos].getcontaCorrente() == 0 && registro[pos].getcontaPoupanca() == 0) { 
                for (int i = pos; i < conta; i++) {
                    registro[i] = registro[i+1];
                }
                conta--;
                System.out.println("Conta apagada com sucesso");    
            }    
            else { 
                System.out.println("Saldo maior/menor que zero");    
            }
        }
        else {
            System.out.println("Conta inexistente");
        }
        return conta;
    }

     public static void listarContas(Registro[] registros, int conta) {
        final int FIM = 0;
        int opcao;
        int pos;
        
        opcao = menuExtra();
        while (opcao != FIM) {
            switch (opcao) {
                case 1: contaNegativa(registros, conta); 
                    break;
                case 2:  pesquisarSaldo(registros, conta); 
                    break;
                case 3: mostrarConta(registros, conta); 
                    break;
                case 4: maiorCorrente(registros, conta);
                    break;
            }
            opcao = menuExtra();
        }   
    }
 
  private static void contaNegativa(Registro[] registros, int conta) {
        System.out.println("Contas negativadas: ");
        
        if (conta == 0) {
            System.out.println("Não há contas a serem listadas");
            return;
        }
        for (int i = 0; i <= conta; i++) {
            if (registros[i] != null && registros[i].getcontaCorrente()< 0) {
                System.out.println(registros[i]);
            }
        }
    }

     private static void pesquisarSaldo(Registro[] registros, int conta) {
        Scanner entrada = new Scanner(System.in);
        double valor ;
        
        if (conta == 0) {
                System.out.println("Não há contas a serem listadas");
                return;
        }
        
        System.out.println("Informe um valor: ");
        valor = leNumero();   
        System.out.printf("Saldo superior a %.2f\n", valor);
        for (int i = 0; i < conta; i++) {
            if (registros[i] != null && registros[i].getcontaCorrente() >= valor){
                System.out.println(registros[i]);
            }else{
                System.out.printf("%.2f é o maior valor" , valor);
                System.out.println("");
            }
        }
     }

     private static void mostrarConta(Registro[] registros, int conta) {
        
        if (conta == 0) {
            System.out.println("Não há contas a serem listadas");
            return;
        } 
        System.out.println(" todas as contas do sistema: ");
        for (int i = 0; i < conta; i++) {
            System.out.println(registros[i]);
        }
    }

    private static void maiorCorrente(Registro[]registros, int conta) {      
        
        if (conta == 0) {
            System.out.println("Não há contas a serem listadas");
            return;
        }
        double maiorValor =  registros[0].getcontaCorrente();
        for (int i = 0; i < conta; i++){
            if(registros[i].getcontaCorrente() > maiorValor) {
                maiorValor = registros[i].getcontaCorrente();
            } 
        }
        System.out.println("Maior saldo é: " + maiorValor);
        
    }
    
    public static Scanner abreArquivoLer(String arq) {
        Scanner entrada = null;
        
        try {
            entrada = new Scanner(new File(arq));
        }
        catch (FileNotFoundException erro) {
            System.out.println("Erro: arquivo nao existe");
            System.exit(1);
        }
        return entrada;
    }
    
    public static int leArquivo(Scanner entrada, Registro[] registros) {
        int i = 0;
        String linha;
        String[] campos;
        
        try {
            while(entrada.hasNext()) {
                linha = entrada.nextLine();
                campos = linha.split(",");
                Registro registro = new Registro(Integer.parseInt(campos[0]), campos[1], 
                    Double.parseDouble(campos[2]), Double.parseDouble(campos[3]));
                registros[i++] = registro;
            }
        }
        catch (NoSuchElementException erro) {
            System.out.println("Erro: formatacao do arquivo");
        }
        catch (IllegalStateException erro) {
            System.out.println("Erro: leitura do arquivo");
        }
        return i;
    } 
    
    public static void fechaArquivo(Scanner entrada) {
        if (entrada != null) {
            entrada.close();
        }
    }
    
    public static void fechaArquivo(Formatter saida) {
        if (saida != null) {
            saida.close();
        }
    }
    
    public static Formatter abreArquivoGravar(String arq) {
        Formatter saida = null;
        
        try {
            saida = new Formatter(arq);
        }
        catch (FileNotFoundException erro) {
            System.out.println("Erro: criacao do arquivo");
            System.exit(1);
        }
        catch (SecurityException erro) {
            System.out.println("Erro: problema de acesso ao arquivo");
            System.exit(1);
        }
        return saida;
    }    
    
    public static void gravaArquivo(Formatter saida, Registro[] registro, int conta) {

        try {
            System.out.println(conta);
            for (int i = 0; i < conta; i++) {
                saida.format("%d,%s,%.2f,%.2f%n", registro[i].getnumeroConta(), registro[i].getnomeConta(),
                    registro[i].getcontaCorrente(), registro[i].getcontaPoupanca());
            }    
        }
        catch (FormatterClosedException erro) {
            System.err.println( "Erro: Não escreveu no arquivo." );
        }
    }
    
    public static int pesquisaConta(Registro[] registros, int conta, int numeroConta) {
        int pos = -1;
        
        for (int i = 0; i < conta; i++) {
            if (registros[i].getnumeroConta() == numeroConta) {
                pos = i;
                break;
            }
        }
        return pos;
    }   
}