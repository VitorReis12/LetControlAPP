<?php
$conn = new mysqli("localhost", "usuario", "senha", "banco");

$id = $_POST['id'];
$nome = $_POST['nome'];

$email = $_POST['email'];

$sql = "UPDATE usuarios SET nome='$nome', email='$email' WHERE id=$id";

if ($conn->query($sql) === TRUE) {
    echo "Atualizado";
} else {
    echo "Erro";
}
?>