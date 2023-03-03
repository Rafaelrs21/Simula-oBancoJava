package mostrarconta;
    public class Registro {
        private int numeroConta;
	private String nomeConta;
	private double contaCorrente;
	private double contaPoupanca;
	
	public Registro(){
		
	}
	
	Registro(int numeroConta, String nomeConta, double contaCorrente,double contaPoupanca) {
            this.numeroConta = numeroConta;
            this.nomeConta = nomeConta;
            this.contaCorrente = contaCorrente;
            this.contaPoupanca = contaPoupanca;
        }

	public int getnumeroConta() {
		return numeroConta;
	}

	public void setnumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getnomeConta() {
		return nomeConta;
	}

	public void setnomeConta(String nomeConta) {
		this.nomeConta = nomeConta;
	}

	public double getcontaCorrente() {
		return contaCorrente;
	}

	public void setcontaCorrente(double contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public double getcontaPoupanca() {
		return contaPoupanca;
	}

	public void setcontaPoupanca(double contaPoupanca) {
		this.contaPoupanca = contaPoupanca;
	}
        
        @Override
        public String toString() {
            String conta;
            
            conta = numeroConta + " " + nomeConta + " " + contaCorrente + " " + contaPoupanca;
            return conta;
        }
}