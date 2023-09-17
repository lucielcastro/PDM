package com.example.exemplo02;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;

public class NotasSalvas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_salvas);


        // Recupera o componente de visualização (textViewListaNotas)
        TextView textViewListaNotas = findViewById(R.id.textViewListaNotas);

        //Pega todas as notas salvas e coloca elas em um lista
        StringBuilder notasBuilder = new StringBuilder();

        //Criação do botão de editar
        Button buttonEdit = findViewById(R.id.buttonEdit);

        // Função que faz a chamada da tela MainActivity quando clico no botão de editar
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotasSalvas.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Função que percorre todas as notas salvas e mostra elas em uma tela
        SharedPreferences sharedPrefs = getSharedPreferences("ALUNOS", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPrefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String[] parts = key.split("_"); // Divida a chave em partes
            if (parts.length == 2 && parts[1].startsWith("nota")) {
                String nota = entry.getValue().toString();
                notasBuilder.append(key).append(": ").append(nota).append("\n");
            }
        }

        // Define o texto no componente de visualização
        textViewListaNotas.setText(notasBuilder.toString());

    }
}

