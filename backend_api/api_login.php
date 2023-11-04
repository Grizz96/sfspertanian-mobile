<?php

include 'koneksi.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $cek = "SELECT * FROM users WHERE email = '$email' AND password = '$password'";
    $msql = mysqli_query($conn, $cek);
    $result = mysqli_num_rows($msql);

    if (!empty($email) && !empty($password)) {
        if ($result == 0) {
            echo "0";
        } else {
            echo "Selamat Datang";
        }
    }
}
?>
