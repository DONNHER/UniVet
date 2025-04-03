package com.example.uni;

public class  serviceType {
    public static enum Services {
        grooming, medical;
        public static enum grooming{
        Trimming,
        Cleaning
        }
        public static enum medical{
            Checkup,
            Vaccination,
            Surgeries
        }
    }

    /**
     * Email address that triggered this transaction.
     */
    public String email;
    /**
     * Type of transcation that was triggered.
     */
    public Services serviceType;
    /**
     * Description of the transaction.
     */
    public String description;

    public serviceType( Services transactionType, String description) {
        this.serviceType = transactionType;
        this.description = description;
    }
}
