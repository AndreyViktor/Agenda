package br.com.alura.agenda.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by andrey on 05/06/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(Context context) {
        super(context, "alunos", null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, caminhoFoto TEXT, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="";
        switch(oldVersion) {
            case 4:
            sql = "ALTER TABLE alunos ADD COLUMN caminhoFoto TEXT";
            db.execSQL(sql);
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(aluno);


        db.insert("alunos",null,dados);
    }

    @NonNull
    private ContentValues getContentValues(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome",aluno.getNome());
        dados.put("endereco",aluno.getEndereco());
        dados.put("telefone",aluno.getTelefone());
        dados.put("site",aluno.getSite());
        dados.put("nota",aluno.getNota());
        dados.put("caminhoFoto",aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM alunos",null);
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public void delete(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(aluno.getId())};
        db.delete("alunos","id=?",args);

        }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(aluno);

        String[] args = {String.valueOf(aluno.getId())};
        db.update("alunos",dados,"id=?",args);

    }
}
