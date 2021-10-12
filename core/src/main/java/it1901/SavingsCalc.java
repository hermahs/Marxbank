package it1901;

public class SavingsCalc {

    /**
     * Caclulates the total amount of the savings after a period. 
     * If the period text field is equal to 0, then we change it to 1.
     * 
     * @param monthlyAmount
     * @param lumpAmount
     * @param interestRate (in percent)
     * @param period 
     * @return the total amount after the given period.
     */
    public static double calculation(int monthlyAmount, int lumpAmount, int period, double interestRate) {
        if (period == 0) 
            period = 1;
        
        double lumpAmountSum = lumpAmount;
        double monthlyAmountSum = 0;

        double interestRateFactor = interestRate / 100;

        for (int i = 0; i < period; i++) {
            monthlyAmountSum *= (1 + interestRateFactor);
            lumpAmountSum *= (1 + interestRateFactor);
            for (int j = 1; j <= 12; j++) {
                monthlyAmountSum += monthlyAmount * (1 + (j * interestRateFactor / 12));
            }
        }

        double sum = lumpAmountSum + monthlyAmountSum;

        return sum;
        //return (int) Math.round(sum) / 1;
    }

}

