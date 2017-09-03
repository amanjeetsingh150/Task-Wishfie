package com.developers.taskwishfie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.developers.coolprogressviews.DoubleArcProgress;
import com.developers.taskwishfie.adapters.NewsAdapter;
import com.developers.taskwishfie.model.Post;
import com.developers.taskwishfie.model.Result;
import com.developers.taskwishfie.rest.ApiInterface;
import com.developers.taskwishfie.util.Constants;
import com.developers.taskwishfie.util.EndlessScrollListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.news_list)
    RecyclerView newsRecyclerView;
    @BindView(R.id.double_progress_bar)
    DoubleArcProgress doubleProgress;
    private Retrofit apiClient, retrofit;
    private List<Post> postList;
    private NewsAdapter newsAdapter;
    private int count = 0;
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiClient = setupRetrofit();
        setupFeed(apiClient);
    }

    private void setupFeed(Retrofit apiClient) {
        ApiInterface apiInterface = apiClient.create(ApiInterface.class);
        apiInterface.getPage1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Result value) {
                        postList = value.getPosts();
                        newsAdapter = new NewsAdapter(MainActivity.this, postList);
                        layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        newsRecyclerView.setLayoutManager(layoutManager);
                        hideLoading();
                        newsRecyclerView.setAdapter(newsAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                int visibleItemCount = layoutManager.getChildCount();
                                int totalItemCount = layoutManager.getItemCount();
                                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                                if (!isLoading && count <= 3) {
                                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                            && firstVisibleItemPosition >= 0
                                            && totalItemCount >= 3) {
                                        Log.d(TAG, "OYE");
                                        fetchPaginatedData();
                                        count++;
                                    }
                                }
                            }
                        });
                    }
                });
    }

    private void hideLoading() {
        doubleProgress.setVisibility(View.GONE);
    }

    private void fetchPaginatedData() {
        isLoading = true;
        ApiInterface apiInterface = apiClient.create(ApiInterface.class);
        apiInterface.getPage2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Result value) {
                        List<Post> postList1 = value.getPosts();
                        newsAdapter.swap(postList1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
        apiInterface.getPage3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Result value) {
                        List<Post> postList2 = value.getPosts();
                        newsAdapter.swap(postList2);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    private Retrofit setupRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        RxJava2CallAdapterFactory factory = RxJava2CallAdapterFactory.create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(factory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    private void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

}
