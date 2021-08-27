import java.time.LocalDate;
import java.util.ArrayList;

public class BankAccount {
    private final ArrayList<BankTransaction> transactions;
    private final BankAccountStatementGenerator bankAccountStatementGenerator;

    public BankAccount(BankAccountStatementGenerator bankAccountStatementGenerator) {
        this.transactions = new ArrayList<>();
        this.bankAccountStatementGenerator = bankAccountStatementGenerator;
    }

    public void deposit(int amount, LocalDate date) {
        transactions.add(new BankTransaction(amount, date, getNewBalance(amount, BankTransactionType.CREDIT), BankTransactionType.CREDIT));
    }

    public void withdraw(int amount, LocalDate date) {
        if (getNewBalance(amount, BankTransactionType.DEBIT) < 0) {
            throw new ArithmeticException("Cannot withdraw below zero");
        }

        transactions.add(new BankTransaction(-amount, date, getNewBalance(amount, BankTransactionType.DEBIT), BankTransactionType.DEBIT));
    }

    public String generateStatement() {
        return this.bankAccountStatementGenerator.generateStatement(transactions);
    }

    private int getNewBalance(int amount, BankTransactionType type) {
        return getPreviousBalance() + switch (type) {
            case DEBIT -> -amount;
            case CREDIT -> amount;
        };
    }

    private int getPreviousBalance() {
        if (transactions.isEmpty()) {
            return 0;
        }

        BankTransaction lastTransaction = transactions.get(transactions.size() - 1);
        return lastTransaction.balance();
    }
}