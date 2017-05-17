package com.example.nextu.reconocercontactos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Next University
 */
public class Adapter extends BaseAdapter {

    private final Context context;
    private final List<Contacto> contactos;


    public Adapter(Context context, List<Contacto> contactos) {
        this.context = context;
        this.contactos = contactos;
    }


    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int i) {
        return contactos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder = new ViewHolder();

        if (row == null)
            row = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        holder.texto1 = (TextView) row.findViewById(R.id.text1);
        holder.texto2 = (TextView) row.findViewById(R.id.text2);
        holder.linearLayout = (LinearLayout) row.findViewById(R.id.item_listview);

        final Contacto persona = contactos.get(position);

        holder.texto1.setText(persona.getNombre());
        holder.texto2.setText(persona.getNumero());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Contacto selecionado: \n"
                        + persona.getNombre()
                        + "\n"
                        + persona.getNumero()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        return row;
    }

    private class ViewHolder {
        public TextView texto1;
        public TextView texto2;
        public LinearLayout linearLayout;
    }

}


