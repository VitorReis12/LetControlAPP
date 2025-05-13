<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "letcontrol_2";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) die("Erro de conexão");

$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';

// Busca a senha direto (sem password_verify), assumindo que ela está em texto plano
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
?>
