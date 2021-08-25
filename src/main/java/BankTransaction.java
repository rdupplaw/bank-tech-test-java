import java.time.LocalDate;

record BankTransaction(int amount, LocalDate date, int balance) {
    public static class Builder {
        private int amount;
        private LocalDate date;
        private int balance;

        private Builder(int amount) {
            this.amount = amount;
        }

        public static Builder amount(int amount) {
            return new Builder(amount);
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder balance(int balance) {
            this.balance = balance;
            return this;
        }

        public BankTransaction build() {
            return new BankTransaction(amount, date, balance);
        }
    }
}
