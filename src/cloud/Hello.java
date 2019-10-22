package cloud;


/**
 * Created by W2G on 2019/10/21.
 */
// AbstractCommand.java
/*abstract class AbstractCommand<R> implements HystrixInvokableInfo<R>, HystrixObservable<R> {

    // ... 省略无关属性与方法

    public Observable<R> toObservable() {

        return Observable.defer(new Func0<Observable<R>>() {
            @Override
            public Observable<R> call() {
                // ....
            }
        }

    }

    public Observable<R> observe() {
        // us a ReplaySubject to buffer the eagerly subscribed-to Observable
        ReplaySubject<R> subject = ReplaySubject.create();
        // eagerly kick off subscription
        final Subscription sourceSubscription = toObservable().subscribe(subject);
        // return the subject that can be subscribed to later while the execution has already started
        return subject.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                sourceSubscription.unsubscribe();
            }
        });
    }
}
// HystrixCommand.java
public abstract class HystrixCommand<R> extends AbstractCommand<R> implements HystrixExecutable<R>, HystrixInvokableInfo<R>, HystrixObservable<R> {
    // ... 省略无关属性与方法

    public Future<R> queue() {
        final Future<R> delegate = toObservable().toBlocking().toFuture();
        final Future<R> f = new Future<R>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public R get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
            // ... 包装 delegate
        }
        // ...
        return f;
    }
    public R execute() {
        try {
            return queue().get();
        } catch (Exception e) {
        }
    }

    protected abstract R run() throws Exception;
}*/
