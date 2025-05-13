<?php
session_start();
// Inclui a conexão com o BD
include 'db.php';
// Recebendo os dados via GET

$user = trim(strip_tags($_GET['user'] ?? ''));

$vazao = floatval($_GET['vazao'] ?? 0);
$litros = floatval($_GET['litros'] ?? 0);
$pressao = floatval($_GET['pressao'] ?? 0); // Se não usar, pode remover

if (!$user || $vazao === 0 && $litros === 0) {
    die("Parâmetros incompletos ou inválidos.");}

// Busca o ID do usuário
$result = $mysqli->query("SELECT id FROM usuarios WHERE nome = '$user'");
if ($result->num_rows == 0) {
    die("Usuário não encontrado.");
}
$row = $result->fetch_assoc();
$usuario_id = $row['id'];
// Insere os dados no banco
$sql = "INSERT INTO consumo_de_agua (usuario_id, vazao_l_min, total_litros, pressao)
        VALUES ($usuario_id, $vazao, $litros, $pressao)";

if ($mysqli->query($sql)) {
    echo "Dados gravados com sucesso!";
    echo "<br>Usuário: $user";
    echo "<br>Vazão: $vazao";
    echo "<br>Litros: $litros";
    echo "<br>Pressão: $pressao";
} else {
    die("Erro ao inserir dados: " . $mysqli->error);
}

$mysqli->close();
?>
