package com.example.orarproiect;


import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecoderHTML extends AsyncTask<Void, Void, Void> {
    private final String URL_HTML_IMPORT_DATA = "https://www.cs.ubbcluj.ro/files/orar/2020-1/tabelar/MI2.html";
    private List<String> listImportRaw = new ArrayList<String>();

    private List<Ora> luniData = new ArrayList<Ora>();
    private List<Ora> martiData = new ArrayList<Ora>();
    private List<Ora> miercuriData = new ArrayList<Ora>();
    private List<Ora> joiData = new ArrayList<Ora>();
    private List<Ora> vineriData = new ArrayList<Ora>();

    private IDecodable handler;

    DecoderHTML(IDecodable handler) {
        this.handler = handler;

        // TODO
        // Refresh UI
        // postExecute () > refreshUI
        // progressBar (onProgressUpdate(), publishProgress(int percent)
        // View visibility for progress bar

        // onItemSelected()
        // AsyncInput
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            this.decode();
        }
        catch (IOException | ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        handler.onDecodeComplete();
    }

    private void decode() throws IOException, ParseException {
        org.jsoup.nodes.Document doc = Jsoup.connect(URL_HTML_IMPORT_DATA).get();

        org.jsoup.select.Elements rows = doc.select("tr");
        for (org.jsoup.nodes.Element row : rows) {
            org.jsoup.select.Elements columns = row.select("td");
            for (org.jsoup.nodes.Element column : columns)
                listImportRaw.add(column.text());
            this.dataFilter(listImportRaw);
            listImportRaw.clear();
        }
    }

    private void dataFilter(List<String> dataRaw) throws ParseException {
        if(!dataRaw.isEmpty()) {
            String[] hoursSplit = dataRaw.get(1).split("-");
            System.out.println(dataRaw.toString());

            if (dataRaw.get(0).toLowerCase().equals("luni")) {
                Ora ora = new Ora(dataRaw.get(6), Integer.parseInt(hoursSplit[0]), Integer.parseInt(hoursSplit[1]),
                        dataRaw.get(4), dataRaw.get(3), dataRaw.get(5), dataRaw.get(7));
                luniData.add(ora);
            }

            if (dataRaw.get(0).toLowerCase().equals("marti")) {
                Ora ora = new Ora(dataRaw.get(6), Integer.parseInt(hoursSplit[0]), Integer.parseInt(hoursSplit[1]),
                        dataRaw.get(4), dataRaw.get(3), dataRaw.get(5), dataRaw.get(7));
                martiData.add(ora);
            }

            if (dataRaw.get(0).toLowerCase().equals("miercuri")) {
                Ora ora = new Ora(dataRaw.get(6), Integer.parseInt(hoursSplit[0]), Integer.parseInt(hoursSplit[1]),
                        dataRaw.get(4), dataRaw.get(3), dataRaw.get(5), dataRaw.get(7));
                miercuriData.add(ora);
            }

            if (dataRaw.get(0).toLowerCase().equals("joi")) {
                Ora ora = new Ora(dataRaw.get(6), Integer.parseInt(hoursSplit[0]), Integer.parseInt(hoursSplit[1]),
                        dataRaw.get(4), dataRaw.get(3), dataRaw.get(5), dataRaw.get(7));
                joiData.add(ora);
            }

            if (dataRaw.get(0).toLowerCase().equals("vineri")) {
                Ora ora = new Ora(dataRaw.get(6), Integer.parseInt(hoursSplit[0]), Integer.parseInt(hoursSplit[1]),
                        dataRaw.get(4), dataRaw.get(3), dataRaw.get(5), dataRaw.get(7));
                vineriData.add(ora);
            }
        }
    }

    public List<Ora> getLuniData() {
        return luniData;
    }

    public List<Ora> getMartiData() {
        return martiData;
    }

    public List<Ora> getMiercuriData() {
        return miercuriData;
    }

    public List<Ora> getJoiData() {
        return joiData;
    }

    public List<Ora> getVineriData() {
        return vineriData;
    }
}
