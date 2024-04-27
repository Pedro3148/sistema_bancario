import java.util.Random;

public class Funcionario extends Thread{
    private Loja loja;
    private Conta contaSalario;
    private Conta contaInvestimento;
    private Banco banco;
    private boolean aguardandoPagamento;

    Funcionario(String nome, Loja loja, Banco banco) {
        super(nome);
        this.loja = loja;
        this.banco = banco;
        this.aguardandoPagamento = true;

        contaSalario = new Conta(getName() + " Salario", 0.00);
        contaInvestimento = new Conta(getName() + " Investimento", 0.00);
        banco.adicionarConta(contaSalario);
        banco.adicionarConta(contaInvestimento);

        start();
    }

    public void run() {
        int numRandom = new Random().nextInt(4000) + 1000;

        while (this.aguardandoPagamento) {

            try {
                Thread.sleep(numRandom);
            } catch (InterruptedException e) {
                System.out.println("Thread interruped");
            }

            loja.pagarFuncionario(this.contaSalario);
            if (contaSalario.getSaldo() == 1400) {
                mudarStatus();
            }
        }

        if (contaSalario.getSaldo() == 1400) {
            Double valorParaInvestir = this.contaSalario.getSaldo() * 0.2;
            investir(valorParaInvestir);
        }
    }

    public void investir(Double valor) {
        int numRandom = new Random().nextInt(4000) + 1000;

        try {
            Thread.sleep(numRandom);
        } catch(InterruptedException e) {
            System.out.println("Thread foi interrompida!");
        }

        banco.transferir(contaSalario, contaInvestimento, valor);
    }

    public Conta getContaSalario() {
        return contaSalario;
    }

    public Conta getContaInvestimento() {
        return contaInvestimento;
    }

    public void mudarStatus() {
        this.aguardandoPagamento = false;
    }
}
