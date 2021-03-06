package com.eschool.e_school.docente;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eschool.e_school.Dialog.DialogAltText;
import com.eschool.e_school.R;
import com.eschool.e_school.altervista.ParametriAltervista;
import com.eschool.e_school.altervista.SaveOnAltervista;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NuovaPaginaTeoria extends AppCompatActivity implements DialogAltText.NoticeDialogListener {

    private EditText argomento,nomeF;
    private Button newTitolo, newImage, btBody;
    private LinearLayout contenitoreTesto;
    private Paragraph paragrafo;
    private ArrayList<Image> listaImage;
    private Document doc;
    private File file;
    private int countImg=0;
    private String alternativeText = "", fpath;
    private HashMap<String,String> listaAlt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crea_file);

        /*permessi
        int statoPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (statoPermissionWrite == PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ID_RICHIESTA_PERMISSION);
        }*/

        listaAlt = new HashMap<>();
        listaImage = new ArrayList<>();
        nomeF = (EditText) findViewById(R.id.editNome);
        newTitolo = (Button) findViewById(R.id.btTitolo);
        btBody = (Button) findViewById(R.id.btCorpo);
        newImage = (Button) findViewById(R.id.btImg);
        argomento = (EditText) findViewById(R.id.editArgomento);
        contenitoreTesto = (LinearLayout) findViewById(R.id.contenitoreTeoria);

        //tts = new TextToSpeech(this,this);

        newTitolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiungiTesto("titolo");
            }
        });
        btBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiungiTesto("corpo");
            }
        });



        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleria, 0);
            }
        });
    }

    /**
     * permette di aggiunfere una nuova edit in cui scrivere testo
     * @param str
     */
    private void aggiungiTesto(String str){
        //if (!titolo.getText().toString().equals("")) {
            EditText tit = new EditText(getApplicationContext());
            tit.setTag(str); //lo utilizzerò nel caso voglia differenziare il font
            tit.setHint(str + "...");
            tit.setHintTextColor(Color.GRAY);
            tit.setTextColor(Color.BLACK);
            tit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tit.setOnLongClickListener(eliminaView(tit));
        contenitoreTesto.addView(tit);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salva_file, menu);
        return true;
    }

    /**
     * salvataggio pdf
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_saveFile) {
           doc= new Document();
           doc.setPageSize(PageSize.A4);

            paragrafo = new Paragraph();
            if(!nomeF.getText().toString().equals("") && !argomento.getText().toString().equals("")) {
                try {
                    fpath = String.valueOf(Environment.getExternalStorageDirectory()) + "/" + nomeF.getText().toString() + ".pdf";
                    Log.d("DATI", "path " + fpath);
                    file = new File(fpath);
                    //file = new File(nomeF.getText().toString()+".pdf");
                    //if(file.isFile()) {
                        //new SaveOnAltervista().execute(file);

                    if (!file.exists()) {
                       file.createNewFile();
                    //}
                        PdfWriter w = PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsoluteFile()));
                        w.setTagged();

                        doc.open();
                        PdfPTable tab = new PdfPTable(1);
                        for (int i = 0; i < contenitoreTesto.getChildCount(); i++) {
                            paragrafo = new Paragraph();
                            if (contenitoreTesto.getChildAt(i) instanceof EditText) {

                                EditText c = (EditText) contenitoreTesto.getChildAt(i);
                                Font font = new Font(Font.FontFamily.TIMES_ROMAN,Font.DEFAULTSIZE,Font.NORMAL);
                                Chunk cn = new Chunk(c.getText().toString(),font);
                                if(c.getTag().toString().equalsIgnoreCase("titolo")){
                                    Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN,20f,Font.BOLD);
                                    cn.setFont(fontBold);
                                }
                                cn.setAccessibleAttribute(PdfName.ALT, new PdfString(c.getText().toString()));
                                //paragrafo.add(cn);

                                PdfPCell cell = new PdfPCell();
                                cell.setBorder(Rectangle.NO_BORDER);
                                cell.addElement(cn);
                                tab.addCell(cell);
                                Log.d("TESTO", "TAB " + tab);

                            } else if (contenitoreTesto.getChildAt(i) instanceof ImageView) {
                                ImageView im = (ImageView) contenitoreTesto.getChildAt(i);
                                Chunk c = new Chunk("(IMG_" + im.getId() + ")");
                                //paragrafo = new Paragraph();
                                //paragrafo.add(c);
                                Image img = Image.getInstance(im.getTag().toString());
                                img.setAccessibleAttribute(PdfName.ID, new PdfString(String.valueOf(im.getId())));
                                img.setAlt(listaAlt.get(im.getTag().toString()));
                                img.scalePercent(10);
                                img.setAlignment(Element.ALIGN_CENTER);
                                listaImage.add(img);
                                //Chunk cn = new Chunk(img,5,-10);
                                //paragrafo.add(cn);
                                PdfPCell cell = new PdfPCell();
                                cell.setBorder(Rectangle.NO_BORDER);
                                cell.addElement(c);
                                cell.addElement(img);
                                tab.addCell(cell);
                            }
                        }
                        //cell.addElement(paragrafo);
                        //tab.addCell(cell);
                        doc.add(tab);
                        //doc.add(paragrafo);
                        doc.close();
                        ParametriAltervista param = new ParametriAltervista(getApplicationContext(),file,HomeDocenteFragment.materia,HomeDocenteFragment.classe, argomento.getText().toString());
                        new SaveOnAltervista().execute(param);       //salvo il file su altervista
                        Toast.makeText(NuovaPaginaTeoria.this, "Creato!", Toast.LENGTH_SHORT).show();
                    }else {
                        nomeF.setError("File già esistente.");
                        nomeF.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                nomeF.setError(null);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }else {
                nomeF.setError("Inserire nome file.");
                nomeF.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        nomeF.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                argomento.setError("Inserire argomento trattato.");
                argomento.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        argomento.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                Toast.makeText(NuovaPaginaTeoria.this, "Dare nome al file!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * metodo dell'interfaccia NoticeDialogListener per ottenere i dati inviati dal Dialog
     * @param testoAlt
     */
    @Override
    public void onDialogPositiveClick(String path, String testoAlt) {
        alternativeText = testoAlt;
        listaAlt.put(path,testoAlt);

    }

    /**
     * metodo che acuisisce lìimmagine selezinata dalla galleria
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                countImg++;
                Uri targetUri = data.getData(); //ottengo l'uri dall'intent
                Log.d("DATI", "uri " + targetUri);
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(targetUri, filePath, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                Log.d("IMG","path img "+ imagePath);
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri)); //ottengo il bitmap

                ImageView imgV = new ImageView(getApplicationContext());
                imgV.setImageBitmap(bitmap);
                imgV.setTag(imagePath);
                imgV.setId(countImg);
                imgV.setOnClickListener(getOnClickDoSomething(imgV));
                imgV.setOnLongClickListener(eliminaView(imgV));
                contenitoreTesto.addView(imgV);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * metodo per settare il listenr alle imageView create dinamicamente
     * @param img
     * @return
     */
    View.OnClickListener getOnClickDoSomething(final ImageView img)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(NuovaPaginaTeoria.this, "mi hai cliccato "+img.getTag() , Toast.LENGTH_SHORT).show();
                DialogFragment dialog = new DialogAltText();
                Bundle arg = new Bundle();
                arg.putString("path", img.getTag().toString());
                dialog.setArguments(arg);
                dialog.show(getFragmentManager(), "NoticeDialogFragment");
            }
        };
    }

    View.OnLongClickListener eliminaView(View v){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                contenitoreTesto.removeView(view);
                return true;
            }
        };
    }

}
