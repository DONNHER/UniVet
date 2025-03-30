package com.example.uni;

public class  serviceType {
    public enum Services {
        grooming, medical;
        public enum grooming{
        Trimming,
        Cleaning
        }
        public enum medical{
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

    public serviceType(String email, Services transactionType, String description) {
        this.email = email;
        this.serviceType = transactionType;
        this.description = description;
    }
}
