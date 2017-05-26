package com.mic.calculators.voltagedivider.voltagedividercalculatorapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ShareActionProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Tag for debug
    private final String TAG = "MainActivity";

    // fields
    private float vin = 0;
    private float r1 = 0;
    private float r2 = 0;
    private float vout = 0;

    // Components
    private TextView textVout;
    private EditText editVin;
    private EditText editR1;
    private EditText editR2;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        // Inicializando os componentes.
        editVin = (EditText) findViewById(R.id.edit_vin);
        editR1 = (EditText) findViewById(R.id.edit_r1);
        editR2 = (EditText) findViewById(R.id.edit_r2);
        textVout = (TextView) findViewById(R.id.text_vout);


        editVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método executa Antes da mudança de texto.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Método eexecuta durante a mudança de texto.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Método executa depois da mudança de texto.
                try {
                    if (s.length() > 0) {
                        vin = Float.parseFloat(s.toString());
                        calc();
                    }
                } catch (Exception ex) {
                    // Mostra Log durante o Debug.
                    Log.e(TAG, "editVin: Text Changed", ex);
                }
            }
        });

        editR1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método executa Antes da mudança de texto.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Método eexecuta durante a mudança de texto.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Método executa depois da mudança de texto.
                try {
                    if (s.length() > 0) {
                        r1 = Float.parseFloat(s.toString());
                        calc();
                    }
                } catch (Exception ex) {
                    // Mostra Log durante o Debug.
                    Log.e(TAG, "editR1: Text Changed", ex);
                }
            }
        });

        editR2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método executa Antes da mudança de texto.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Método eexecuta durante a mudança de texto.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Método executa depois da mudança de texto.
                try {
                    if (s.length() > 0) {
                        r2 = Float.parseFloat(s.toString());
                        calc();
                    }
                } catch (Exception ex) {
                    // Mostra Log durante o Debug.
                    Log.e(TAG, "editR2: Text Changed", ex);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    editR1.setText(null);
                    editR2.setText(null);
                    editVin.setText(null);
                    textVout.setText(null);
                    Snackbar.make(view, R.string.edit_text_cleared, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (Exception ex) {
                    // Mostra Log durante o Debug.
                    Log.e(TAG, "fab Clean Texts", ex);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Calcula a saída de voltagem.
     */
    private void calc() {
        if(vin == 0 || r1 == 0 || r2 == 0) {
            vout = 0;
        } else {
            vout = r2/(r1 + r2) * vin;
            textVout.setText(String.valueOf(vout));
        }
    }

    /**
     * Método para compartilhar o App escolhendo que meio de "Comunicação" utilizar.
     */
    private void shareApp() {
        // Cria um Intent com a ação de envio.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // Define o tipo de dado a ser enviado
        shareIntent.setType("text/plain");
        // Mensagem a ser enviada
        String shareBodyText = getString(R.string.share_message) + getString(R.string.app_link);
        // Adiciona os Extras ao Intent
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        // Inicia a Activity para escolher o método de envio.
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_options)));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else
        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent("about_filter");
            startActivity(aboutIntent);
        } else if (id == R.id.nav_share) {
            shareApp();
            Log.d(TAG, "Testing Share");
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
