package com.example.crafty;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.crafty.ChartView;
import com.example.crafty.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ChartView chartView;
    private TextView profitTextView;
    private TextView salesTextView;
    private TextView quantityTextView;
    private ImageButton arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        chartView = findViewById(R.id.ChartView);
        profitTextView = findViewById(R.id.profit_text);
        salesTextView = findViewById(R.id.sales_text);
        quantityTextView = findViewById(R.id.quantity_text);

        arrow_left = findViewById(R.id.arrow_left);

        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Lakukan permintaan HTTP di latar belakang menggunakan AsyncTask
        new FetchDataAsyncTask().execute("https://e297-2001-448a-1082-63ed-b9a8-7da1-cada-2641.ngrok-free.app/predict");
    }
    private class FetchDataAsyncTask extends AsyncTask<String, Void, DataResult> {
        @Override
        protected DataResult doInBackground(String... urls) {
            String urlString = urls[0];
            try {
                // Buat URL objek dan buka koneksi HTTP
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                // Baca data dari respons
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Konversi JSON ke List<Float>
                JSONArray jsonArray = new JSONArray(response.toString());
                List<Integer> dataList = new ArrayList<>();
                JSONArray firstArray = jsonArray.getJSONArray(0);
                JSONObject quantityObject = firstArray.getJSONObject(1);
                JSONArray quantityArray = quantityObject.getJSONArray("Quantity");
                JSONObject sumObject = firstArray.getJSONObject(4);
                int profitSum = sumObject.getInt("Profit_sum");
                int salesSum = sumObject.getInt("Sales_sum");
                int quantitySum = sumObject.getInt("Quantity_sum");
                for (int i = 0; i < quantityArray.length(); i++) {
                    dataList.add(quantityArray.getInt(i));
                }
                return new DataResult(dataList, profitSum, salesSum, quantitySum);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(DataResult result) {
            super.onPostExecute(result);
            if (result != null) {
                // Atur data pada grafik
                chartView.setData(result.getQuantityData());

                // Atur nilai Profit_sum pada TextView
                profitTextView.setText(String.valueOf(result.getProfitSum()));
                salesTextView.setText(String.valueOf(result.getSalesSum()));
                quantityTextView.setText(String.valueOf(result.getQuantitySum()));
            } else {
                // Handle kesalahan
            }
        }
    }
}
