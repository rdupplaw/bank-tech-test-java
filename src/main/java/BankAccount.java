import java.time.LocalDate;
import java.util.ArrayList;

public class BankAccount {
    private final ArrayList<BankTransaction> transactions = new ArrayList<>();
    private final BankAccountStatementGenerator bankAccountStatementGenerator;
    private int previousBalance = 0;

    public BankAccount(BankAccountStatementGenerator bankAccountStatementGenerator) {
        this.bankAccountStatementGenerator = bankAccountStatementGenerator;
    }

    public void deposit(int amount, LocalDate date) {
        BankTransaction transaction = BankTransaction.Builder.amount(amount).date(date).balance(previousBalance + amount).build();
        this.previousBalance = previousBalance + amount;
        transactions.add(transaction);
    }

    public void withdraw(int amount, LocalDate date) {
        BankTransaction transaction = BankTransaction.Builder.amount(-amount).date(date).balance(previousBalance - amount).build();
        this.previousBalance = previousBalance - amount;
        transactions.add(transaction);
    }

    public String generateStatement() {
        return this.bankAccountStatementGenerator.generateStatement(transactions);
    }
}