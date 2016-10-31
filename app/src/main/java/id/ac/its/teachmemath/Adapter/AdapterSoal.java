package id.ac.its.teachmemath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import id.ac.its.teachmemath.Database.Soal;
import id.ac.its.teachmemath.R;

public class AdapterSoal extends RecyclerView.Adapter<AdapterSoal.ReyclerViewHolder> {

    private Context context;
    private List<Soal> items;

    public AdapterSoal(Context context, List<Soal> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclersoal, parent, false);
        return new ReyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ReyclerViewHolder holder, int position) {
        try{
            Soal soal = new Select()
                    .from ( Soal.class )
                    .where("id = ?",items.get(position).getId())
                    .executeSingle();
            holder.a.setText("Soal "+soal.getId());
            holder.b.setText("dalam "+soal.materi.nama);
            holder.c.setText(soal.soal);
        }catch (Exception e) {
            holder.a.setText("error");
            holder.b.setText("lo eh error");
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

            a = (TextView) v.findViewById(R.id.soala);
            b = (TextView) v.findViewById(R.id.soalb);
            c = (TextView) v.findViewById(R.id.soalc);
        }
    }
}
