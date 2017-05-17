package com.example.nextu.reconocercontactos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ProgressDialog pDialog;
    private android.os.Handler updateBarHandler;

    ArrayList<Contacto> contactList;

    Cursor cursor;
    int counter=0;
    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISOS = {
            Manifest.permission.READ_CONTACTS
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (leer == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);

        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        contactList = new ArrayList<Contacto>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Leyendo");
        pDialog.setCancelable(false);
        pDialog.show();

        mListView = (ListView) findViewById(R.id.listView);
        updateBarHandler = new android.os.Handler();


        new Thread(new Runnable() {

            @Override
            public void run() {
                getContacts();
            }
        }).start();

    }

    /**
     * Clase encargada de extraer los contactos del telefono
     * */
    public void getContacts() {
        String phoneNumber;
        String email;
        Uri CONTENT_URI= ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER= ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTAC_ID =ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER =ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri emailCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
       // String emailCONTAC_ID =ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String data =ContactsContract.CommonDataKinds.Phone.DATA;

        //StringBuffer output;

        ContentResolver contentResolver = getContentResolver();

        //Incializa el cursor
        cursor = contentResolver.query(CONTENT_URI,null,null,null,null);

        if(cursor.getCount()>0) {

            counter = 0;
            while (cursor.moveToNext()) {
                String nombre = "";
                String numero = "";
                String correo = "";

                updateBarHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.setMessage("Leyendo :" + counter++ + "/" + cursor.getCount());
                    }
                });
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt
                        (cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    nombre = name;

                    Cursor phoneCursor = contentResolver.query
                            (PhoneCONTENT_URI, null, Phone_CONTAC_ID + "=?",
                                    new String[]{contact_id}, null);

                    /**
                     * Extrae el número
                     * */
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        numero = phoneNumber;
                    }
                    phoneCursor.close();
                    /**
                     * Extrae el email
                     * */

                    Cursor emailCursor = contentResolver.query
                            (emailCONTENT_URI, null, Phone_CONTAC_ID + "=?"
                                    , new String[]{contact_id}, null);

                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(data));
                        correo = email;
                    }
                    emailCursor.close();

                }
                //Crea la lista de contactos
                contactList.add(new Contacto(nombre, numero, correo));
            }



            runOnUiThread(new Runnable() {
                /** Inicializa el hilo y carga el adapter*/
                @Override
                public void run() {
                    Adapter adapter = new Adapter(MainActivity.this, contactList);
                    mListView.setAdapter(adapter);
                }
            });

            updateBarHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.cancel();
                }
            }, 500);

        }

    }

}