<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Conexão com o banco de dados
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "letcontrol_4";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(array("status" => "error", "mensagem" => "Falha na conexão com o banco de dados: " . $conn->connect_error));
    exit();
}

// Receber os dados do POST
$data = json_decode(file_get_contents("php://input"), true);

// Se não conseguir ler o JSON, tentar ler do POST tradicional
if (empty($data)) {
    $data = $_POST;
}

$nome = isset($data['nome']) ? $data['nome'] : '';
$email = isset($data['email']) ? $data['email'] : '';
$senha = isset($data['senha']) ? $data['senha'] : '';
$estado = isset($data['estado']) ? $data['estado'] : '';

// Validar dados
if (empty($nome) || empty($email) || empty($senha)) {
    http_response_code(400);
    echo json_encode(array("status" => "error", "mensagem" => "Dados incompletos"));
    exit();
}

// Verificar se o email já existe
$stmt = $conn->prepare("SELECT id FROM usuarios WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    http_response_code(409);
    echo json_encode(array("status" => "error", "mensagem" => "Email já cadastrado"));
    $stmt->close();
    $conn->close();
    exit();
}
$stmt->close();

// Iniciar transação para garantir que todas as operações sejam concluídas com sucesso
$conn->begin_transaction();

try {
    // Inserir usuário
    $stmt = $conn->prepare("INSERT INTO usuarios (nome, email, senha, estado) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $nome, $email, $senha, $estado);
    $stmt->execute();
    
    if ($stmt->affected_rows === 0) {
        throw new Exception("Falha ao cadastrar usuário");
    }
    
    $usuario_id = $stmt->insert_id;
    $stmt->close();
    
    // Criar registro inicial de consumo de água (vazio)
    $stmt = $conn->prepare("INSERT INTO consumo_de_agua (vazao_l_min, total_litros, usuario_id) VALUES (0, 0, ?)");
    $stmt->bind_param("i", $usuario_id);
    $stmt->execute();
    $stmt->close();
    
    // Obter o ano atual
    $ano_atual = date('Y');
    
    // Criar relatório anual (vazio)
    $stmt = $conn->prepare("INSERT INTO relatorio_anual (usuario_id, ano, total_litros_ano) VALUES (?, ?, 0)");
    $stmt->bind_param("ii", $usuario_id, $ano_atual);
    $stmt->execute();
    $stmt->close();
    
    // Obter o mês atual
    $mes_atual = date('n');
    
    // Criar relatório mensal (vazio)
    $stmt = $conn->prepare("INSERT INTO relatorio_mensal (usuario_id, ano, mes, total_litros_mes) VALUES (?, ?, ?, 0)");
    $stmt->bind_param("iii", $usuario_id, $ano_atual, $mes_atual);
    $stmt->execute();
    $stmt->close();
    
    // Obter a semana atual
    $semana_atual = date('W');
    
    // Criar relatório semanal (vazio)
    $stmt = $conn->prepare("INSERT INTO relatorio_semanal (usuario_id, ano, semana, total_litros_semana) VALUES (?, ?, ?, 0)");
    $stmt->bind_param("iii", $usuario_id, $ano_atual, $semana_atual);
    $stmt->execute();
    $stmt->close();
    
    // Confirmar todas as operações
    $conn->commit();
    
    http_response_code(201);
    echo json_encode(array(
        "status" => "ok",
        "mensagem" => "Usuário cadastrado com sucesso",
        "usuario_id" => $usuario_id
    ));
    
} catch (Exception $e) {
    // Em caso de erro, desfazer todas as operações
    $conn->rollback();
    
    http_response_code(500);
    echo json_encode(array(
        "status" => "error",
        "mensagem" => "Erro durante o cadastro: " . $e->getMessage()
    ));
}

$conn->close();
?>