package com.gabrielaangebrandt.funworld.memory_activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gabrielaangebrandt.funworld.R;
import com.gabrielaangebrandt.funworld.memory_activity.MemoryContract;
import com.gabrielaangebrandt.funworld.models.data_model.MemoryObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    List<MemoryObject> objects = new ArrayList<>();
    private int counter;
    private MemoryContract.MemoryView listener;

    public MyRecyclerAdapter(MemoryContract.MemoryView listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_element_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.flag.setImageResource(R.drawable.back);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void addDataToAdapter(List<MemoryObject> definedDrawables) {
        objects.clear();
        objects.addAll(definedDrawables);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       @BindView(R.id.iv_memory_element) ImageView flag;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            flag.setOnClickListener(this);
        }

        public void onClick(View v) {
            //na klik elementa u recycleru provjeri je li on već pronašao svog para, ako niji postavi da je kliknut
            //zatim prikaži zastavu. Nakon toga prođi kroz cijeli niz i pogledaj postoji li još jedna zastava koja je kliknuta
            //Ako postoji provjeri jesu li zastave na tim mjestima jednake preko alphaCoda, ako jesu postavi ih na isMatched(),
            //ako nisu postavi isClicked na false da se element vrati u početno stanje bez prikaza zastave
            //Korišten je i counter da izbroji kad su svi elementi na isMatched() = true, to znaci da je partija gotova
            if (!objects.get(getAdapterPosition()).isMatched()) {
                objects.get(getAdapterPosition()).setClicked(true);
                flag.setImageResource(objects.get(getAdapterPosition()).isClicked() ? objects.get(getAdapterPosition()).getID() : R.drawable.back);
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).isClicked() && !objects.get(i).isMatched() && (getAdapterPosition() != i)) {
                        if (objects.get(i).getAlphaCode().equals(objects.get(getAdapterPosition()).getAlphaCode())) {
                            objects.get(i).setMatched(true);
                            objects.get(getAdapterPosition()).setMatched(true);
                        } else {
                            objects.get(i).setClicked(false);
                            objects.get(getAdapterPosition()).setClicked(false);
                            notifyItemChanged(i);
                            notifyItemChanged(getAdapterPosition());
                        }
                    }
                }
            }
            for (MemoryObject object : objects) {
                if (object.isMatched()) counter++;
            }
            if (counter == 18) {
                listener.showScore();
                counter = 0;
            } else {
                counter = 0;
            }
        }
    }
}
