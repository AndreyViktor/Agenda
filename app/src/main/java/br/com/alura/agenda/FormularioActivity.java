package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.DAO.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    public static final int CODIGO_CAMERA = 666;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        this.helper = new FormularioHelper(this);
        Intent intent =getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno!=null){
            helper.preencheFormulario(aluno);
        }
        Button botaoFoto = (Button) findViewById(R.id.Formulario_foto_botao);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" +System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentFoto, CODIGO_CAMERA);
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);
                if (aluno.getId() != 0) {
                    dao.altera(aluno);
                } else {
                    dao.insere(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this,"Contato " + aluno.getNome() + " Salvo",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CODIGO_CAMERA:
                if(resultCode== Activity.RESULT_OK){
                    helper.carregaImagem(caminhoFoto);
                    //Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    //Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap,300,300,true);

                }
        }
    }
}
