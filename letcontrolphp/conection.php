<?php

session_start();

header("Content-Type: application/json");
file_put_contents("logconx.txt", json_encode($_GET) . PHP_EOL, FILE_APPEND);

// Conexão com o banco
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "letcontrol_2";
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["status" => "erro", "mensagem" => "Erro na conexão com o banco."]));
}

// Recebendo dados via GET
$user = $_GET['user'] ?? '';
$vazao = $_GET['vazao'] ?? null;
$litros = $_GET['litros'] ?? null;

if (!$user || $vazao === null || $litros === null) {
    die(json_encode(["status" => "erro", "mensagem" => "Parâmetros incompletos."]));
}

// Busca o ID do usuário
$sql_user = "SELECT id FROM usuarios WHERE email = ?";
$stmt = $conn->prepare($sql_user);
if (!$stmt) {
    die(json_encode(["status" => "erro", "mensagem" => "Erro no prepare() SELECT: " . $conn->error]));
}
$stmt->bind_param("s", $user);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows == 0) {
    die(json_encode(["status" => "erro", "mensagem" => "Usuário não encontrado."]));
}

$usuario = $result->fetch_assoc();
$usuario_id = $usuario['id'];

// Insere ou atualiza os dados do usuário
$sql_upsert = "INSERT INTO consumo_de_agua (usuario_id, vazao_l_min, total_litros)
               VALUES (?, ?, ?)
               ON DUPLICATE KEY UPDATE 
               vazao_l_min = VALUES(vazao_l_min), 
               total_litros = VALUES(total_litros)";

$stmt_upsert = $conn->prepare($sql_upsert);
if (!$stmt_upsert) {
    die(json_encode(["status" => "erro", "mensagem" => "Erro no prepare() UPSERT: " . $conn->error]));
}
$stmt_upsert->bind_param("idd", $usuario_id, $vazao, $litros);

$ok = $stmt_upsert->execute();

if ($ok) {
    echo json_encode(["status" => "ok", "mensagem" => "Dados atualizados com sucesso."]);
} else {
    echo json_encode([
        "status" => "erro",
        "mensagem" => "Erro ao atualizar os dados.",
        "erro_sql" => $stmt_upsert->error
    ]);
}

$conn->close();
?>
