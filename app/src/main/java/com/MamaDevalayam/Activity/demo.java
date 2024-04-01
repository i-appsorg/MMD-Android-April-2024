//package com.MamaDevalayam.Activity;
//
////public class demo {
////}
//import android.app.ProgressDialog;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.graphics.Color;
//        import android.graphics.drawable.ColorDrawable;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.Toast;
//
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.appcompat.widget.SearchView;
//        import androidx.recyclerview.widget.DefaultItemAnimator;
//        import androidx.recyclerview.widget.GridLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.google.gson.Gson;
//        import com.google.gson.reflect.TypeToken;
//
//        import java.lang.reflect.Type;
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MainActivity";
//    private RecyclerView recyclerView;
//    private ProgressDialog progressDialog;
//    private CustomAdapter customAdapter;
//    private ArrayList<ProductModelClass> userList;
//
//    private static final ArrayList<Integer> idList = new ArrayList<>();
//    private static ArrayList<ProductModelClass> publicationPageModel = new ArrayList<>();
//    private static ArrayList<ProductModelClass> publicationPageModel1 = new ArrayList<>();
//    private static final ArrayList<Integer> arrayListCount = new ArrayList<>();
//    private static final ArrayList<Integer> id = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        SharedPreferences preferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = preferences.getString("courses", null);
//        Type type = new TypeToken<ArrayList<ProductModelClass>>() {}.getType();
//
//        if (json != null) {
//            publicationPageModel1 = gson.fromJson(json, type);
//            if (publicationPageModel1 == null) {
//                publicationPageModel1 = new ArrayList<>();
//            }
//        } else {
//            publicationPageModel1 = new ArrayList<>();
//        }
//
//        getProductListFromApi();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//
//        String stringRepresentation2 = sharedPreferences.getString("integerArrayListKey", "");
//
//        ArrayList<Integer> retrievedIntegerArrayList;
//        if (stringRepresentation2 == null || stringRepresentation2.isEmpty()) {
//            retrievedIntegerArrayList = new ArrayList<>();
//        } else {
//            String[] stringArray = stringRepresentation2.split(",");
//            retrievedIntegerArrayList = new ArrayList<>();
//            for (String s : stringArray) {
//                retrievedIntegerArrayList.add(Integer.parseInt(s));
//            }
//        }
//
//        Log.e(TAG, "onCreate:retrievedIntegerArrayList == " + retrievedIntegerArrayList);
//
//        findViewById(R.id.ivAddCart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "onCreate:idList--->>> " + idList);
//
//                Intent intent = new Intent(MainActivity.this, MyCartActivity.class);
//                intent.putExtra("idList", idList);
//                intent.putExtra("tvCountValue", id);
//                intent.putExtra("retrievedIntegerArrayList", retrievedIntegerArrayList);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void getProductListFromApi() {
//        progressDialog = createProgressDialog(MainActivity.this);
//        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
//        Call<List<ProductModelClass>> call = retrofitInterface.getUsers();
//        call.enqueue(new Callback<List<ProductModelClass>>() {
//            @Override
//            public void onResponse(Call<List<ProductModelClass>> call, Response<List<ProductModelClass>> response) {
//                progressDialog.dismiss();
//                userList = new ArrayList<>(response.body());
//                for (int i = 0; i < userList.size(); i++) {
//                    for (int j = 0; j < publicationPageModel1.size(); j++) {
//                        if (userList.get(i).getTitle().equals(publicationPageModel1.get(j).getTitle())) {
//                            ProductModelClass model = userList.get(i);
//                            model.setTvCountValue(publicationPageModel1.get(j).getTvCountValue());
//                            userList.set(i, model);
//                        }
//                    }
//                }
//                customAdapter = new CustomAdapter(MainActivity.this, userList, new CustomItemClickListener() {
//                    @Override
//                    public void onItemClick(ProductModelClass user, int position) {
//                    }
//                });
//                recyclerView.setAdapter(customAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<ProductModelClass>> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(MainActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private ProgressDialog createProgressDialog(Context mContext) {
//        ProgressDialog dialog = new ProgressDialog(mContext);
//        try {
//            dialog.show();
//        } catch (BadTokenException e) {
//            // Handle the exception
//        }
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_layout);
//        return dialog;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finishAffinity();
//    }
//}