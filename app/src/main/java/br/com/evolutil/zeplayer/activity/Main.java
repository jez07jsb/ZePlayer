package br.com.evolutil.zeplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import br.com.evolutil.zeplayer.R;
import br.com.evolutil.zeplayer.filebrowser.FileSelectedListener;
import br.com.evolutil.zeplayer.filebrowser.Filebrowser;
import br.com.evolutil.zeplayer.notification.NotificationUtil;
import br.com.evolutil.zeplayer.player.InterfaceMp3;
import br.com.evolutil.zeplayer.player.Mp3Service;

public class Main extends AppCompatActivity {

    private static final String TAG = "Main";
    private EditText text;
    private ImageButton playBt, stopBt, searchBt;
    private SeekBar seekBar;
    private int position;
    // Objeto que encapsula o MediaPlayer
    private InterfaceMp3 interfaceMp3;
    private ServiceConnection conexao = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Recupera a interface para interagir com o servico
            Mp3Service.Mp3ServiceBinder conexao = (Mp3Service.Mp3ServiceBinder) service;
            interfaceMp3 = conexao.getInterface();
            Log.i(TAG, "onServiceConnected, interfaceMp3 conectada: " + interfaceMp3);

            // Recupera informacoes para a tela
            if (interfaceMp3.isPlaying()) {
                String mp3 = interfaceMp3.getMp3();
                getIntent().putExtra("LAST_PLAYED", mp3);

                position = interfaceMp3.getPosition();

                searchBt.setEnabled(false);

                String mp3Name = getMP3Name(mp3);
                text.setText(mp3Name);
                text.setEnabled(false);

                if (interfaceMp3.isPaused()) {
                    actionPlay(mp3);
                } else {

                    playBt.setImageResource(android.R.drawable.ic_media_pause);
                    getIntent().putExtra("BT_PLAY", false);
                }

                seekBar.setEnabled(true);
                seekBar.setMax(interfaceMp3.getDuration());
                updateSeekBar();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected, liberando recursos");

            if (interfaceMp3 != null && interfaceMp3.isPlaying()) {
                NotificationUtil.cancelAll(Main.this);
            }

            interfaceMp3 = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText) findViewById(R.id.editText_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar_main);
        playBt = (ImageButton) findViewById(R.id.play_pause_FB_main);
        stopBt = (ImageButton) findViewById(R.id.stopFB_main);
        searchBt = (ImageButton) findViewById(R.id.searchFB_main);

        // Set toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // Set menu icon.
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.app_name);
        }

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String mp3 = text.getText().toString();

                if (checkMP3(mp3)) {
                    getIntent().putExtra("LAST_PLAYED", mp3);
                }
                return false;
            }
        });

        playBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interfaceMp3 != null) {
                    String mp3 = getIntent().getStringExtra("LAST_PLAYED");

                    if (!checkMP3(mp3)) {
                        return;
                    }

                    if (getIntent().getBooleanExtra("BT_PLAY", true)) {
                        if (interfaceMp3.isPlaying()) {
                            actionPlay(mp3);
                        } else {
                            actionPlay(mp3);
                            searchBt.setEnabled(false);

                            text.setText(getMP3Name(mp3));
                            text.setEnabled(false);

                            seekBar.setEnabled(true);
                            seekBar.setMax(interfaceMp3.getDuration());
                            updateSeekBar();
                        }
                    } else {
                        actionPause();
                    }
                }
            }
        });

        stopBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interfaceMp3 != null) {
                    actionStop();
                }
            }
        });

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File ftemp = new File("/storage/extSdCard/");
                Filebrowser fileBrowserSearch = new Filebrowser(Main.this, ftemp);
                fileBrowserSearch.setFileEndsWith(".mp3");

                fileBrowserSearch.addFileListener(new FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        try {
                            String mp3 = file.getPath();
                            text.setText(mp3);
                            getIntent().putExtra("LAST_PLAYED", mp3);
                        } catch (Exception e) {
                            Log.e(TAG, "File Browser ERROR\n" + e.getMessage(), e);
                        }
                    }
                });

                fileBrowserSearch.showDialog();
            }
        });

        seekBar.setProgress(position);
        seekBar.setEnabled(false);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    position = progress;
                    interfaceMp3.setPosition(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Intent intent = new Intent(this, Mp3Service.class);
        Log.i(TAG, "Iniciando o Service");
        startService(intent);
        // Faz o bind, ligacao
        boolean b = bindService(intent, conexao, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "Service conectado: " + b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.configurar:
                createToast("Clicado configurar");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (interfaceMp3 != null && interfaceMp3.isPlaying()) {

            Log.i(TAG, "Activity destruida, Service continua a tocar a musica.");
            unbindService(conexao);
            // Cria notificacao para abrir Activity do player
            String mp3 = interfaceMp3.getMp3();
            Intent intent = new Intent(this, Main.class);
            NotificationUtil.create(
                    this,
                    intent,
                    R.mipmap.ic_launcher,
                    "ZePlayer",
                    getMP3Name(mp3),
                    1,
                    false
            );

        } else {

            Log.i(TAG, "Activity destruida. Service parado, musica inexistente.");
            unbindService(conexao);
            Intent intent = new Intent(this, Mp3Service.class);
            stopService(intent);

        }
    }

    // ++++++++++++++++++++++++ SESSAO EXTRA ++++++++++++++++++++++++++++++

    private void actionPlay(String mp3) {
        interfaceMp3.play(mp3);
        playBt.setImageResource(android.R.drawable.ic_media_pause);
        getIntent().putExtra("BT_PLAY", false);
    }

    private void actionPause() {
        interfaceMp3.pause();
        playBt.setImageResource(android.R.drawable.ic_media_play);
        getIntent().putExtra("BT_PLAY", true);
    }

    private void actionStop() {
        if (interfaceMp3.isPlaying()) {
            interfaceMp3.stop();
            playBt.setImageResource(android.R.drawable.ic_media_play);
            getIntent().putExtra("BT_PLAY", true);

            text.setEnabled(true);
            text.setText("");

            searchBt.setEnabled(true);
        }
    }

    private String getMP3Name(String path) {
        if (!(path == null || path.isEmpty())) {
            File neoFile = new File(path);
            String fileMp3Name = neoFile.getName();
            fileMp3Name = fileMp3Name.replaceFirst(".mp3", "");
            return fileMp3Name;
        } else {
            createToast("Erro ao processar \n endereço de arquivo.");
            return ("");
        }
    }

    private boolean checkMP3(String s) {
        if (s == null) {
            createToast("Campo de caminho de mp3 nulo.");
            return false;
        }
        if (s.isEmpty()) {
            createToast("Sem caminho de arquivo.");
            return false;
        }

        File f = new File(s);
        Boolean checkUri = f.exists();
        Boolean checkType = s.matches(".*.mp3");

        if (!checkUri) {
            createToast("Sem arquivo no caminho.");
            return false;
        }

        if (!checkType) {
            createToast("Escolha um arquivo do tipo mp3.");
            return false;
        }

        return true;
    }

    private void createToast(String msg) {
        if (msg != null && !msg.isEmpty()) {
            Toast.makeText(
                    this,
                    msg,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void updateSeekBar() {
        new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                Integer p = 0;
                try {
                    p = interfaceMp3.getPosition();
                } catch (Exception e) {
                    Log.e(TAG, "AsyncTask, player.getPosition() \n" + e.getMessage(), e);
                }
                return p;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                seekBar.setProgress(integer);

                if (interfaceMp3.isPlaying()) {

                    Handler h = new Handler();

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            updateSeekBar();
                        }
                    };

                    h.postDelayed(r, 1000);

                } else {

                    seekBar.setProgress(0);
                    seekBar.setEnabled(false);

                    playBt.setImageResource(android.R.drawable.ic_media_play);
                    getIntent().putExtra("BT_PLAY", true);

                    text.setEnabled(true);
                    text.setText("");

                    searchBt.setEnabled(true);

                }
            }
        }.execute();
    }
}
