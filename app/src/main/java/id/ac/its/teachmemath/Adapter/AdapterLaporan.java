package id.ac.its.teachmemath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import id.ac.its.teachmemath.Database.Laporan;
import id.ac.its.teachmemath.R;

public class AdapterLaporan extends RecyclerView.Adapter<AdapterLaporan.ReyclerViewHolder> {

    private Context context;
    private List<Laporan> items;

    public AdapterLaporan(Context context, List<Laporan> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlaporan, parent, false);
        return new ReyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ReyclerViewHolder holder, int position) {
        try{

            Laporan laporan = new Select()
                    .from ( Laporan.class )
                    .where("id = ?",items.get(position).getId())
                    .executeSingle();
            holder.a.setText("Soal "+ laporan.soal.getId() + " - Materi " +laporan.soal.materi.nama);
            holder.b.setText("oleh "+laporan.murid.nama);
            String pesan;
            if(laporan.status==true)pesan="benar"; else pesan="salah";
            holder.c.setText("Soal dan jawaban : " + laporan.soal.soal +" = "+ laporan.soal.jawaban + "\nDijawab : " +laporan.jawaban +"\nMaka hasilnya : "+pesan);
        }catch (Exception e) {
            holder.a.setText("lo eh error");
            holder.b.setText("error");
            holder.c.setText("error cuy");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView a,b,c;

        private ReyclerViewHolder(final View v) {
            super(v);

            a = (TextView) v.findViewById(R.id.laporana);
            b = (TextView) v.findViewById(R.id.laporanb);
            c = (TextView) v.findViewById(R.id.laporanc);
        }
    }
}
