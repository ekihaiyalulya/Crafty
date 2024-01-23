package com.example.crafty;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class ChartView extends View {

    private List<Integer> dataPoints;

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<Integer> dataPoints) {
        this.dataPoints = dataPoints;
        invalidate(); // Menyegarkan tampilan setelah data diatur
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataPoints == null || dataPoints.size() < 2) {
            return;
        }

        Paint paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(7);
        paintLine.setAntiAlias(true);

        Paint paintAxis = new Paint();
        paintAxis.setColor(Color.WHITE);
        paintAxis.setStrokeWidth(5);
        paintAxis.setAntiAlias(true);

        Paint paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(28);
        paintText.setAntiAlias(true);

        int width = getWidth();
        int height = getHeight();

        int maxX = dataPoints.size() - 1;
        int maxY = getMaxValue(dataPoints);

        int xScale = width / maxX;
        int yScale = height / maxY;

        int xPadding = 40;  // Padding pada sumbu x
        int yPadding = 30;  // Padding pada sumbu y

        // Gambar sumbu x dan y
        canvas.drawLine(xPadding, height, width, height, paintAxis); // sumbu x
        canvas.drawLine(xPadding, yPadding, xPadding, height, paintAxis); // sumbu y

        // Definisikan array label untuk sumbu x
        String[] dayLabels = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};

        // Tambahkan label pada sumbu x
        for (int i = 0; i < dayLabels.length; i++) {
            int xLabel = xPadding + i * xScale;
            float textWidth = paintText.measureText(dayLabels[i]);
            paintText.setTextSize(28);
            // Menyesuaikan nilai posisi vertikal
            float yOffset = 7;  // Sesuaikan dengan nilai yang sesuai
            canvas.drawText(dayLabels[i], xLabel - textWidth / 2, height - yOffset, paintText);

            // Tambahkan label pada garis sumbu x
            canvas.drawText("|", xLabel, height - -5, paintText);
        }

        // Mulai dari titik pertama
        int startX = xPadding;
        int startY = height - (dataPoints.get(0) * yScale);

        for (int i = 1; i < dataPoints.size(); i++) {
            int endX = xPadding + i * xScale;
            int endY = height - (dataPoints.get(i) * yScale);

            // Gambar garis
            canvas.drawLine(startX, startY, endX, endY, paintLine);
            paintText.setTextSize(35);
            // Tampilkan nilai di setiap titik
            canvas.drawText(String.valueOf(dataPoints.get(i)), endX, endY - 10, paintText);

            startX = endX;
            startY = endY;
        }

        // Tambahkan label pada sumbu y
        for (int i = 1; i <= maxY; i++) {
            int yLabel = height - i * yScale;

            // Mengurangi ukuran teks
            paintText.setTextSize(24); // Ubah nilai sesuai kebutuhan

            canvas.drawText(String.valueOf(i), xPadding - 35, yLabel, paintText);

            // Tambahkan label pada garis sumbu y
            canvas.drawText("_", xPadding - 5, yLabel, paintText);

            // Mengembalikan ukuran teks ke nilai semula setelah menggambar label
            paintText.setTextSize(20); // Ubah nilai sesuai kebutuhan
        }

    }

    private int getMaxValue(List<Integer> dataPoints) {
        int max = Integer.MIN_VALUE;
        for (int value : dataPoints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
