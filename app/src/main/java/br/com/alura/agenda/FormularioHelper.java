package br.com.alura.agenda;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by andrey on 05/06/2016.
 */
public class FormularioHelper {
    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        this.campoNome = (EditText) activity.findViewById(R.id.Formulario_Nome);
        this.campoEndereco = (EditText) activity.findViewById(R.id.Formulario_Endereco);
        this.campoTelefone = (EditText) activity.findViewById(R.id.Formulario_Telefone);
        this.campoSite = (EditText) activity.findViewById(R.id.Formulario_Site);
        this.campoNota = (RatingBar)activity.findViewById(R.id.Formulario_Nota);
        this.campoFoto = (ImageView)activity.findViewById(R.id.Formulario_foto);
        aluno = new Aluno();
    }
    public Aluno pegaAluno(){
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoNota.setProgress((int) aluno.getNota());
        campoEndereco.setText( aluno.getEndereco());
        campoTelefone.setText( aluno.getTelefone());
        campoSite.setText( aluno.getSite());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if(caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            //aluno.setBitmapReduzido(bitmapReduzido);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
