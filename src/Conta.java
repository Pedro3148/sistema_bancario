public class Conta {
    private final String titular;
    private Double saldo;

    Conta(String titular, Double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public void depositar(Double valor) {
        saldo += valor;
    }

    public boolean sacar(Double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public String getTitular() {
        return titular;
    }

    public Double getSaldo() {
        return saldo;
    }
}
