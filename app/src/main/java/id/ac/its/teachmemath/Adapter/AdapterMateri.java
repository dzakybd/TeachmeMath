package id.ac.its.teachmemath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import id.ac.its.teachmemath.Database.Materi;
import id.ac.its.teachmemath.R;

public class AdapterMateri extends RecyclerView.Adapter<AdapterMateri.ReyclerViewHolder> {

    private Context context;
    private List<Materi> items;

    public AdapterMateri(Context context, List<Materi> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermateri, parent, false);
        return new ReyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ReyclerViewHolder holder, int position) {
        try{

            Materi materis = new Select()
                    .from ( Materi.class )
                    .where("id = ?",items.get(position).getId())
                    .executeSingle();
            holder.a.setText(materis.nama);
            holder.b.setText("oleh "+materis.guru.nama);
            holder.c.setText(materis.penjelasan);
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

            a = (TextView) v.findViewById(R.id.materia);
            b = (TextView) v.findViewById(R.id.materib);
            c = (TextView) v.findViewById(R.id.materic);
        }
    }
}
