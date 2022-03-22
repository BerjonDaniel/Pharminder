package com.example.farmahomie;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

    public class EditActivity extends AppCompatActivity {

        private EditText mTitleText;
        private EditText mBodyText;
        private Long mRowId;
        private NotesDbAdapter dbAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // infla el layout
            setContentView(R.layout.activity_edit);

            // obtiene referencia a los tres views que componen el layout
            mTitleText = (EditText) findViewById(R.id.title);
            mBodyText = (EditText) findViewById(R.id.body);
            Button confirmButton = (Button) findViewById(R.id.confirm);

            //creamos el adaptador de la BD y la abrimos
            dbAdapter = new NotesDbAdapter(this);
            dbAdapter.open();

            // obtiene id de fila de la tabla si se le ha pasado (hemos pulsado una nota para editarla)
            mRowId = (savedInstanceState == null) ? null :
                    (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
            if (mRowId == null) {
                Bundle extras = getIntent().getExtras();
                mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID) : null;
            }

            // Si se le ha pasado un id (no era null) rellena el título y el cuerpo con los campos guardados en la BD
            // en caso contrario se dejan en blanco (editamos una nota nueva)
            if (mRowId != null) {
                Cursor note = dbAdapter.fetchNote(mRowId);
                mTitleText.setText(note.getString(
                        note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
                mBodyText.setText(note.getString(
                        note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Se recrea el menu que aparece en ActionBar de la actividad.
            getMenuInflater().inflate(R.menu.menu_edit, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Gestiona la seleccion de opciones en el menú
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                if (mRowId != null) {
                    dbAdapter.deleteNote(mRowId);
                }
                setResult(RESULT_OK);
                dbAdapter.close();
                finish();
            }

            if (id == R.id.action_about) {
                System.out.println("APPMOV: About action...");
            }

            return super.onOptionsItemSelected(item);
        }


        public void saveNote(View view) {
            String title = mTitleText.getText().toString();
            String body = mBodyText.getText().toString();

            if (mRowId == null) {
                long id = dbAdapter.createNote(title, body);
                if (id > 0) {
                    mRowId = id;
                }
            } else {
                dbAdapter.updateNote(mRowId, title, body);
            }
            setResult(RESULT_OK);
            dbAdapter.close();
            finish();
        }

    }

