import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {
    private BankAccountStatementGenerator bankAccountStatementGenerator;
    private BankAccount subject;

    @BeforeEach
    void setup() {
        bankAccountStatementGenerator = new BankAccountStatementGenerator();
        subject = new BankAccount(bankAccountStatementGenerator);
    }

    @Test
    void testDeposit() {
        subject.deposit(1000, LocalDate.of(2021, 1, 10));
        String result = subject.generateStatement();

        assertEquals("date || credit || debit || balance\n" +
                "10/01/2021 || 1000.00 || - || 1000.00", result);
    }

    @Test
    void testWithdraw() {
        subject.withdraw(500, LocalDate.of(2021, 1, 14));
        String result = subject.generateStatement();

        assertEquals("date || credit || debit || balance\n" +
                "14/01/2021 || - || 500.00 || -500.00", result);
    }

    @Test
    void testDepositAndWithdraw() {
        subject.deposit(1000, LocalDate.of(2021, 1, 10));
        subject.deposit(2000, LocalDate.of(2021, 1, 13));
        subject.withdraw(500, LocalDate.of(2021, 1, 14));
        String result = subject.generateStatement();

        assertEquals("date || credit || debit || balance\n" +
                "14/01/2021 || - || 500.00 || 2500.00\n" +
                "13/01/2021 || 2000.00 || - || 3000.00\n" +
                "10/01/2021 || 1000.00 || - || 1000.00", result);
    }
}
