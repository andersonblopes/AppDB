package com.example.ander.appdb;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText etxNome, etxEndereco, etxEmpresa;
    private Button btnCadastrar, btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dbHelper = new DBHelper(this);

        etxNome = (EditText) findViewById(R.id.etxNome);
        etxEndereco = (EditText) findViewById(R.id.etxEndereco);
        etxEmpresa = (EditText) findViewById(R.id.etxEmpresa);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnListar = (Button) findViewById(R.id.btnListar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etxNome.getText().length() > 0 && etxEndereco.getText().length() > 0 && etxEmpresa.getText().length() > 0) {
                    dbHelper.insert(etxNome.getText().toString(), etxEndereco.getText().toString(), etxEmpresa.getText().toString());
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Sucesso!");
                    adb.setMessage("Registro inserido com sucesso!");
                    adb.show();
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Erro!");
                    adb.setMessage("Todos os campos devem ser preenchidos!");
                    adb.show();
                }
                limparCampos();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Contato> contatos = dbHelper.queryGetAll();
                if (contatos == null) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Aviso!");
                    adb.setMessage("Não há contatos cadastrados!");
                    adb.show();
                    return;
                }
                for (Contato c : contatos) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Contato");
                    adb.setMessage(c.imprimir());
                    adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                }
            }
        });
    }

    public void limparCampos() {
        etxNome.setText("");
        etxEndereco.setText("");
        etxEmpresa.setText("");
        etxNome.findFocus();
    }

}
