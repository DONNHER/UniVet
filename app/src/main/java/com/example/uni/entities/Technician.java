package com.example.uni.entities;

public class Technician {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String image;
        private String password;
        // Constructor
        public Technician( String email, String password) {
            this.email = email;
            this.password = password;
        }
        public Technician(int id, String name, String email, String phone, String address, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.password =password;
        }

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword(){ return  password;}

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Owner{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
    }
}
