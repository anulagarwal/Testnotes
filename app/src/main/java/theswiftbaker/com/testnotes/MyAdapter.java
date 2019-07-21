package theswiftbaker.com.testnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<TodoItem> listforview;
    LayoutInflater inflator=null;
    View v;
    ViewHolder vholder;
    int listPosititon;
    //Constructor
    public MyAdapter(Context con,ArrayList<TodoItem> list)
    {
        super();
        context=con;
        listforview=list;
        inflator=LayoutInflater.from(con);
    }
    // return position here
    @Override
    public long getItemId(int position) {
        return position;
    }
    // return size of list
    @Override
    public int getCount() {
        return listforview.size();
    }
    //get Object from each position
    @Override
    public Object getItem(int position) {
        return listforview.get(position);
    }
    //Viewholder class to contain inflated xml views
    private  class ViewHolder
    {
        private String text;

        private EditText et;
        private ImageButton delete;
        private CheckBox done;
    }
    // Called for each view
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        v=convertView;
        listPosititon = position;

        if(convertView==null)
        {
            //inflate the view for each row of listview
            v=inflator.inflate(R.layout.myadapter,null);

            //ViewHolder object to contain myadapter.xml elements
            vholder=new ViewHolder();
            vholder.et=(EditText)v.findViewById(R.id.ListEdit);
            vholder.et.setText("Enter Task");
            vholder.delete=(ImageButton)v.findViewById(R.id.TodoDelete);
            vholder.done = (CheckBox)v.findViewById(R.id.TodoCheck);


            //set holder to the view
            v.setTag(vholder);

            vholder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listforview.remove(position);
                    MyAdapter.this.notifyDataSetChanged();
                }
            });
        }
        else
            vholder=(ViewHolder)v.getTag();
        //getting MyItem Object for each position
        TodoItem item=(TodoItem)listforview.get(position);
//set the id to editetxt important line here as it will be helpful to set text according to position
        vholder.done.setTag(position);

//setting the values from object to holder views for each row vholder.title.setText(item.getImageheading()); vholder.image.setImageResource(item.getImageid());
       /* vholder.title.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            final int id = v.getId();
                            TodoItem item = listforview.get(id);
                            final EditText field = ((EditText) v);
                            listforview.get(id).setImageheading(field.getText().toString());
                        }
                    }
                }
        );*/
        return v;
    }
}