package mostrarconta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MostrarConta {
    public static void main(String[] args) {
        final String NOMEARQ = "C:\\Users\\aluno\\Desktop\\MostrarConta\\contas.txt";
        int opcao, conta = 0;
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
                case 4: listarContas(registros, conta);
                    break;
            }
            opcao = menu();
        }
        saida = abreArquivoGravar(NOMEARQ);      
        gravaArquivo(saida ,registros, conta);
        fechaArquivo(saida);
    }
     
    public static void relGeren(){
        int opcao;
        final int FIM = 0;
        
        opcao = MenuExtra();
        while (opcao != FIM) {
            switch (opcao) {
                case 1: salNeg(); break;
                case 2: salMaior(); break;
                case 3: ContaNumero(); break;
                case 4: saldoposi(); break; 
            }
            opcao = menu();
        }
    }
       
    public static int menu() {
        int opcao;
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
    
    private static int MenuExtra() {
        int opcao;
        Scanner entrada = new Scanner(System.in);

        do {
            System.out.println("[1] Saldo negativo");
            System.out.println("[2] Consultar Saldo");
            System.out.println("[3] Contas");
            System.out.println("[4] Saldo Positivo");
            System.out.println("[0] Sair");
            System.out.print("Selecione uma opção: ");
            opcao = entrada.nextInt();
            if ((opcao < 0) || (opcao > 4)) {
                System.out.println("Erro: opção inválida");
            }
        } while ((opcao < 0) || (opcao > 4));    
        return opcao;
    }    

    private static int incluir(Registro[] registros, int conta) {
        String nomeConta; 
        int numeroConta;
        float contaCorrente, contaPoupanca;
        Registro registro;
        Scanner entrada = new Scanner(System.in);
         
        System.out.print("Insira o número da conta: ");
        numeroConta = entrada.nextInt(); 
        if (pesquisaConta(registros, conta, numeroConta) != -1) {
            System.out.println("Erro: conta já existe!");  
            
        }else {
            entrada.nextLine();
            System.out.print("Insira o nome da conta: ");
            nomeConta  = entrada.nextLine();
            nomeConta = nomeConta.toUpperCase();
            System.out.print("Insira a conta corrente: ");
            contaCorrente = entrada.nextFloat();
            System.out.print("Insira a conta poupança: ");
            contaPoupanca = entrada.nextFloat();
            registro = new Registro(numeroConta , nomeConta , contaCorrente, contaPoupanca);
            registros[conta++] = registro;
        }    
        return conta;
    }

    private static void alterar(Registro[] registro, int conta) {
        Scanner entrada = new Scanner(System.in);
        int numeroConta; 
        String NomeConta;
        float ContaCorrente, ContaPoupanca;
        int pos;
        
        System.out.print("Entre com o numero da conta: ");
        numeroConta = entrada.nextInt();
        
        pos = pesquisaConta(registro, conta, numeroConta);
            if (pos != -1) {
        entrada.nextLine();        
        System.out.print("Entre com o nome da conta: ");
        NomeConta = entrada.nextLine();
        System.out.print("Entre com a conta corrente: ");
        ContaCorrente = entrada.nextFloat();
        System.out.print("Entre com a conta poupança: ");
        ContaPoupanca = entrada.nextFloat();
        registro[pos].setnomeConta(NomeConta);
        registro[pos].setcontaCorrente(ContaCorrente);
        registro[pos].setcontaPoupanca(ContaPoupanca);
        }
        else {
            System.out.println("Erro: conta não existe!");
        }
    }
    
    private static int excluir(Registro[] registro, int conta) {    
        int pos, numeroConta;
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("Insira o numero da conta que quer excluir: ");
        numeroConta = entrada.nextInt();
        
        pos = pesquisaConta(registro, conta, numeroConta);
        if (pos != -1) {
            for (int i = pos; i < conta; i++) {
                registro[i] = registro[i+1];
            }    
            conta--;
        }else {
            System.out.println("Erro: conta não existe!");
        }
        return conta;
    }

    private static void relatorioGerencial() {
        
    }
    
    private static void salNeg() {
    
    }

    private static void salMaior() {
    
    }

    private static void ContaNumero() {
    
    }

    private static void saldoposi() {
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
    
    public static void listarContas(Registro[] registros, int conta) {
        
        if (conta > 0) {
            for (int i = 0; i < conta; i++) {
                System.out.println(registros[i]);
            }
        }
        else {
            System.out.println("Não há contas a serem listadas");
        }
    }
}