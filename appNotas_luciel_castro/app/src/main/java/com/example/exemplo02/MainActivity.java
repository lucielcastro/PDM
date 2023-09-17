package com.example.exemplo02;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        sharedPrefs = getSharedPreferences("ALUNOS", Context.MODE_PRIVATE);

        Button saveButton = findViewById(R.id.buttonSave);
        Button deleteButton = findViewById(R.id.buttonDelete);
        Button gradeButton = findViewById(R.id.buttonGrade);

        gradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verNotas(view);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar(view);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remover(view);
            }
        });
    }

    public void salvar(View view){
        EditText nome = findViewById(R.id.nome);
        EditText nota1 = findViewById(R.id.nota1);
        EditText nota2 = findViewById(R.id.nota2);
        EditText nota3 = findViewById(R.id.nota3);

        String name = nome.getText().toString();
        String grade1 = nota1.getText().toString();
        String grade2 = nota2.getText().toString();
        String grade3 = nota3.getText().toString();

        if (name.equals("")) {
            Toast.makeText(context, "Digite um nome", Toast.LENGTH_SHORT).show();
            return;
        }

        if (grade1.equals("") || grade2.equals("") || grade3.equals("")) {
            Toast.makeText(context, "Digite todas as notas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Salva os dados em SharedPreferences
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(name + "_nota1", grade1);
        editor.putString(name + "_nota2", grade2);
        editor.putString(name + "_nota3", grade3);
        editor.apply();

        Toast.makeText(context, "Notas Salvas para: " + name, Toast.LENGTH_SHORT).show();
    }

    public void remover(View view) {
        // Inflar o layout do diálogo personalizado
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_aluno, null);

        // Pega o texto digitado na barra de texto para buscar o aluno
        EditText editTextName = dialogView.findViewById(R.id.editTextName);

        // Cria a tela de notificação para digitar o nome do aluno
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String alunoToDelete = editTextName.getText().toString();

                        // Verificaando se o aluno existe e se existe remover suas notas
                        if (sharedPrefs.contains(alunoToDelete + "_nota1")) {
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.remove(alunoToDelete + "_nota1");
                            editor.remove(alunoToDelete + "_nota2");
                            editor.remove(alunoToDelete + "_nota3");
                            editor.apply();
                            Toast.makeText(context, "Aluno Removido: " + alunoToDelete, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Aluno não encontrado: " + alunoToDelete, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Apenas fecha o pop-up
                    }
                });

        // Mostrar o AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void verNotas(View view) {
        Intent intent = new Intent(this, NotasSalvas.class);
        startActivity(intent);
    }
}