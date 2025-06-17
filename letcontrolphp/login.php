<?php
header("Content-Type: application/json; charset=utf-8");

$servername = "localhost";
$username = "root";

$password = "";
$dbname = "letcontrol_4";

$conn = new mysqli($servername, $username, $password, $dbname);
$conn->set_charset("utf8mb4");

if ($conn->connect_error) {
    die(json_encode(["status" => "erro", "mensagem" => "Erro de conexão: " . $conn->connect_error]));
}

$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';

// Consulta usuário com email e senha em texto plano (não recomendado em produção)
$sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $email, $senha);
$stmt->execute();
$result = $stmt->get_result();
$user = $result->fetch_assoc();

if ($user) {
    echo json_encode(["status" => "ok", "mensagem" => "Login bem-sucedido."]);
} else {
    echo json_encode(["status" => "erro", "mensagem" => "Credenciais inválidas."]);
}

$conn->close();
