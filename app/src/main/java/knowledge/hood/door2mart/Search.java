package knowledge.hood.door2mart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import knowledge.hood.door2mart.Adapters.CategoryAdapter;
import knowledge.hood.door2mart.Adapters.ProductAdapter;
import knowledge.hood.door2mart.Adapters.SearchAdapter;
import knowledge.hood.door2mart.Models.CategoryResponseModel;
import knowledge.hood.door2mart.Models.ProductResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {

    SearchView searchview;
    RecyclerView srch_recview;

    ImageButton voice_srch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchview = findViewById(R.id.searchview);
        srch_recview = findViewById(R.id.srch_recview);

        voice_srch= findViewById(R.id.voice_srch);

        srch_recview.setLayoutManager(new GridLayoutManager(this, 3));

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Call<List<ProductResponseModel>> call = apicontroler.
                        getInstance().getapi().getallProduct(String.valueOf(newText));

                    call.enqueue(new Callback<List<ProductResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<ProductResponseModel>> call, Response<List<ProductResponseModel>> response) {
                            findViewById(R.id.np).setVisibility(View.GONE);
                            srch_recview.setVisibility(View.VISIBLE);
                            List<ProductResponseModel> data = response.body();
//                        ArrayList<ProductResponseModel> data= new ArrayList<ProductResponseModel>();
//                        data.addAll(listdata)
                            if(data!=null)
                            {
                                SearchAdapter searchAdapter = new SearchAdapter(data);
                                //             searchAdapter.getFilter().filter(newText);
                                srch_recview.setAdapter(searchAdapter);
                            }
                            else {
                                srch_recview.setVisibility(View.GONE);
                                findViewById(R.id.np).setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onFailure(Call<List<ProductResponseModel>> call, Throwable t) {
                            Toast.makeText(Search.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });



                return false;
            }
        });

        voice_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice_recognize();
            }
        });

    }

    private void  voice_recognize(){
        Intent intent =  new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = arrayList.get(0);
            searchview.setQuery(voice.trim(),false);
        }else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }
}