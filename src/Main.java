//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int numCliente = 5;
    static int numLoja = 2;

    static public List<Loja> criarLoja(int numeroDeLoja, Banco banco) {
        List<Loja> lojas = new ArrayList<>();
        for (int i = 0; i < numeroDeLoja; i++) {
            Loja loja = new Loja("Loja #" + (i+1), banco);
            lojas.add(loja);
        }
        return  lojas;
    }

    static public Funcionario[] criarFuncionario(List<Loja> lojas, Banco banco) {
        Funcionario[] funcionarios = new Funcionario[lojas.size()*2];
        int qtdFuncionario = 0;
        for (Loja loja : lojas) {
            funcionarios[qtdFuncionario] = new Funcionario("Funcionario #" + (++qtdFuncionario), loja, banco);
            funcionarios[qtdFuncionario] = new Funcionario("Funcionario #" + (++qtdFuncionario), loja, banco);
        }

        return  funcionarios;
    }

    static public Cliente[] criarCliente(int numeroDeCliente, List<Loja> lojas, Banco banco) {
        Cliente[] clientes = new Cliente[numeroDeCliente];
        for (int i = 0; i < numeroDeCliente; i++) {
            clientes[i] = new Cliente("Cliente #" + (i+1), lojas, banco);
        }
        return  clientes;
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        List<Loja> lojas = criarLoja(numLoja, banco);
        Funcionario[] funcionarios = criarFuncionario(lojas, banco);
        Cliente[] clientes = criarCliente(numCliente, lojas, banco);


        try {
            for (Cliente cliente : clientes) {
                cliente.join();
            }

            for (Loja loja : lojas) {
                Conta conta = loja.getConta();
                while (conta.getSaldo() >= loja.getSalarioFuncionario()) {
                    Thread.sleep(2000);
                }
            }

            for (Funcionario funcionario : funcionarios) {
                funcionario.mudarStatus();
                System.out.print(". ");
                Thread.sleep(2000);
            }
            System.out.print("\n\n");

        } catch (InterruptedException e) {
            System.out.println("Main Thread interruped");
        }

        for (Cliente cliente : clientes) {
            Conta conta = cliente.getConta();
            System.out.println(cliente.getName() + " Conta " +  ": " + conta.getSaldo());
        }


        for (Loja loja: lojas) {
            Conta conta = loja.getConta();
            System.out.println(loja.getNome() + " Conta " + ": " + conta.getSaldo());
        }


        for (Funcionario funcionario : funcionarios) {
            Conta contaSalario = funcionario.getContaSalario();
            Conta contaInvestimento = funcionario.getContaInvestimento();
            System.out.println(funcionario.getName()  + " Conta Salario" + ": " + contaSalario.getSaldo());
            System.out.println(funcionario.getName()  + " Conta Investimento" + ": " + contaInvestimento.getSaldo());
        }
    }
}