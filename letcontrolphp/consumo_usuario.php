<?php
header('Content-Type: application/json; charset=utf-8');
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);

$servername = "localhost";
$username = "root"; // Altere se necessário
$password = "";     // Altere se necessário
$dbname = "letcontrol_4";

$response = [];

try {
    // Conectar ao banco
    $conn = new mysqli($servername, $username, $password, $dbname);
    $conn->set_charset("utf8mb4");

    // Verificar se o email foi enviado
    if (!isset($_GET['email'])) {
        throw new Exception("Parâmetro 'email' é obrigatório.");
    }

    $email = $_GET['email'];

    // Buscar o ID do usuário com base no email
    $stmtUser = $conn->prepare("SELECT id FROM usuarios WHERE email = ?");
    $stmtUser->bind_param("s", $email);
    $stmtUser->execute();
    $resultUser = $stmtUser->get_result();

    if ($resultUser->num_rows === 0) {
        throw new Exception("Usuário não encontrado.");
    }

    $usuario = $resultUser->fetch_assoc();
    $usuario_id = intval($usuario['id']);

    // Total geral
    $stmt1 = $conn->prepare("SELECT SUM(total_litros) AS total_agua FROM consumo_de_agua WHERE usuario_id = ?");
    $stmt1->bind_param("i", $usuario_id);
    $stmt1->execute();
    $result1 = $stmt1->get_result()->fetch_assoc();

    // Total mensal
    $stmt2 = $conn->prepare("SELECT SUM(total_litros_mes) AS total_mes FROM relatorio_mensal WHERE usuario_id = ?");
    $stmt2->bind_param("i", $usuario_id);
    $stmt2->execute();
    $result2 = $stmt2->get_result()->fetch_assoc();

    // Total anual
    $stmt3 = $conn->prepare("SELECT SUM(total_litros_ano) AS total_ano FROM relatorio_anual WHERE usuario_id = ?");
    $stmt3->bind_param("i", $usuario_id);
    $stmt3->execute();
    $result3 = $stmt3->get_result()->fetch_assoc();

    // Resposta JSON
    $response['success'] = true;
    $response['total_litros'] = floatval($result1['total_agua'] ?? 0);
    $response['total_litros_mes'] = floatval($result2['total_mes'] ?? 0);
    $response['total_litros_ano'] = floatval($result3['total_ano'] ?? 0);

} catch (Exception $e) {
    $response['success'] = false;
    $response['error'] = $e->getMessage();
}

echo json_encode($response);
