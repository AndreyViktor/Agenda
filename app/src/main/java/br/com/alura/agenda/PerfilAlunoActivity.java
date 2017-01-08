package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.alura.agenda.modelo.Aluno;

public class PerfilAlunoActivity extends AppCompatActivity {

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aluno);

        PerfilAlunoHelper helper = new PerfilAlunoHelper(this);
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        helper.preenchePerfil(aluno);

        TextView nomePerfil = (TextView) findViewById(R.id.Nome_Perfil);
        nomePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilAlunoActivity.this,"sim, meu nome é "+aluno.getNome(),Toast.LENGTH_LONG).show();
               }
        });

        TextView enderecoPerfil = (TextView)findViewById(R.id.Endereco_Perfil);
        enderecoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(Intent.ACTION_VIEW);
                mapa.setData(Uri.parse("geo:0,0?z=14&q="+aluno.getEndereco()));
                startActivity(mapa);
            }
        });

        TextView telefonePerfil = (TextView)findViewById(R.id.Telefone_Perfil);
        telefonePerfil.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           openContextMenu(v);

          }
        });
        TextView SitePerfil = (TextView) findViewById(R.id.Site_Perfil);
        SitePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                String site = aluno.getSite();
                if(!site.startsWith("http://")){
                    site = "http://"+site;
                }
                intentSite.setData(Uri.parse(site));
                startActivity(intentSite);
            }
        });

        registerForContextMenu(telefonePerfil);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem sms = menu.add("mandar sms");
        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent mandaSms = new Intent(Intent.ACTION_VIEW);
                mandaSms.setData(Uri.parse("sms:"+aluno.getTelefone()));
                startActivity(mandaSms);
                return false;
            }
        });
        MenuItem ligar = menu.add("ligar para "+aluno.getNome());
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(PerfilAlunoActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PerfilAlunoActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);
                }else {
                    Intent phoneCall = new Intent(Intent.ACTION_CALL);
                    phoneCall.setData(Uri.parse("tel:"+aluno.getTelefone()));
                    startActivity(phoneCall);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_perfil_editar:
                Intent vaiParaFormulario = new Intent(this,FormularioActivity.class);
                vaiParaFormulario.putExtra("aluno",aluno);
                startActivity(vaiParaFormulario);
        }
        return super.onOptionsItemSelected(item);
    }

}
