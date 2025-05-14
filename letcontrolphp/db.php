<?php
header("Access-Control-Allow-Origin: *");
$mysqli = new mysqli('localhost', 'root', '', 'letcontrol_1');
if ($mysqli->connect_error) {
    die('Erro de conexão: (' . $mysqli->connect_error . ')');
    
}
?>