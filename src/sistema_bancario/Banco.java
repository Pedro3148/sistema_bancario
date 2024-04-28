package sistema_bancario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    private final List<Conta> contas = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public void adicionarConta(Conta conta) {
        this.lock.lock();
        try {
            contas.add(conta);
        } finally {
            this.lock.unlock();
        }
    }

    public void transferir(Conta contaOrigem, Conta contaDestino, Double valor) {
        this.lock.lock();

        System.out.println("==============================");
        try {
            if (contaOrigem.sacar(valor)) {
                contaDestino.depositar(valor);
                System.out.println("Transferencia realizada");
            } else {
                System.out.println("Transfenrencia nao realizada");
                System.out.println("Saldo insuficente!!!");
            }
            System.out.printf("De: %s Para: %s Valor: %.2f\n\n", contaOrigem.getTitular(), contaDestino.getTitular(), valor);
        } finally {
            this.lock.unlock();
        }
    }

    public Conta getConta(String titular) {
        this.lock.lock();

        try {
            Conta c = null;
            for (Conta conta : contas) {
                if (conta.getTitular().equals(titular)) {
                    c = conta;
                }
            }
            return c;
        } finally {
            this.lock.unlock();
        }
    }
}
