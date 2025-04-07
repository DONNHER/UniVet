package com.example.uni.management;

public class  serviceType {
    public static enum Services {
        grooming, medical,products, Services;
        public static enum Grooming{
        Trimming,
        Cleaning
        }
        public static enum Medical{
            Checkup,
            Vaccination,
            Surgeries
        }
        public static enum Products{
            Feeds,
            Accessories
        }
        public static enum Other{
            inquiries,
            contacts
        }
    }
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
