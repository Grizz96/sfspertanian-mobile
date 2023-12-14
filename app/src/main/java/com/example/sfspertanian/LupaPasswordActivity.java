package com.example.sfspertanian;
import android.content.Intent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import androidx.appcompat.app.AppCompatActivity;

public class LupaPasswordActivity extends AppCompatActivity {
    private EditText TextNama1, masukkantoken;
    private Button btnKirimToken, btnVerify;
    private String generatedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapassword);

        TextNama1 = findViewById(R.id.TextNama1);
        masukkantoken = findViewById(R.id.masukkantoken);
        btnKirimToken = findViewById(R.id.btnKirimToken);
        btnVerify = findViewById(R.id.btnVerify);

        // Implementasi logika untuk tombol Kirim Token
        btnKirimToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mendapatkan email penerima dari EditText
                String emailPenerima = TextNama1.getText().toString().trim();

                // Validasi email
                if (!Patterns.EMAIL_ADDRESS.matcher(emailPenerima).matches()) {
                    Toast.makeText(LupaPasswordActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                } else {
                    // Mengirim token ke email
                    generatedToken = generateRandomToken();
                    new SendEmailTask(emailPenerima, generatedToken).execute();
                }
            }
        });

        // Implementasi logika untuk tombol Verifikasi Token
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyToken();
            }
        });
    }

    private void verifyToken() {
        // Ambil token dari EditText
        String token = masukkantoken.getText().toString().trim();

        // Cek apakah token sudah benar
        if (token.equals(generatedToken)) {
            // Token benar
            // Lakukan tindakan selanjutnya, misalnya mengarahkan pengguna ke activity_passwordbaru.xml
            Intent intent = new Intent(LupaPasswordActivity.this, login_email.class);
            startActivity(intent);
            finish(); // Optional: tutup activity saat ini agar tidak dapat kembali lagi
        } else {
            // Token salah
            // Tampilkan pesan kesalahan
            Toast.makeText(this, "Token salah", Toast.LENGTH_SHORT).show();
        }
    }

    private class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
        // Parameter AsyncTask
        private String emailPenerima;
        private String token;

        // Constructor untuk AsyncTask
        public SendEmailTask(String emailPenerima, String token) {
            this.emailPenerima = emailPenerima;
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Implementasi kirimEmail
            try {
                // Konfigurasi email pengirim
                final String emailPengirim = "e41222830@student.polije.ac.id";
                final String subject = "Token Verifikasi";

                // Konfigurasi server SMTP Gmail
                String host = "smtp.gmail.com";
                String port = "587";

                // Konfigurasi properti email
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", port);
                properties.put("mail.smtp.ssl.trust", host);

                // Membuat sesi untuk otentikasi
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailPengirim, "your_app_password"); // Ganti dengan app password yang dihasilkan dari akun Gmail
                    }
                });

                // Membuat objek pesan email
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailPengirim));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailPenerima));
                message.setSubject(subject);

                // Isi pesan email dengan token
                message.setText("Token Verifikasi Anda adalah: " + token);

                // Mengirim pesan
                Transport.send(message);

                return true;
            } catch (MessagingException e) {
                // Menangani exception
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(LupaPasswordActivity.this, "Token telah dikirim ke email Anda", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LupaPasswordActivity.this, "Gagal mengirim email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String generateRandomToken() {
        // Logika untuk menghasilkan token acak (Anda dapat mengganti ini sesuai kebutuhan)
        // Contoh
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }
}
