package com.eschool.e_school;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Alunno_VisualizzatoreFile extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, TextToSpeech.OnInitListener {

    private final static int ID_RICHIESTA_PERMISSION = 0;
    private Button btSucc,btPrec,btPlay,btPausa,btStop,b;
    private PDFView pdfView;
    private ProgressBar pb;
    private  Dialog dialog;
    private int downloadedSize = 0, totalSize = 0, countPlay = 0;
    private File dir, teoria, pdfFileName;
    private TextView cur_val;
    private Integer pageNumber = 0;
    private ArrayList<CharSequence> testoPagine;  //lista del testo per ogni pagina
    private TextToSpeech tts;
    private CharSequence toSpeak, pagina = "";
    private String[] frasi;
    private String download_file_path = "http://eschooldb.altervista.org/File/propriet____4_operazioni.pdf";
    private HashMap<String, String> map;
    private Boolean controllo = false;
    private PowerManager pm;
    private PowerManager.WakeLock wl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizzatore_file);

        //permessi
        int statoPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int statoPermissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (statoPermissionWrite == PackageManager.PERMISSION_DENIED || statoPermissionRead == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
        }

        pdfView = (PDFView) findViewById(R.id.pdfView);
        btSucc = (Button) findViewById(R.id.btSucc);
        btPrec = (Button) findViewById(R.id.btPrec);
        btPlay = (Button) findViewById(R.id.btPlay);
        btPausa = (Button) findViewById(R.id.btPausa);
        btStop = (Button) findViewById(R.id.btStop);
        b = (Button) findViewById(R.id.b1);

        testoPagine = new ArrayList<>();
        tts = new TextToSpeech(this, this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(download_file_path);
                new Download().execute();
            }
        });

        btSucc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSitesi();

                tts.speak(frasi[++countPlay], TextToSpeech.QUEUE_FLUSH, null, "messaggioID");
            }
        });

        btPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSitesi();

                if (countPlay >= 1) {
                    countPlay = countPlay - 1;
                    tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messaggioID");
                } else {
                    Toast.makeText(getApplicationContext(), "Non puoi andare indietro.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSucc.setEnabled(true);
                btPrec.setEnabled(true);

                ottieniRighePagina();
                listenerSitesi();

                tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
            }
        });

        btPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSpeech();
                wl.release();
            }
        });

    }
    private void listenerSitesi(){
        map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                pm = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
                wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "lock");
                wl.acquire(); //Forzo l'accensione dello schermo

            }

            @Override
            public void onDone(String s) {
                if (countPlay < frasi.length - 1) {
                    /*controllo se la posizione all'interno dell'array è piena, nel caso la leggo;
                        altrimenti se la lunghezza della frase è minore o uguale ad uno, vuol dire che sono alla fine della lista delle frasi
                        quindi posso cambiare pagina e continuare a leggere la pagian successiva*/
                    if (frasi[countPlay + 1].length() > 1) {
                        tts.speak(frasi[++countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
                    } else {
                        stopSpeech();
                        voltaPagina();
                    }
                }
                if(!pm.isInteractive()) {
                    wl.release();
                }
            }

            @Override
            public void onError(String s) {
                //do nothing
            }
        });
    }

    private void ottieniRighePagina() {
        ArrayList<Integer> posVuote = new ArrayList<>();
        String testoPag = (String) testoPagine.get(pageNumber);
        frasi = testoPag.split("\\n");
        for (int i = 0; i < frasi.length - 1; i++) {
            if (frasi[i].length() == 1) {
                posVuote.add(i);
            } else {
                if (!posVuote.isEmpty()) {
                    frasi[posVuote.get(0)] = frasi[i];
                    frasi[i] = "";
                    posVuote.add(i);
                    posVuote.remove(0);
                }
            }
        }
    }

    private void stopSpeech() {
        tts.stop();
        countPlay = 0;
    }

    private void voltaPagina() {
        controllo = true;
        pageNumber = pageNumber + 1;
        if (pageNumber <= testoPagine.size()) {
            pdfView.fromFile(teoria)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .load();
        }
        ottieniRighePagina();

        //listenerSitesi();
        map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                //do nothing
            }

            @Override
            public void onDone(String s) {
                controllo = false;
                if (countPlay < frasi.length - 1) {
                    countPlay = countPlay + 1;
                    tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
                } else {
                    stopSpeech();
                }
            }

            @Override
            public void onError(String s) {
                //do nothing
            }
        });
        tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ITALIAN); //impostiamo l'italiano
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("LOG", "Mancano i dati vocali: installali per continuare");
            } else {
                Log.d("LOG", "giunge qui se tutto va come previsto");
            }
        } else {
            Log.d("LOG", "Il Text To Speech sembra non essere supportato");
        }
    }

    private void ottieniTesto() {
        new OttieniTesto().execute();
    }

    File downloadFile() {

        try {
            //scarico il file
            URL url = new URL(download_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();
            File sdCard;
            //controllo sulla memeoria esterna
            //String state = Environment.getExternalStorageState();
            sdCard = Environment.getExternalStorageDirectory();
            if (sdCard.exists()) {
                dir = sdCard;
            } else {
                dir = getDir("prova.pdf", MODE_PRIVATE);

            }
            //set the path where we want to save the file
            //File SDCardRoot = Environment.getExternalStorageDirectory();
            //Log.d("DATI", "memorizzo qui " + dir);
            //create a new file, to save the downloaded file
            File file = new File(dir, "teoria.pdf");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float) downloadedSize / totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int) per + "%)");
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });
            if (file.exists()) {
                    /*Uri path = Uri.fromFile(file);
                    Log.d("DATI", String.valueOf(path));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                return file;
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void display(File x) {
        pdfView.fromFile(x)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
        if(tts.isSpeaking() && !controllo) {
            stopSpeech();
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    private void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.e("DATI", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    void showProgress(String file_path) {
        dialog = new Dialog(Alunno_VisualizzatoreFile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar_dialog);
        dialog.setTitle("Download Progress");

        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();

        pb = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.layout_progressbar));
    }

    private class Download extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            display(teoria);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            teoria = downloadFile();    //scarico il file
            ottieniTesto();             //acquisisco il testo per pagina
            return null;
        }
    }

    private class OttieniTesto extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            btPlay.setEnabled(true);
            btPausa.setEnabled(true);
            btStop.setEnabled(true);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... strings) {
            try {
                CharSequence parsedText = "";
                PdfReader reader = new PdfReader(download_file_path);
                int n = reader.getNumberOfPages();
                for (int i = 0; i < n; i++) {
                    parsedText = PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + ""; //Extracting the content from the different pages
                    testoPagine.add(parsedText);
                }
                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
    }

    @Override
    protected void onPause() {
        Log.d("DATI","sono in onPause");
        super.onPause();
        tts.stop();
        //Log.d("DATI", "contatore onPause "+countPlay);
        //Log.d("DATI", "frase onPause"+ frasi.toString());
    }

    @Override
    public void onStop() {
        Log.d("DATI","sono in onStop");
        super.onStop();
        stopSpeech();
    }
}