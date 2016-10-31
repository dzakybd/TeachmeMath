package id.ac.its.teachmemath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import id.ac.its.teachmemath.Database.Tanyajawab;
import id.ac.its.teachmemath.R;

public class AdapterTanyajawab extends RecyclerView.Adapter<AdapterTanyajawab.ReyclerViewHolder> {

    private Context context;
    private List<Tanyajawab> items;

    public AdapterTanyajawab(Context context, List<Tanyajawab> items) {
        this.context = context;
        this.items = items;

    }

    @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclertanyajawab, parent, false);
        return new ReyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ReyclerViewHolder holder, int position) {
        try{

            Tanyajawab tanyajawab = new Select()
                    .from ( Tanyajawab.class )
                    .where("id = ?",items.get(position).getId())
                    .executeSingle();
            holder.a.setText(tanyajawab.user.nama);
            holder.c.setText(tanyajawab.teks);
        }catch (Exception e) {
            holder.a.setText("error");
            holder.c.setText("error cuy");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView a,c;

        private ReyclerViewHolder(final View v) {
            super(v);

            a = (TextView) v.findViewById(R.id.tja);
            c = (TextView) v.findViewById(R.id.tjc);
        }
    }
}
