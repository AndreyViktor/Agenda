package br.com.alura.agenda.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.ViewHolder;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by andrey on 03/07/2016.
 */
public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;
    private ViewHolder holder;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.campoNome.setText(aluno.getNome());

        holder.campoTelefone.setText(aluno.getTelefone());

        String caminhoFoto=aluno.getCaminhoFoto();

        if(caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            //Bitmap bitmapReduzido = aluno.getBitmapReduzido();
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            holder.campoFoto.setImageBitmap(bitmapReduzido);
            holder.campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
