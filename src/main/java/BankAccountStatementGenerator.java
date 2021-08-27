import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class BankAccountStatementGenerator {
    private static final String TABLE_HEADER = "date || credit || debit || balance\n";
    private final MessageFormat transactionFormat = new MessageFormat("{0} || {1} || {2} || {3}\n");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String generateStatement(ArrayList<BankTransaction> transactions) {
        StringBuilder statement = new StringBuilder(TABLE_HEADER);
        transactions.sort(Comparator.comparing(BankTransaction::date).reversed());
        transactions.forEach(transaction -> statement.append(formatTransaction(transaction)));

        return statement.toString().trim();
    }

    private String formatTransaction(BankTransaction transaction) {
        Object[] args;
        if (transaction.amount() < 0) {
            args = new Object[]{transaction.date().format(dateFormat), "-", formatAmount(-transaction.amount()), formatAmount(transaction.balance())};
        } else {
            args = new Object[]{transaction.date().format(dateFormat), formatAmount(transaction.amount()), "-", formatAmount(transaction.balance())};
        }
        return transactionFormat.format(args);
    }

    private String formatAmount(int amount) {
        Float amountFloat = (float) amount;
        return String.format("%.2f", amountFloat);
    }
}
