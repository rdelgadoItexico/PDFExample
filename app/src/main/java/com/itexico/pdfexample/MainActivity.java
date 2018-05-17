package com.itexico.pdfexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pdf = findViewById(R.id.button);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();
            }
        });
    }

    private void generatePdf(){

        AbstractViewRenderer page = new AbstractViewRenderer(getApplicationContext(), R.layout.pdf_page_1) {
            private String _text;

            public void setText(String text) {
                _text = text;
            }

            @Override
            protected void initView(View view) {
                TextView licence = view.findViewById(R.id.licenceEditText);
                licence.setText("211295614006");
            }
        };

        // you can reuse the bitmap if you want
        page.setReuseBitmap(true);

        new PdfDocument.Builder(getApplicationContext()).addPage(page).orientation(PdfDocument.A4_MODE.LANDSCAPE)
                .progressMessage(R.string.gen_pdf_file).progressTitle(R.string.gen_please_wait)
                .renderWidth(1500).renderHeight(2115)
                .saveDirectory(getApplication().getExternalFilesDir(null))
                         .filename("test")
                .listener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Completo");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                    }
                }).create().createPdf(this);

    }
}
