import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("BankAccount")
public class BankAccountTest {
    private BankAccount subject;

    @BeforeEach
    void setup() {
        BankAccountStatementGenerator bankAccountStatementGenerator = new BankAccountStatementGenerator();
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
        subject.deposit(1000, LocalDate.of(2021, 1, 10));
        subject.withdraw(500, LocalDate.of(2021, 1, 14));
        String result = subject.generateStatement();

        assertEquals("""
                date || credit || debit || balance
                14/01/2021 || - || 500.00 || 500.00
                10/01/2021 || 1000.00 || - || 1000.00""", result);
    }

    @Test
    void testDepositAndWithdraw() {
        subject.deposit(1000, LocalDate.of(2021, 1, 10));
        subject.deposit(2000, LocalDate.of(2021, 1, 13));
        subject.withdraw(500, LocalDate.of(2021, 1, 14));
        String result = subject.generateStatement();

        assertEquals("""
                date || credit || debit || balance
                14/01/2021 || - || 500.00 || 2500.00
                13/01/2021 || 2000.00 || - || 3000.00
                10/01/2021 || 1000.00 || - || 1000.00""", result);
    }

    @Nested
    @DisplayName("when withdrawal would put balance below zero")
    class whenWithdrawalWouldPutBalanceBelowZero {
        @Test
        @DisplayName("throws ArithmeticException")
        void testThrowsArithmeticException() {
            Exception exception = assertThrows(ArithmeticException.class, () -> subject.withdraw(1000, LocalDate.of(2021, 1, 10)));
            assertEquals("Cannot withdraw below zero", exception.getMessage());
        }
    }
}
