<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "letcontrol_4";


$conn = new mysqli($host, $user, $pass, $db);

if ($conn->connect_error) {
    die("Erro: " . $conn->connect_error);
}

$email = $_GET['email']; // recebido do app

// Pega o ID do usuário pelo email
$sql = "SELECT id FROM usuarios WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$res = $stmt->get_result();

if ($user = $res->fetch_assoc()) {
    $usuario_id = $user['id'];

    // Pega os dados da tabela consumo com base no usuario_id
    $sql2 = "SELECT * FROM consumo_de_agua WHERE usuario_id = ?";
    $stmt2 = $conn->prepare($sql2);
    $stmt2->bind_param("i", $usuario_id);
    $stmt2->execute();
    $res2 = $stmt2->get_result();

    $dados = array();
    while ($row2 = $res2->fetch_assoc()) {
        $dados[] = $row2;
    }

    echo json_encode($dados);
} else {
    echo json_encode(["erro" => "Usuário não encontrado"]);
}

$conn->close();
?>