package com.example.farmahomie;

/*
 * Asignatura Aplicaciones Moviles - UC3M
 * Update: 13/02/2018.
 *
 * Based in code by Google with Apache License, Version 2.0
 *
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class NotepadActivity extends AppCompatActivity {

    private com.example.farmahomie.NotesDbAdapter dbAdapter;
    private ListView m_listview;

    // para indicar en un Intent si se quiere crear una nueva nota o editar una existente
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflamos el layout
        setContentView(R.layout.activity_notepad);

        //creamos el adaptador de la BD y la abrimos
        dbAdapter = new com.example.farmahomie.NotesDbAdapter(this);
        dbAdapter.open();

        // Creamos un listview que va a contener el título de todas las notas y
        // en el que cuando pulsemos sobre un título lancemos una actividad de editar
        // la nota con el id correspondiente
        m_listview = (ListView) findViewById(R.id.id_list_view);
        m_listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
                    {
                        Intent i = new Intent(view.getContext(), com.example.farmahomie.EditActivity.class);
                        i.putExtra(com.example.farmahomie.NotesDbAdapter.KEY_ROWID, id);
                        startActivityForResult(i, ACTIVITY_EDIT);
                    }
                }
        );

        // rellenamos el listview con los títulos de todas las notas en la BD
        fillData();
    }

    private void fillData() {
        Cursor notesCursor = dbAdapter.fetchAllNotes();

        // Creamos un array con los campos que queremos mostrar en el listview (sólo el título de la nota)
        String[] from = new String[]{com.example.farmahomie.NotesDbAdapter.KEY_TITLE};

        // array con los campos que queremos ligar a los campos del array de la línea anterior (en este caso sólo text1)
        int[] to = new int[]{R.id.text1};

        // Creamos un SimpleCursorAdapter y lo asignamos al listview para mostrarlo
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.notes_row, notesCursor, from, to, 0);
        m_listview.setAdapter(notes);
    }



    // con AppCompatActivity hay que sobreescribir onOptionsItemSelected en lugar de
    // onMenuItemSelected para gestionar el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona la seleccion de opciones en el menú
        int id = item.getItemId();
        if (id == R.id.action_insert) {
            createNote();
            return true;
        }

        if (id == R.id.action_about) {
            System.out.println("APPMOV: About action...");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this,com.example.farmahomie.EditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}