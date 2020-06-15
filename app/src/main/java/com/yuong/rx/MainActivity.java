package com.yuong.rx;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String APP_KEY = "aa47561558f285fee99f1943c7b844fb";
    private final static String URL = "http://v.juhe.cn/toutiao/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        test();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
        test8();
    }


    public void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.e("MainActivity", "当前的线程1" + Thread.currentThread().toString());
                Log.e("MainActivity", "subscribe=" + 1);
                e.onNext("1");
                Log.e("MainActivity", "subscribe=" + 2);
                e.onNext("2");
                Log.e("MainActivity", "subscribe=" + 3);
                e.onNext("3");
                Log.e("MainActivity", "subscribe=" + 4);
                e.onNext("4");
                e.onComplete();
            }
        })
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("MainActivity", "当前的线程2" + Thread.currentThread().toString());
                        Log.e("MainActivity", "onNext=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    public void test2() {
        CompositeDisposable mRxEvent = new CompositeDisposable();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.e("MainActivity", "subscribe=" + 1);
                e.onNext("1");
                Log.e("MainActivity", "subscribe=" + 2);
                e.onNext("2");
                Log.e("MainActivity", "subscribe=" + 3);
                e.onNext("3");
                Log.e("MainActivity", "subscribe=" + 4);
                e.onNext("4");
                e.onComplete();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                Log.e("MainActivity", "onNext=" + s);
            }
        });
        mRxEvent.add(subscribe);
        mRxEvent.clear();
    }


    public void test3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.e("MainActivity", "subscribe=" + 1);
                e.onNext("1");
                Log.e("MainActivity", "subscribe=" + 2);
                e.onNext("2");
                Log.e("MainActivity", "subscribe=" + 3);
                e.onNext("3");
                Log.e("MainActivity", "subscribe=" + 4);
                e.onNext("4");
                e.onComplete();
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                return "xxxx" + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void test4() {
        Flowable.create(new FlowableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Throwable {
                Log.e("MainActivity", "当前的线程1" + Thread.currentThread().toString());
                Log.e("MainActivity", "subscribe=" + 1);
                e.onNext("1");
                Log.e("MainActivity", "subscribe=" + 2);
                e.onNext("2");
                Log.e("MainActivity", "subscribe=" + 3);
                e.onNext("3");
                Log.e("MainActivity", "subscribe=" + 4);
                e.onNext("4");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("MainActivity", "当前的线程2" + Thread.currentThread().toString());
                        Log.e("MainActivity", "onNext=" + s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("MainActivity", "onComplete");
                    }
                });
    }

    public void test5() {
        //   创建第1个被观察者
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

        //创建 第2个被观察者
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(10000);
                Log.e(TAG, "被观察者2发送了事件A");
                emitter.onNext("A");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

//使用zip变换操作符进行事件合并
// 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                return integer + string;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.e(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }


    public void test6() {
        //   创建第1个被观察者
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(3000);
                Log.e(TAG, "被观察者1发送了事件1");
                emitter.onNext("1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

        //创建 第2个被观察者
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(10000);
                Log.e(TAG, "被观察者2发送了事件A");
                emitter.onNext("A");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());


        Observable.mergeArray(observable1, observable2)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "最终接收到的事件 =  " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    public void test7() {
        Log.e(TAG, "test7...........");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) {
                Log.e(TAG, "当前的线程1" + Thread.currentThread().toString());
//                String result = getData();
                String result = getData2();
                if (!TextUtils.isEmpty(result)) {
                    e.onNext(result);
                }
                e.onComplete();
            }
        })

                .subscribeOn(Schedulers.io())
//                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
//                    @Override
//                    public ObservableSource<? extends String> apply(Throwable throwable) throws Throwable {
//                        Log.e(TAG, "onErrorResumeNext : " + throwable.toString());
//                        return Observable.just("出现了异常");
//                    }
//                })
                .onErrorResumeNext(new HttpResponseFunc<String>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("MainActivity", "当前的线程2" + Thread.currentThread().toString());
                        Log.e("MainActivity", "onNext=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError : " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    public void test8() {
        Log.e(TAG, "test8...........");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String, String> map = new HashMap<>();
        map.put("key", APP_KEY);
        map.put("type", "top");

        ApiService service = retrofit.create(ApiService.class);
        service.getData2(map)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new HttpResponseFunc<DataBean>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull DataBean responseBody) {
                        Log.e(TAG, "retrofit response=" + responseBody.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError ： " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getData() {
        Log.e(TAG, "请求数据.....");
        String result = null;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("key", APP_KEY)
                .add("type", "top")
                .build();
        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //处理错误的变换
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return upstream.onErrorResumeNext(new HttpResponseFunc<T>());
        }
    }

    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            Log.e(TAG, "转换异常 ： " + throwable.toString());
            return Observable.error(new Exception("网络异常"));
        }
    }


    public interface ApiService {

        @POST("index")
        Call<ResponseBody> getData(@QueryMap Map<String, String> map);

        @POST("index")
        Observable<DataBean> getData2(@QueryMap Map<String, String> map);
    }

    private String getData2() {
        String result = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Map<String, String> map = new HashMap<>();
        map.put("key", APP_KEY);
        map.put("type", "top");
        Call<ResponseBody> call = service.getData(map);
        try {
            retrofit2.Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
