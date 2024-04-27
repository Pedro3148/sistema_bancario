import java.util.concurrent.locks.ReentrantLock;

public class Loja {
    private ReentrantLock lock;

    private String nome;
    private Conta conta;
    private Banco banco;

    Loja(String nome, Banco banco) {
        this.lock = new ReentrantLock();

        this.nome = nome;
        this.banco = banco;

        conta = new Conta(this.nome, 0.00);
        banco.adicionarConta(conta);
    }

    public void pagarFuncionario(Conta conta) {
        this.lock.lock();

        if (this.conta.getSaldo() >= 1400) {
            banco.transferir(this.conta, conta, 1400.00);
        }
        this.lock.unlock();
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
}
