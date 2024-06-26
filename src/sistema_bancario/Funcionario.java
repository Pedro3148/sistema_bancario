package sistema_bancario;

import java.util.Random;

public class Funcionario extends Thread{
    private final Loja loja;
    private final Conta contaSalario;
    private final Conta contaInvestimento;
    private final Banco banco;
    private boolean esperandoPagamento;

    Funcionario(String nome, Loja loja, Banco banco) {
        super(nome);
        this.loja = loja;
        this.banco = banco;
        this.esperandoPagamento = true;

        contaSalario = new Conta(getName() + " Salario", 0.00);
        contaInvestimento = new Conta(getName() + " Investimento", 0.00);
        banco.adicionarConta(contaSalario);
        banco.adicionarConta(contaInvestimento);

        start();
    }

    public void run() {
        while (esperandoPagamento) {
            int numRandom = new Random().nextInt(4000) + 1000;
            try {
                Thread.sleep(numRandom);
            } catch (InterruptedException e) {
                System.out.println("Thread foi interrompida!");
            }

            loja.pagarFuncionario(this.contaSalario);
            if (this.contaSalario.getSaldo() == 1400) {
                Double valorParaInvestir = this.contaSalario.getSaldo() * 0.2;
                investir(valorParaInvestir);
                break;
            }
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
        this.esperandoPagamento = false;
    }
}
