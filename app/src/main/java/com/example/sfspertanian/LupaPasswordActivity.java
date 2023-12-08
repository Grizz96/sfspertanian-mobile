package com.example.sfspertanian;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import androidx.appcompat.app.AppCompatActivity;


public class LupaPasswordActivity extends AppCompatActivity {
    private TextView txtemail1, txtemail2, txtpassword;
    private EditText TextNama1, masukkantoken;
    private Button btnKirimToken, btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapassword);

        txtemail1 = findViewById(R.id.txtemail1);
        txtemail2 = findViewById(R.id.txtemail2);
        txtpassword = findViewById(R.id.txtpassword);
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

                // Mengirim token ke email
                kirimEmail(emailPenerima);
            }
        });
    }

    private void kirimEmail(final String emailPenerima) {
        // Konfigurasi email pengirim
        final String emailPengirim = "rizkypradika27@gmail.com";
        final String passwordPengirim = "rizkypradikayayuk"; // Ganti dengan password email pengirim
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

        // Membuat sesi untuk otentikasi
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailPengirim, passwordPengirim);
            }
        });

        try {
            // Membuat objek pesan email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailPengirim));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailPenerima));
            message.setSubject(subject);

            // Menghasilkan token secara acak (Anda dapat mengganti ini sesuai kebutuhan)
            String token = generateRandomToken();

            // Isi pesan email dengan token
            message.setText("Token Verifikasi Anda adalah: " + token);

            // Mengirim pesan
            Transport.send(message);

            // Menampilkan pesan sukses
            Toast.makeText(this, "Token telah dikirim ke email Anda", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            // Menampilkan pesan error
            e.printStackTrace();
            Toast.makeText(this, "Gagal mengirim email", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateRandomToken() {
        // Logika untuk menghasilkan token acak (Anda dapat mengganti ini sesuai kebutuhan)
        // Contoh sederhana: menghasilkan string acak dengan panjang 6 karakter
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }
}
