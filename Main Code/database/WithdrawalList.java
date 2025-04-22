package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.Withdrawal;

public class WithdrawalList implements Serializable{
    private List<Withdrawal> withdrawals;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public WithdrawalList() {
        this.withdrawals = new ArrayList<>();
    }

    public void addWithdrawal(Withdrawal withdrawal) {
        if (withdrawal != null) {
            withdrawals.add(withdrawal);
        }
    }

    public List<Withdrawal> getAllWithdrawals() {
        return withdrawals;
    }

    public Withdrawal getWithdrawalByApplicant(String nric) {
        for (Withdrawal w : withdrawals) {
            if (w.getApplicant().getNRIC().equals(nric)) {
                return w;
            }
        }
        return null;
    }

    public void removeWithdrawal(Withdrawal withdrawal) {
        withdrawals.remove(withdrawal);
    }

    public boolean hasPendingWithdrawal(String nric) {
        for (Withdrawal w : withdrawals) {
            if (w.getApplicant().getNRIC().equals(nric) &&
                w.getStatus() == ENUM.ApplicationStatus.WITHDRAWNPENDING) {
                return true;
            }
        }
        return false;
    }

    public List<Withdrawal> getPendingWithdrawals() {
        List<Withdrawal> pending = new ArrayList<>();
        for (Withdrawal w : withdrawals) {
            if (w.getStatus() == ENUM.ApplicationStatus.WITHDRAWNPENDING) {
                pending.add(w);
            }
        }
        return pending;
    }
}
