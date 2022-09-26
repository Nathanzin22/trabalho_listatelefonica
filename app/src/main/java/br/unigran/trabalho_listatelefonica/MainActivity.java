package br.unigran.trabalho_listatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.unigran.trabalho_listatelefonica.db.DBHelper;
import br.unigran.trabalho_listatelefonica.db.TelefoneDB;
import br.unigran.trabalho_listatelefonica.entidades.Telefone;

public class MainActivity extends AppCompatActivity {
    Boolean verificarEdicao;

    TelefoneDB telefoneDB;
    Telefone telefone;

    EditText textNome;
    EditText textTelefone;
    EditText textData_Nascimento;
    Button botaoSalvar;

    ListView listaDados;
    List<Telefone> listaTelefonica;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(MainActivity.this);
        telefoneDB = new TelefoneDB(db);

        textNome = findViewById(R.id.textNome);
        textTelefone = findViewById(R.id.textTelefone);
        textData_Nascimento = findViewById(R.id.textData_Nascimento);
        botaoSalvar = findViewById(R.id.salvar);
        listaDados = findViewById(R.id.listaTelefones);

        listaTelefonica = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaTelefonica);

        listaDados.setAdapter(adapter);
        telefoneDB.listar(listaTelefonica);

        verificarEdicao = false;

        acaoComponentes();
    }

    private void acaoComponentes() {
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textNome.getText().toString().isEmpty() || textTelefone.getText().toString().isEmpty() || textData_Nascimento.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Dados Inválidos!", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificarEdicao == false) {
                        telefone = new Telefone();
                    }

                    telefone.setNome(textNome.getText().toString());
                    telefone.setTelefone(textTelefone.getText().toString());
                    telefone.setData_nascimento(textData_Nascimento.getText().toString());

                    if (verificarEdicao) {
                        telefoneDB.editar(telefone);

                        Toast.makeText(MainActivity.this, "Editado com Sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        telefoneDB.inserir(telefone);

                        Toast.makeText(MainActivity.this, "Salvo com Sucesso!", Toast.LENGTH_LONG).show();
                    }

                    telefoneDB.listar(listaTelefonica);
                    adapter.notifyDataSetChanged();

                    telefone = null;
                    verificarEdicao = false;
                    textNome.setText("");
                    textTelefone.setText("");
                    textData_Nascimento.setText("");
                }
            }
        });

        listaDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Selecione uma Opção:")
                        .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                verificarEdicao = true;

                                telefone = new Telefone();
                                telefone.setId(listaTelefonica.get(i).getId());

                                textNome.setText(listaTelefonica.get(i).getNome());
                                textTelefone.setText(listaTelefonica.get(i).getTelefone());
                                textData_Nascimento.setText(listaTelefonica.get(i).getData_nascimento());
                            }
                        })
                        .setNegativeButton("Remover", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                new AlertDialog.Builder(view.getContext())
                                        .setMessage("Deseja realmente remover?")
                                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int k) {
                                                telefoneDB.remover(listaTelefonica.get(i).getId());

                                                telefoneDB.listar(listaTelefonica);
                                                adapter.notifyDataSetChanged();

                                                Toast.makeText(MainActivity.this, "Removido com Sucesso!", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("Cancelar", null)
                                        .create().show();
                            }
                        })
                        .create().show();

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        telefone = null;
        verificarEdicao = false;

        textNome.setText("");
        textTelefone.setText("");
        textData_Nascimento.setText("");

        Toast.makeText(MainActivity.this, "Operação cancelada!", Toast.LENGTH_LONG).show();
    }
}