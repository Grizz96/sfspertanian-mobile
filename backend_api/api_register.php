<?php

include 'koneksi.php';

$email = $_POST['email'];
$password = $_POST['password'];

$queryRegister = "SELECT * FROM users WHERE email = '".$email."'";

$msql = mysqli_query($conn, $queryRegister);
$result = mysqli_num_rows($msql);

if (!empty($email) && !empty($password)) {
 if ($result == 0) {
    $regis = "INSERT INTO users ( email, password) VALUES 
    ('$email', '$password')";
    echo    "Daftar Berhasil";
    $msqlRegis = mysqli_query($conn, $regis);
 }else{
    echo    "Email sudah terdaftar";
 }
}


?>