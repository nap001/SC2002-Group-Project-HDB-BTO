package Collection;

import java.util.List;
import Application.Withdrawal;
import Interfaces.CSVHandler;

public class WithdrawalList {
    private List<Withdrawal> withdrawals;
    private final String csvPath;
    private final CSVHandler<Withdrawal> csvHandler;

    public WithdrawalList(String csvPath, CSVHandler<Withdrawal> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.withdrawals = csvHandler.loadFromCSV(csvPath);
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void addItem(Withdrawal withdrawal) {
        withdrawals.add(withdrawal);
    }

    public void removeItem(Withdrawal withdrawal) {
        withdrawals.remove(withdrawal);
    }

    public void saveItems() {
        csvHandler.saveToCSV(withdrawals, csvPath);
    }

//    public Withdrawal findByProjectName(String name) {
//        for (Withdrawal withdrawal : withdrawals) {
//            if (withdrawal.getProjectName().equalsIgnoreCase(name)) {
//                return withdrawal;
//            }
//        }
//        return null;
//    }
}
