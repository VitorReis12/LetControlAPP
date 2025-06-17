const dados = [
    { data: "01/05", consumo: 120 },
    { data: "02/05", consumo: 135 },
    { data: "03/05", consumo: 150 },
    { data: "04/05", consumo: 145 },
    { data: "05/05", consumo: 160 },
    { data: "06/05", consumo: 170 },
  ];
  
  // Preenche a tabela
  const tabela = document.getElementById("tabela-dados");
  dados.forEach(d => {
    const linha = `<tr><td>${d.data}</td><td>${d.consumo}</td></tr>`;
    tabela.innerHTML += linha;
  });
  
  // Cria o gráfico
  const ctx = document.getElementById("graficoConsumo").getContext("2d");
  new Chart(ctx, {
    type: 'line',
    data: {
      labels: dados.map(d => d.data),
      datasets: [{
        label: 'Consumo de Água (L)',
        data: dados.map(d => d.consumo),
        backgroundColor: 'rgba(0, 119, 204, 0.2)',
        borderColor: '#0077cc',
        borderWidth: 2,
        fill: true,
        tension: 0.3
      }]
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
  