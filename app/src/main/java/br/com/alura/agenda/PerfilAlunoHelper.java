package br.com.alura.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by andrey on 09/06/2016.
 */
public class PerfilAlunoHelper {
    private TextView campoNome;
    private TextView campoEndereco;
    private TextView campoTelefone;
    private TextView campoSite;
    private RatingBar campoNota;
    private ImageView campoFoto;
    private Aluno aluno;

    public PerfilAlunoHelper(PerfilAlunoActivity activity){
        this.campoNome = (TextView) activity.findViewById(R.id.Nome_Perfil);
        this.campoEndereco = (TextView) activity.findViewById(R.id.Endereco_Perfil);
        this.campoTelefone = (TextView) activity.findViewById(R.id.Telefone_Perfil);
        this.campoSite = (TextView) activity.findViewById(R.id.Site_Perfil);
        this.campoNota = (RatingBar)activity.findViewById(R.id.Nota_Perfil);
        this.campoFoto = (ImageView)activity.findViewById(R.id.Perfil_foto);
        aluno = new Aluno();
    }

    public void preenchePerfil(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoNota.setProgress((int) aluno.getNota());
        campoEndereco.setText("endereço: " + aluno.getEndereco());
        campoTelefone.setText("telefone: "+ aluno.getTelefone());
        campoSite.setText("Site: "+ aluno.getSite());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno= aluno;
    }
    public void carregaImagem(String caminhoFoto) {
        if(caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
