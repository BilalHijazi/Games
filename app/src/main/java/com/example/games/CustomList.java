package com.example.games;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
public class CustomList extends BaseAdapter implements Filterable {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference("VideoGames");
    StorageReference storageRef=FirebaseStorage.getInstance().getReference("GamesPics") ;
    private final Context context;
    private List<Game>Games;
    private List<Game>filteredData ;
    private   LayoutInflater Inflater;
    private ItemFilter mFilter=new ItemFilter();
    public CustomList(Context context,  List<Game>games) {
        this.context = context;
        this.Games=games;
        this.filteredData=games;
        Inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return filteredData.size();
    }
    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Filter getFilter(){
        return mFilter;
    }
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View row=convertView;
        if(convertView==null)
             row = Inflater.inflate(R.layout.list_single, null, true);
               TextView Title = (TextView) row.findViewById(R.id.listview_item_title);
             TextView Description = (TextView) row.findViewById(R.id.listview_item_description);
            final ImageView image = (ImageView) row.findViewById(R.id.listview_image);
         Title.setText(filteredData.get(position).getName());
         Description.setText(filteredData.get(position).getGenres());
         storageRef.child(filteredData.get(position).getDbID()).list(1)
                 .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                     @Override
                     public void onSuccess(ListResult listResult) {
                         if(listResult.getItems().size()!=0)
                         listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 Glide.with(context).load(uri).centerCrop().placeholder(R.drawable.ic_launcher_foreground).
                                         into(image);
                             }
                         });
                     }
                 });
        return  row;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Game> list = Games;
            int count = list.size();
            final ArrayList<Game> nlist = new ArrayList<Game>(count);
            String filterableString ;
                 for (int i=0;i<count;i++){
                     filterableString=list.get(i).getName();
                     if(filterableString.toLowerCase().contains(filterString)){
                         nlist.add(list.get(i));
                     }
                 }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Game>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}


