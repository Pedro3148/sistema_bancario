import java.util.List;
import java.util.Random;

public class Cliente extends Thread{
    private final Conta conta;
    private final Banco banco;
    private final List<Loja> lojas;

    Cliente(String nome, List<Loja> lojas, Banco banco) {
        super(nome);
        this.lojas = lojas;
        this.banco = banco;

        conta = new Conta(getName()  , 1000.00);
        banco.adicionarConta(conta);
        start();
    }

    public void run() {
        while (conta.getSaldo() != 0) {
            int numRandom = new Random().nextInt(4000) + 1000;

            try {
                Thread.sleep(numRandom);
            } catch (InterruptedException e) {
                System.out.println("Thread foi interrompida!");
            }

            Loja loja = lojas.get(new Random().nextInt(lojas.size()));

            int numRandomValor = new Random().nextInt(2);
            Double valorCompra = numRandomValor == 0 ? 100.00 : 200.00;

            loja.comprar(this, valorCompra);
        }
    }

    public Conta getConta() {
        return conta;
    }
}