package com.example.letcontrol;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
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

    BluetoothAdapter bluetoothAdapter, adapter;
    BluetoothSocket socket;
    BluetoothDevice letControl = null;

    String nomeRede, senhaRede, comando, comand;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @RequiresApi(api = Build.VERSION_CODES.S)
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

            nomeRede = TextInputRede.getText().toString();
            senhaRede = TextInputSenhaRede.getText().toString();

            if (nomeRede.isEmpty() || senhaRede.isEmpty()) {
                Toast.makeText(this, "Preencha ambos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("cadastro", MODE_PRIVATE);
            String email = prefs.getString("email", null);

            String user = User.getEmail();

            comando = "rede=" + nomeRede + ";" + "senha=" + senhaRede + ";" + "user=" + email + "\n";
            comand = "rede=Homi 2G" + ";" + "senha=C0rn37_0g" + ";" + "user=" + "guh.santoshenri@gmail.com" + "\n";

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.BLUETOOTH_CONNECT
                        },
                        1);
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Ative o Bluetooth e conecte ao dispositivo LetControl", Toast.LENGTH_LONG).show();
                return;
            }

            if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().equals("LetControl")) {
                        letControl = device;

                    }
                }
            }

            if (letControl == null) {
                Toast.makeText(getApplicationContext(), "Dispositivo LetControl não encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                return;
            }

            new Thread(() -> {
                try {
                    socket = letControl.createRfcommSocketToServiceRecord(uuid);
                    bluetoothAdapter.cancelDiscovery();
                    socket.connect();

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(comando.getBytes());
                    outputStream.flush();

                    // Fecha o socket antes de tocar na UI
                    if (socket != null && socket.isConnected()) {
                        socket.close();
                    }

                    // Agora sim, toque na UI com segurança
                    runOnUiThread(() -> {
                        if (!isFinishing()) {
                            Toast.makeText(getApplicationContext(), "Comando enviado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        if (!isFinishing()) {
                            Toast.makeText(getApplicationContext(), "Erro na conexão", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).start();

        });

    }
}