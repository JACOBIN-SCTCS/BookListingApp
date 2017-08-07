package com.app.jacobinsctcs.my.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>>
{

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=";
    private static final int LOADER_ID=3123;

    private EditText search_edit_text;
    private Button   search_button;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressBar loading_indicator;
    private TextView error_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_edit_text=(EditText)findViewById(R.id.search_box);
        search_button=(Button) findViewById(R.id.search_buttton);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        loading_indicator=(ProgressBar) findViewById(R.id.loading_indicator);
        error_message=(TextView) findViewById(R.id.message_display);

        ArrayList<Book> books=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
         adapter=new BookAdapter(books,this, new BookAdapter.OnItemClick() {
             @Override
             public void itemClick(Intent url_intent)
             {

                     startActivity(url_intent);

             }
         });


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });



     /*   books.add(new Book("http://books.google.com/books/content?id=yl4dILkcqm4C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                "Lord Of The Rings","Jacob james "));
        books.add(new Book("http://books.google.com/books/content?id=TlYWqAAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
                "The Fellowship of the Ring","John Ronald Reuel Tolkien"));
        books.add(new Book("http://books.google.com/books/content?id=TvsF3vxvEswC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                "From Hobbits to Hollywood", "Ernest Mathijs"));*/



    }

    private boolean Read_network_state(Context context)
    {    boolean is_connected;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }

    private void search()
    {
        String search_query=search_edit_text.getText().toString();

        boolean is_connected = Read_network_state(this);
        if(!is_connected)
        {
            error_message.setText(R.string.Failed_to_Load_data);
            recyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }

      //  Log.d("QUERY",search_query);


        if(search_query.equals(""))
        {
            Toast.makeText(this,"Please enter your query",Toast.LENGTH_SHORT).show();
            return;
        }
        String final_query=search_query.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        Uri.Builder buider = uri.buildUpon();


        Bundle args=new Bundle();
        args.putString("SEARCH_URL",buider.toString());
        LoaderManager manager=getSupportLoaderManager();
        Loader loader =manager.getLoader(LOADER_ID);
        if(loader==null)
        {
            getSupportLoaderManager().initLoader(LOADER_ID,args,this);

        }
        else
        {
            getSupportLoaderManager().restartLoader(LOADER_ID,args,this);
        }


    }




    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, final Bundle args)
    {

        return new AsyncTaskLoader<ArrayList<Book>>(this) {


            @Override
            protected void onStartLoading()
            {  if (args==null)
            {
                return;
            }
                error_message.setVisibility(View.INVISIBLE);
                loading_indicator.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                forceLoad();

            }

            @Override
            public ArrayList<Book> loadInBackground()
            {   String url= args.getString("SEARCH_URL");
                URL urll=NetworkUtils.construct_url(url);
                String JSON_RESPONSE=NetworkUtils.GET_JSON_RESPONSE(urll);
                return NetworkUtils.DECODE_JSON(JSON_RESPONSE);
            }
        };


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data)
    {

         loading_indicator.setVisibility(View.INVISIBLE);
         recyclerView.setVisibility(View.VISIBLE);
          adapter.clear();
           adapter.add_new(data);



    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader)
    {
        adapter.clear();

    }



}
