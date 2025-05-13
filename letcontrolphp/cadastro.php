<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "letcontrol_2";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(["status" => "erro", "mensagem" => "Conexão falhou: " . $conn->connect_error]));
}

$nome = $_POST['nome'] ?? '';
$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';
$estado = $_POST['estado'] ?? '';

if (!$nome || !$email || !$senha) {
    die(json_encode(["status" => "erro", "mensagem" => "Dados incompletos."]));
}



$conn->begin_transaction();

try {
    // 1. Inserir na tabela consumo_de_agua
    $conn->query("INSERT INTO consumo_de_agua (vazao_l_min, total_litros) VALUES (NULL, NULL)");
    $consumo_id = $conn->insert_id;

    // 2. Inserir na tabela relatorio_mensal
    $ano = date('Y');
    $mes = date('n');
    $conn->query("INSERT INTO relatorio_mensal (ano, mes, total_litros_mes) VALUES ($ano, $mes, 0)");
    $relatorio_id = $conn->insert_id;

    // 3. Inserir na tabela usuarios
    $sql = "INSERT INTO usuarios (nome, email, senha, estado, consumo_de_agua_id, relatorio_mensal_id)
            VALUES (?, ?, ?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssssii", $nome, $email, $senha, $estado, $consumo_id, $relatorio_id);
    $stmt->execute();

    $usuario_id = $stmt->insert_id; // ID do novo usuário
    $conn->query("UPDATE consumo_de_agua SET usuario_id = $usuario_id WHERE id = $consumo_id");

    $conn->commit();

    echo json_encode(["status" => "ok", "user" => $email]);

} catch (Exception $e) {
    $conn->rollback();
    echo json_encode(["status" => "erro", "mensagem" => "Erro no cadastro: " . $e->getMessage()]);
}

$conn->close();
?>