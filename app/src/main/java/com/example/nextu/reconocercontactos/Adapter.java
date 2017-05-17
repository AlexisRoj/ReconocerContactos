package com.example.nextu.reconocercontactos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alexis Rojas
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

    /** Instancia todos los elementos y los asigna*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder = new ViewHolder();

        if (row == null)
            row = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        holder.texto1 = (TextView) row.findViewById(R.id.text1);
        holder.texto2 = (TextView) row.findViewById(R.id.text2);
        holder.linearLayout = (LinearLayout) row.findViewById(R.id.item_listview);
        holder.checkbox = (CheckBox) row.findViewById(R.id.checkbox);

        final Contacto persona = contactos.get(position);

        holder.texto1.setText(persona.getNombre());
        holder.texto2.setText(persona.getNumero());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            /***
             * Si no esta marcado lo marca
             * */
            @Override
            public void onClick(View v) {
                if (!holder.checkbox.isChecked()) {
                    holder.checkbox.setChecked(true);
                    Toast.makeText(context, "Contacto selecionado: \n"
                                    + persona.getNombre()
                                    + "\n"
                                    + persona.getNumero()
                            , Toast.LENGTH_SHORT).show();
                }else
                    holder.checkbox.setChecked(false);
            }
        });

        return row;
    }

    /** Clase holder que instancia los elementos*/
    private class ViewHolder {
        private TextView texto1;
        private TextView texto2;
        private LinearLayout linearLayout;
        private CheckBox checkbox;
    }

}


