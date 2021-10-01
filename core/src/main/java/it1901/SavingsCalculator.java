package it1901;

public class SavingsCalculator {

    
    public static long calculator(int monthlyAmount, int lumpAmount, double interestRate, int period) {
        if (monthlyAmount == 0) {
            double sum = lumpAmount * Math.pow(1 + (interestRate / 100), period);
            return Math.round(sum);
        }

        double interestRatePerYear = interestRate / 12;

        double sum = lumpAmount;

        for (int i = 0; i < period; i++) {
            for (int j = 0; j < 12; j++) {
                sum += monthlyAmount;
                sum *= (1 + (interestRatePerYear / 100));
            }
            
        }
        return Math.round(sum);
    }



    public static void main(String[] args) {
        long s = SavingsCalculator.calculator(100, 1000, 5, 1);
        System.out.println(s);
    }
}