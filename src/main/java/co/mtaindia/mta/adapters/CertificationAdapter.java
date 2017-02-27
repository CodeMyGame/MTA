package co.mtaindia.mta.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import co.mtaindia.mta.R;
import co.mtaindia.mta.beans.CertificationBean;

/**
 * Created by Kapil Gehlot on 1/14/2017.
 */

public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.MyViewHolder> {
    private List<CertificationBean> gallaryList;
    private boolean isclick = false;
    private Context context;
    private int certificate_position;
    private View view;
    public CertificationAdapter(List<CertificationBean> gList, Context c) {
        this.gallaryList = gList;
        this.context = c;
    }

    @Override
    public CertificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.certification_single_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CertificationAdapter.MyViewHolder holder, final int position) {
        CertificationBean gallary = gallaryList.get(position);
        holder.tvHeading.setText(gallary.getHeading());
        holder.tvDescription.setText(gallary.getDescription());
        holder.tvCode.setText("Exam code : " + gallary.getCode());
        Glide
                .with(context)
                .load(gallaryList.get(position).url)
                .placeholder(R.drawable.image_no)
                .crossFade()
                .into(holder.imageView);
        holder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.vibe.vibrate(12);
                view = v;
                isclick = true;
                certificate_position = position;
                Toast.makeText(context, "Downloading.... ", Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("mta");
                myRef.child("certificate_pdf").child("" + position).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String url = dataSnapshot.getValue().toString();
                            if (isclick) {
                                new DownloadFile().execute(url, "certificate" + position + ".pdf");
                                isclick = false;
                            }
                        } else {
                            Toast.makeText(context, "item not found (database empty)", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeading;
        TextView tvDescription;
        TextView tvCode;
        ImageView imageView;
        ImageView imageViewDownload;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHeading = (TextView) itemView.findViewById(R.id.title);
            tvDescription = (TextView) itemView.findViewById(R.id.description);
            tvCode = (TextView) itemView.findViewById(R.id.code);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewDownload = (ImageView) itemView.findViewById(R.id.imgdownload);

        }
    }

    private class DownloadFile extends AsyncTask<String, String, Void> {
        private static final int MEGABYTE = 1024 * 1024;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.setMax(100);
            progressDialog.setMessage("Downloading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Snackbar snackbar = Snackbar
                    .make(view, "Download successfull!!!", Snackbar.LENGTH_LONG)
                    .setAction("OPEN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPDF();
                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
            progressDialog.setIndeterminate(false);
        }

        void viewPDF() {
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "MTA");
            File pdfFile = new File(folder, "certificate" + certificate_position + ".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        }
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "MTA");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            if (pdfFile.exists()) {
                pdfFile.delete();
            }
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total += bufferLength;
                    fileOutputStream.write(buffer, 0, bufferLength);
                    publishProgress("" + (int) ((total * 100) / totalSize));
                }
                fileOutputStream.close();
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
