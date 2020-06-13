package com.yuong.rx;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        test();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
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
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Throwable {
                        return null;
                    }
                })
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
}
