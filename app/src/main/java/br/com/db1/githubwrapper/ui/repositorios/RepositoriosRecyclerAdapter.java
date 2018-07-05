package br.com.db1.githubwrapper.ui.repositorios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.db1.githubwrapper.R;
import br.com.db1.githubwrapper.data.model.Repositorio;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriosRecyclerAdapter extends RecyclerView.Adapter<RepositoriosRecyclerAdapter.ViewHolder> {

    private List<Repositorio> repositorios;
    private RepositoriosContract.Presenter presenter;

    public RepositoriosRecyclerAdapter(RepositoriosContract.Presenter presenter,
                                       List<Repositorio> repositorios) {
        this.presenter = presenter;
        this.repositorios = repositorios;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFoto)
        ImageView ivFoto;

        @BindView(R.id.tvRepoIdNome)
        TextView tvRepoIdNome;

        public ViewHolder(View itemView, RepositoriosContract.Presenter presenter) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION)
                        presenter.abreDetalhesDoRepositorio(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public RepositoriosRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.github_recycler_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, this.presenter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoriosRecyclerAdapter.ViewHolder viewHolder, int position) {

        Repositorio repositorio = repositorios.get(position);

        viewHolder.tvRepoIdNome.setText(String.format("%s - %s", String.valueOf(repositorio.getId()), repositorio.getNome()));

        Picasso.get()
                .load(repositorio.getDono().getAvatarUrl())
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(viewHolder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return repositorios.size();
    }

    public void clear() {
        repositorios.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Repositorio> photos) {
        this.repositorios.addAll(photos);
        notifyDataSetChanged();
    }

    public List<Repositorio> getRepositorios() {
        return repositorios;
    }
}
