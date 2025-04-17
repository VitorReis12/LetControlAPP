package com.example.letcontrol;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ConnectingWifiActivity extends AppCompatActivity {

    TextInputEditText TextInputRede, TextInputSenhaRede;
    Button buttonCadastrarRede;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice letControl;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connecting_wifi);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputRede = findViewById(R.id.TextInputRede);
        TextInputSenhaRede = findViewById(R.id.TextInputSenhaRede);
        buttonCadastrarRede = findViewById(R.id.buttonCadastrarMinhaRede);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        buttonCadastrarRede.setOnClickListener(v -> {
            String nomeRede = TextInputRede.getText().toString();
            String senhaRede = TextInputSenhaRede.getText().toString();

            if (nomeRede.isEmpty() || senhaRede.isEmpty()) {
                Toast.makeText(this, "Preencha ambos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String comando = nomeRede + ";" + senhaRede + "\n";


            if (!bluetoothAdapter.isEnabled()) {
                Intent BtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(BtIntent, 1);
                return;
            }


            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("LetControl")) {
                    letControl = device;
                    break;
                }
            }

            if (letControl == null) {
                Toast.makeText(this, "Dispositivo LetControl não encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    socket = letControl.createRfcommSocketToServiceRecord(uuid);
                    bluetoothAdapter.cancelDiscovery();
                    socket.connect();

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(comando.getBytes());

                    runOnUiThread(() ->
                            Toast.makeText(ConnectingWifiActivity.this, "Comando enviado com sucesso", Toast.LENGTH_SHORT).show()
                    );

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(ConnectingWifiActivity.this, "Erro na conexão: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }).start();
        });
    }

}