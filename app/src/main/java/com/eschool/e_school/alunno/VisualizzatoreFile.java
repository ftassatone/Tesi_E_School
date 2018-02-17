package com.eschool.e_school.alunno;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eschool.e_school.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
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

public class VisualizzatoreFile extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, TextToSpeech.OnInitListener {

    private final static int ID_RICHIESTA_PERMISSION = 0;
    private ImageButton btSucc,btPrec,btPlay,btPausa,btStop;
    private Button sintetizzatore, cal;
    private LinearLayout media, menuSuperiore,contenitore;
    private PDFView pdfView;
    private ProgressBar pb;
    private  Dialog dialog;
    private int downloadedSize = 0, totalSize = 0, countPlay = 0; //countPlay contatore di lettura
    private File dir, teoria;
    private TextView cur_val;
    private Integer pageNumber = 0;
    private ArrayList<String> testoPagine;  //lista del testo per ogni pagina
    private TextToSpeech tts;
    private String[] frasi;
    private String download_file_path;
    private HashMap<String, String> map;
    private Boolean controllo = false, isTTSready; //isTTSready controllo sull'istanza TextToSpeech
    private Boolean controlloSint = false;  //controlla se il sintetizzatore è stato attivato
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private HashMap<String,String> listaImage;
    private boolean opzDsa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizzatore_file);

        //permessi
        int statoPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int statoPermissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int statoPermissionLock = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
        if (statoPermissionWrite == PackageManager.PERMISSION_DENIED || statoPermissionRead == PackageManager.PERMISSION_DENIED || statoPermissionLock == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, ID_RICHIESTA_PERMISSION);
        }

        pdfView = (PDFView) findViewById(R.id.pdfView);
        btSucc = (ImageButton) findViewById(R.id.btSucc);
        btPrec = (ImageButton) findViewById(R.id.btPrec);
        btPlay = (ImageButton) findViewById(R.id.btPlay);
        btPausa = (ImageButton) findViewById(R.id.btPausa);
        btStop = (ImageButton) findViewById(R.id.btStop);
        sintetizzatore = (Button) findViewById(R.id.btSintetizzatore);
        cal = (Button) findViewById(R.id.btCal);
        media = (LinearLayout) findViewById(R.id.media);
        menuSuperiore = (LinearLayout) findViewById(R.id.menuSuperiore);
        contenitore = (LinearLayout) findViewById(R.id.contenitore);

        testoPagine = new ArrayList<>();
        listaImage = new HashMap<>();

        tts = new TextToSpeech(this, this);

        btSucc.setEnabled(false);
        btPrec.setEnabled(false);

        download_file_path = getIntent().getStringExtra("file");
        opzDsa = getIntent().getBooleanExtra("dsa",false);
        Log.d("ALUNNO","dsa "+opzDsa);
        //abilito/disabilito la sezione degli strumenti compensativi
        if(opzDsa){
            menuSuperiore.setVisibility(View.VISIBLE);
        }else{
            menuSuperiore.setVisibility(View.INVISIBLE);
        }

        if(download_file_path.equalsIgnoreCase("")){
            Toast.makeText(VisualizzatoreFile.this, "Non c'è nulla da scaricare.", Toast.LENGTH_SHORT).show();
        }else {
            showProgress(download_file_path);   //visualizza la progressbar e avvia il download
            new Download().execute(download_file_path);           //avvio il download
        }

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CalcolatriceParlante.class));
            }
        });

        //permettono di rendere visibile/invisibile il menu per la lettura
        menuSuperiore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visualizzaMenu();
            }
        });
        contenitore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               visualizzaMenu();
            }
        });

        sintetizzatore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlloSint = true;   //indica che il sintetizzatore è stato cliccato e quindi posso utilizzare il menu lettura
                if(media.getVisibility() == View.INVISIBLE)
                    media.setVisibility(View.VISIBLE);
                else
                    media.setVisibility(View.INVISIBLE);
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSucc.setEnabled(true);
                btPrec.setEnabled(true);
                ottieniRighePagina(pageNumber);   //ottengo la lsita delle righe per pagina
                listenerSitesi();
                lettura(frasi[countPlay],"messageID");
                //tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
            }
        });


        btPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DATI", "entro in pausa");

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

        btSucc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSitesi();
                if((countPlay+1)<frasi.length-1) {
                    lettura(frasi[++countPlay],"messageID");
                    //tts.speak(frasi[++countPlay], TextToSpeech.QUEUE_FLUSH, null, "messaggioID");
                }else {
                    stopSpeech();
                    voltaPagina();
                }
            }
        });

        btPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSitesi();
                if (countPlay >= 1) {
                    countPlay = countPlay - 1;
                    lettura(frasi[countPlay],"messageID");
                    //tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messaggioID");
                } else {
                    Toast.makeText(getApplicationContext(), "Non puoi andare indietro.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void lettura(String testo,String message){
        if (isTTSready) {
            if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(testo, TextToSpeech.QUEUE_FLUSH, null, message);
            } else {
                map = new HashMap<>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, message);
                tts.speak(testo, TextToSpeech.QUEUE_FLUSH,map);
            }
        }
    }

    /**
     * Listner legato alla sintesi volcale, gestisce cosa deve succedere all fine della lettura del testo
     */
    private void listenerSitesi(){
        map = new HashMap<>();
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
                    if((countPlay+1)<frasi.length) {
                        lettura(frasi[++countPlay],"messageID");
                        //tts.speak(frasi[++countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
                    }else {
                        stopSpeech();
                        voltaPagina();
                    }
            }
            @Override
            public void onError(String s) {
                //do nothing
            }
        });
    }

    /**
     * Permette la suddivisione del testo il blocchi in modo da potersi fermare in un punto,
     * per poi ricominciare a leggere da lì e non dover leggere il pdf dall'inizio
     * Effetua un controllo su possibili posizioni vuore (blocco di linee bianche) in modo da rendere la lettura e l'avanzamento di frasi
     * fluido
     */
    private void ottieniRighePagina(Integer pagina) {
        ArrayList<Integer> posVuote = new ArrayList<>();
        String testoPag = (String) testoPagine.get(pagina);
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
        for(int i =0; i < frasi.length; i++){
            Log.d("PROG", "-- " +frasi[i]);
        }
    }

    /**
     * Stoppo il tts e azzero il contatore
     * in modo da ricominciare la lettura dall'inizio
     */
    private void stopSpeech() {
        tts.stop();
        countPlay = 0;
    }

    /**
     * Permette di voltare pagina e continuare al lettura della pagina successiva
     * quando la sintesi vocale ha terminato la pagina corrente.
     */
    private void voltaPagina() {
        controllo = true;
         pageNumber = pageNumber + 1;
        if (pageNumber < testoPagine.size()) {
            pdfView.fromFile(teoria).defaultPage(pageNumber).onPageChange(this).load();
            ottieniRighePagina(pageNumber);   //dopo aver voltato(visualizzato) pagina, acquisisco le righe della nuova pagina
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
                public void onError(String s) {//do nothing
                }
            });
            tts.speak(frasi[countPlay], TextToSpeech.QUEUE_FLUSH, null, "messageID");
        }else{
            stopSpeech();
            Toast.makeText(VisualizzatoreFile.this, "non ci sono piu pagine da leggere ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Visualizza il menu dei pulsanti che permetteranno la lettura del pdf
     */
    private void visualizzaMenu(){
        if(controlloSint) {
            if (media.getVisibility() == View.INVISIBLE)
                media.setVisibility(View.VISIBLE);
            else
                media.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Vedi AsyncTask
     */
    private void ottieniTesto() {
        new OttieniTesto().execute();
    }

    /**
     * Permette di scaricare il file sul dispositivo
     * @return file
     */
    private File downloadFile(String pathFile) {

        try {
            //scarico il file
            URL url = new URL(pathFile);
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
            File file = new File(dir, "eschool_teoria.pdf");

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
                        Log.d("PROG","progresso "+per);
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

    /**
     * Visualizzo il file a video
     * @param x - file da visualizzare
     */
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

    /**
     * Al cambiamento della pagina setta il titolo con il numero di pagine lette e da leggere
     * e nel caso si stia voltando pagina mentre la sistensi è ancora in azione la stoppa
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s / %s",page + 1, pageCount));
        if(tts.isSpeaking() && !controllo) {    //stoppa la sintesi al volta pagina
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
            //Log.e("DATI", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    /**
     * Mostra la progressBar durante il download del file
     * @param file_path
     */
    private void showProgress(String file_path) {
        dialog = new Dialog(VisualizzatoreFile.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar_dialog);
        dialog.setTitle("Download Progress");

       // TextView text = dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();

        pb = dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.layout_progressbar));
    }

    /**
     * AsyncTAck che permette il download del file e l'estrazione del testo
     * Concluso il download visualizzo il file sul display
     */
    private class Download extends AsyncTask<String, Void, File> {
        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            cur_val.setText("Attendi qualche altro secondo... ");
            new OttieniTesto().execute(download_file_path);  //acquisisco il testo per pagina
        }

        @Override
        protected File doInBackground(String... strings) {
            teoria = downloadFile(strings[0]);    //scarico il file
            try {
                manipulatePdf(teoria.getAbsolutePath()); //ottengo le immagini presenti nel pdf
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return teoria;
        }
    }

    /**
     * Utilizzando la libreria ITEXT riesco a risalire alla struttira del pdf
     * e sfruttando il metodo "manipulate", rieso ad analizzare elemento per elemento.
     * Se l'elemento in analisi è una figura, aggiungo nell'HashMap listaImage l'id e il testo alternativo associato all'immagine.
     * LIMITAZIONE: L'analisi della struttura del pdf è possibilie solo se quest'ultimo è stato creato con la libreria ITEXT
     * e se è un pdf Tagged, in caso contrario non sarà possiblile accedere alla struttura e all'eventuale presenza di immagini
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public void manipulatePdf(String file) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(file);
        PdfDictionary catalog = reader.getCatalog();
        PdfDictionary structTreeRoot = catalog.getAsDict(PdfName.STRUCTTREEROOT);
        manipulate(structTreeRoot);
    }
    /**
     * Permette di analizzare gli elementi del pdf.
     * Richiamato nel metodo "manipulatePdf"
     * @param element
     */
    public void manipulate(PdfDictionary element) {
        if (element == null)
            return;
        if (PdfName.FIGURE.equals(element.get(PdfName.S))) {
            listaImage.put("(IMG_"+element.get(PdfName.ID).toString() +")",element.get(PdfName.ALT).toString());
        }
        PdfArray kids = element.getAsArray(PdfName.K);
        if (kids == null) return;
        for (int i = 0; i < kids.size(); i++)
            manipulate(kids.getAsDict(i));
    }

    /**
     * AsyncTask che mi permette di estrarre il testo per pagina ed in presenza di immagini nel pdf
     * sostituuire l'indicazione (IMG_x) con il testo alternativo asssociato, se è presente.
     * LIMITAZIONE: Ciò è possibile solo se il pdf è stato creato con l'app, poichè alla creazione viene data la possibilità
     * di associare testo alternativo alle immagini inserite.
     */
    private class OttieniTesto extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            display(teoria); //visualizzo il file
            btPlay.setEnabled(true);
            btPausa.setEnabled(true);
            btStop.setEnabled(true);
        }
        @Override
        protected Void doInBackground(String... strings) {
            try {
                String parsedText = "";
                PdfReader reader = new PdfReader(strings[0]);
                int n = reader.getNumberOfPages();
                for (int i = 0; i < n; i++) {
                    parsedText = PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + ""; //Estrazione del contenuto di ogni pagina
                    if(!listaImage.isEmpty()) {
                        for (int j = 1; j <= listaImage.size(); j++) {
                            String idImg = "(IMG_" + j + ")";
                            if (parsedText.contains(idImg)) {
                                String alt = listaImage.get("(IMG_" + j + ")");
                                if (alt != "") {
                                    parsedText = parsedText.replace(idImg, " " + "immagine " + j + " " + alt);
                                }
                            }
                        }
                    }
                    testoPagine.add(parsedText);
                }
                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
    }

    /**
     * Listener del TextToSpeech che informa del completamento dell'inizializzazione.
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSready = true;
            int result = tts.setLanguage(Locale.ITALIAN);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Ligua non trovata.", Toast.LENGTH_LONG).show();
            }
        }
        /*if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ITALIAN); //impostiamo l'italiano
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("LOG", "Mancano i dati vocali: installali per continuare");
            } else {
                Log.d("LOG", "giunge qui se tutto va come previsto");
            }
        } else {
            Log.d("LOG", "Il Text To Speech sembra non essere supportato");
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stoppo il tts
        tts.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopSpeech();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //prima di tornare all'activity precedente, rilascio le risorse utilizzate dal motore TextToSpeech.
            tts.shutdown();

            Intent back = NavUtils.getParentActivityIntent(this);
            back.putExtra("materia", HomeMateria.materia);
            back.putExtra("livello", HomeMateria.livello);
            if (NavUtils.shouldUpRecreateTask(this, back)) {
                TaskStackBuilder.create(this).addNextIntentWithParentStack(back).startActivities();
            } else {
                NavUtils.navigateUpTo(this, back);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}