<?php
$conn = new mysqli("localhost", "root", "", "letcontrol_2");

$email = $_POST['email'];

$sql = "SELECT nome FROM usuarios WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    echo json_encode(["nome" => $row["nome"]]);
} else {
    echo json_encode(["erro" => "Usuário não encontrado"]);
}