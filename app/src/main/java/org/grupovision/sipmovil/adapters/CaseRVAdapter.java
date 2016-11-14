package org.grupovision.sipmovil.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.grupovision.sipmovil.R;
import org.grupovision.sipmovil.models.Case;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ivalle on 07/11/2016.
 */

public class CaseRVAdapter extends RecyclerView.Adapter<CaseRVAdapter.ViewHolder> {


    ArrayList<Case> cases;
    private OnItemClickListener mListener;

    public CaseRVAdapter(ArrayList<Case> cases,OnItemClickListener mListener) {
        this.cases = cases;
        this.mListener = mListener;
    }

    @Override
    public CaseRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_case,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CaseRVAdapter.ViewHolder holder, int position) {

        final Case _case = cases.get(position);
        if (_case.getLastName()!=null)
            holder.txtFullName.setText(_case.getFirstName() + " " + _case.getLastName());
        else
            holder.txtFullName.setText(_case.getFirstName());
        if (_case.getIdentificationNumber()!=null)
            holder.txtIdentificationNumber.setText(_case.getIdentificationNumber());

        if (_case.getPathPhoto()!=null){
            File imgFile = new File(_case.getPathPhoto());
            if (imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgProfileThumb.setImageBitmap(bitmap);
            }
        }

        holder.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mListener.OnItemClick(_case);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView imgProfileThumb;
        TextView txtIdentificationNumber, txtFullName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView  = itemView;
            imgProfileThumb = (ImageView) itemView.findViewById(R.id.imgProfileThumb);
            txtIdentificationNumber = (TextView) itemView.findViewById(R.id.txtIdentificationNumber);
            txtFullName = (TextView) itemView.findViewById(R.id.txtFullName);
        }

        public  void  setOnClickListener(View.OnClickListener listener){
            itemView.setOnClickListener(listener);
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(Case _case);
    }

}
