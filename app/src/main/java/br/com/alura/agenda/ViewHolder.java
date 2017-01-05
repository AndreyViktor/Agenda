package br.com.alura.agenda;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andrey on 06/07/2016.
 */
public class ViewHolder {
    public TextView campoNome;
    public TextView campoTelefone;
    public ImageView campoFoto;

    public ViewHolder(View view){
        campoNome = (TextView) view.findViewById(R.id.list_nome);
        campoTelefone = (TextView)view.findViewById(R.id.list_telefone);

        campoFoto = (ImageView) view.findViewById(R.id.list_foto);
    }
}
