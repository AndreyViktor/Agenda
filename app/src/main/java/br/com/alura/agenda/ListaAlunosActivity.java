package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.Adapter.AlunosAdapter;
import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private  ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView)findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Toast.makeText(ListaAlunosActivity.this,"aluno "+aluno.getNome()+" clicado!", Toast.LENGTH_LONG);

                Intent intentVaiParaPerfil = new Intent(ListaAlunosActivity.this,PerfilAlunoActivity.class);
                intentVaiParaPerfil.putExtra("aluno",aluno);
                startActivity(intentVaiParaPerfil);
            }
        });

        Button novoAluno = (Button)findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentVaiParaFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intentVaiParaFormulario);
            }
        });
        registerForContextMenu(listaAlunos);
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        MenuItem editar = menu.add("Editar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                Toast.makeText(ListaAlunosActivity.this,"aluno " + aluno.getNome() + " deletado!",Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.delete(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                Intent editaPerfil = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                editaPerfil.putExtra("aluno",aluno);
                startActivity(editaPerfil);
                return false;
            }
        });
    }
}
