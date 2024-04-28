package sistema_bancario;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Loja {
    private ReentrantLock lock;
    private Condition saldoDisponivelPagamento;

    private final String nome;
    private final Conta conta;
    private final Banco banco;
    private final Double salarioFuncionario;

    Loja(String nome, Banco banco) {
        this.lock = new ReentrantLock();
        this.saldoDisponivelPagamento = this.lock.newCondition();
        this.nome = nome;
        this.banco = banco;
        this.salarioFuncionario = 1400.00;

        conta = new Conta(this.nome, 0.00);
        banco.adicionarConta(conta);
    }

    public void comprar(Cliente cliente, Double valorCompra) {
        this.lock.lock();
        banco.transferir(cliente.getConta(), this.conta, valorCompra);
        this.lock.unlock();
    }

    public boolean pagarFuncionario(Conta contaFuncionario) {
        this.lock.lock();
        try {
            if (this.conta.getSaldo() >= this.salarioFuncionario) {
                banco.transferir(this.conta, contaFuncionario, this.salarioFuncionario);
                return true;
            }
            return  false;
        } finally {
            this.lock.unlock();
        }
    }

    public Conta getConta() {
        this.lock.lock();
        try {
            return conta;
        } finally {
            this.lock.unlock();
        }
    }

    public String getNome() {
        return nome;
    }

    public Double getSalarioFuncionario() {
        return salarioFuncionario;
    }

}
