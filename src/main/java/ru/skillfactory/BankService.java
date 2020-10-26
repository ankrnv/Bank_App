package ru.skillfactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static java.util.Optional.ofNullable;

/**
 * BankService - класс, который нарушает принцип единственной ответственности. У нас он сразу
 * и хранит аккаунты, и реализует логику проверки баланса и переводов.
 */
public class BankService {
    /**
     * В Map-е храним аккаунты, ключ это реквизиты
     * (реквизиты у аккаунтов неизменяемые, это важно сохранить чтобы ключ в map всегда был такой же как у аккаунта)
     */
    private final Map<String, BankAccount> accounts = new HashMap<>();

    /**
     * @param account Аккаунт с заполненными полями.
     */
    public void addAccount(BankAccount account) {
        accounts.putIfAbsent(account.getRequisite(), account);
    }

    /**
     * Метод проверяет что в Map-е есть аккаунт, если есть вернёт реквезиты
     *
     * @param username валидная строка.
     * @param password валидная строка.
     * @return возвращает объект Optional, который будет содержат строку - requisite,
     * если передоваемого пользователя нету или пароль не совпадает вы сможете
     * передать пустой объект Optional и проверить что он не пуст.
     */
    public Optional<String> getRequisiteIfPresent(String username, String password) {

        Optional<String> optional = ofNullable(null);

        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            BankAccount check = entry.getValue();
            if (check.getUsername().equals(username) && check.getPassword().equals(password)) {
                optional = ofNullable(entry.getKey());
                break;
            } else {
                optional = ofNullable(null);
            }

        }
        return optional;
    }


    /**
     * @param requisite реквизиты, строка в произвольном формате.
     * @return кол-во средств
     */
    public long balance(String requisite) {
        long currentBalance = 0L;
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {

            if (entry.getKey().equals(requisite)) {
                BankAccount check = entry.getValue();
                currentBalance = check.getBalance();
                break;
            }
        }
        return currentBalance;
    }


    /**
     * Метод пополняет баланс
     *
     * @param requisite реквизиты, строка в произвольном формате.
     * @param amount    сумма для пополнения.
     * @return возвращает true если баланс был увеличен.
     */
    public boolean topUpBalance(String requisite, long amount) {

        boolean done = false;
        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            if (entry.getKey().equals(requisite)) {
                BankAccount check = entry.getValue();
                check.setBalance(check.getBalance() + amount);
                done = true;
                System.out.println("Баланс пополнен, текущий баланс: " + check.getBalance() + " рублей");
                break;
            } else {
                done = false;
            }
        }
        return done;
    }


    /**
     * Метод, если все условия соблюдены, переводит средства с одного счёта на другой.
     *
     * @param srcRequisite  реквизиты, строка в произвольном формате.
     * @param destRequisite реквизиты, строка в произвольном формате.
     * @param amount        кол-во средств
     * @return true если выполнены все условия, средства фактически переведены.
     */
    public boolean transferMoney(String srcRequisite, String destRequisite, long amount) {


        boolean srcDone = false;
        boolean destDone = false;
        boolean checkAmm;
        BankAccount check = new BankAccount();
        BankAccount checkDest = new BankAccount();

        for (Map.Entry<String, BankAccount> entry : accounts.entrySet()) {
            if (entry.getKey().equals(srcRequisite)) {
                check = entry.getValue();
                srcDone = true;
                break;
            } else {
                srcDone = false;
            }
        }

        for (Map.Entry<String, BankAccount> entryDest : accounts.entrySet()) {
            if (entryDest.getKey().equals(destRequisite)) {
                checkDest = entryDest.getValue();
                destDone = true;
                break;
            } else {
                destDone = false;
            }
        }

        if (amount <= check.getBalance()) {
            checkAmm = true;
            check.setBalance(check.getBalance() - amount);
            checkDest.setBalance(checkDest.getBalance() + amount);
            System.out.println("Перевод выполнен, текущий баланс: " + check.getBalance() + " рублей");
        } else {
            checkAmm = false;
        }

        if (!destDone) {
            System.out.println("Неправильные реквизиты получателя, перевод не выполнен");
        } else if (!checkAmm) {
            System.out.println("Недостаточно средств...");
        }

        return srcDone & destDone & checkAmm;
    }
}



