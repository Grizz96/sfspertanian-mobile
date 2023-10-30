package com.example.sfspertanian;

public class Petani {

        public String username;
        public String email;

        public Petani() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Petani(String username, String email) {
            this.username = username;
            this.email = email;
        }


}
