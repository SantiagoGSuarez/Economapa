package com.example.economapa;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdutoAdapter extends ArrayAdapter<Produto> {
    private Context context;
    private List<Produto> produtoList;

    public ProdutoAdapter(Context context, List<Produto> productList) {
        super(context, R.layout.fragment_lista, productList);
        this.context = context;
        this.produtoList = productList;
    }
    public void updateProductList(List<Produto> newProductList) {
        produtoList.clear(); // Limpia la lista actual
        produtoList.addAll(newProductList); // Agrega los nuevos productos
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout, parent, false);
        }

        if (produtoList == null || position >= produtoList.size()) {
            return listItem; // Retornar el elemento de la lista sin modificar si la lista es nula o la posición está fuera de los límites
        }

        Produto currentProduct = produtoList.get(position);
        System.out.println(currentProduct.getImagem());

        ImageView imageView = listItem.findViewById(R.id.imageview1);
        // Aquí cargamos la imagen utilizando Picasso u otra biblioteca similar
        Picasso.get().load(currentProduct.getImagem()).into(imageView);

        TextView productName = listItem.findViewById(R.id.textview1);
        productName.setText(currentProduct.getNomeProduto());



        TextView productPrice = listItem.findViewById(R.id.textview3);

        productPrice.setText("Precio $"+currentProduct.getPreco()+"\nPrecio Promoción $"+currentProduct.getPrecoPromocao());




        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int produtoId = currentProduct.getIdProduto();


                Intent i = new Intent(context, InfoProduto.class);

                i.putExtra("PRODUTO_ID",produtoId);
                context.startActivity(i);
            }
        });


        return listItem;
    }


}
