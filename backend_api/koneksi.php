<?php
// database.php
$host = "localhost";
$dbUsername = "root";
$dbPassword = "";
$database = "sfs_pertanian";

$conn = mysqli_connect($host, $dbUsername, $dbPassword, $database);

if (!$conn){
    echo "koneksi gagal";
} 

?>