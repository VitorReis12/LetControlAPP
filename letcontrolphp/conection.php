<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header('Access-Control-Allow-Headers: Content-Type');

// Database connection
$servername = "127.0.0.1";
$username = "root";
$password = "";
$dbname = "letcontrol_4";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die(json_encode(["status" => "error", "mensagem" => "Falha na conexão: " . $conn->connect_error]));
}

// Get data from GET request
$userEmail = isset($_GET['user']) ? $_GET['user'] : '';
$vazao = isset($_GET['vazao']) ? floatval($_GET['vazao']) : 0;
$litros = isset($_GET['litros']) ? floatval($_GET['litros']) : 0;

if (empty($userEmail)) {
    echo json_encode(["status" => "error", "mensagem" => "Email do usuário não fornecido"]);
    exit;
}

// Get user ID from email
$userId = 0;
$sql = "SELECT id FROM usuarios WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $userEmail);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $userId = $row['id'];
} else {
    echo json_encode(["status" => "error", "mensagem" => "Usuário não encontrado"]);
    exit;
}

$stmt->close();

// Get current date information
$currentDate = new DateTime();
$ano = $currentDate->format('Y');
$mes = $currentDate->format('n');
$semana = $currentDate->format('W');
$dataAtual = $currentDate->format('Y-m-d H:i:s');

try {
    // Start transaction
    $conn->begin_transaction();

    // 1. UPDATE na tabela consumo_de_agua (atualiza o último registro do usuário)
    $sqlConsumo = "UPDATE consumo_de_agua 
                   SET vazao_l_min = ?, total_litros = ?, data_leitura = ?
                   WHERE consumo_de_agua_id = (
                       SELECT MAX(consumo_de_agua_id) 
                       FROM (SELECT * FROM consumo_de_agua) AS temp 
                       WHERE usuario_id = ?
                   )";
    $stmtConsumo = $conn->prepare($sqlConsumo);
    $stmtConsumo->bind_param("ddsi", $vazao, $litros, $dataAtual, $userId);
    $stmtConsumo->execute();
    
    $stmtConsumo->close();

    // 2. UPDATE relatorio_semanal
    $sqlSemanal = "UPDATE relatorio_semanal 
                   SET total_litros_semana = total_litros_semana + ? 
                   WHERE usuario_id = ? AND ano = ? AND semana = ?";
    $stmtSemanal = $conn->prepare($sqlSemanal);
    $stmtSemanal->bind_param("diii", $litros, $userId, $ano, $semana);
    $stmtSemanal->execute();
    
    $stmtSemanal->close();

    // 3. UPDATE relatorio_mensal
    $sqlMensal = "UPDATE relatorio_mensal 
                  SET total_litros_mes = total_litros_mes + ? 
                  WHERE usuario_id = ? AND ano = ? AND mes = ?";
    $stmtMensal = $conn->prepare($sqlMensal);
    $stmtMensal->bind_param("diii", $litros, $userId, $ano, $mes);
    $stmtMensal->execute();
    
    $stmtMensal->close();

    // 4. UPDATE relatorio_anual
    $sqlAnual = "UPDATE relatorio_anual 
                 SET total_litros_ano = total_litros_ano + ? 
                 WHERE usuario_id = ? AND ano = ?";
    $stmtAnual = $conn->prepare($sqlAnual);
    $stmtAnual->bind_param("dii", $litros, $userId, $ano);
    $stmtAnual->execute();
    
    $stmtAnual->close();

    // Commit transaction
    $conn->commit();

    echo json_encode(["status" => "ok", "mensagem" => "Dados atualizados com sucesso"]);
} catch (Exception $e) {
    // Rollback transaction on error
    $conn->rollback();
    echo json_encode(["status" => "error", "mensagem" => "Erro ao atualizar dados: " . $e->getMessage()]);
}

$conn->close();
?>