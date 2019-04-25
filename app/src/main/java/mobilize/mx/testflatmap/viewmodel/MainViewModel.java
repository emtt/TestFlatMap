package mobilize.mx.testflatmap.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import mobilize.mx.restcore.model.Posts;
import mobilize.mx.restcore.model.User;
import mobilize.mx.restcore.rest.ProjectRepository;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public class MainViewModel extends ViewModel {
    String TAG = this.getClass().getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private ProjectRepository projectRepository;

    public MainViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public MutableLiveData<List<User>> getUsers() {
        final List<User> userList = new ArrayList<>();
        final MutableLiveData<List<User>> response = new MutableLiveData<>();

        disposables.add(
                projectRepository.getUsers()
                        /**
                         * Converting List<User> emission to single User emissions
                         * */
                        .flatMap(new Function<List<User>, ObservableSource<User>>() {
                            @Override
                            public ObservableSource<User> apply(List<User> users) throws Exception {
                                Log.d(TAG, "User List Size: " + users.size());
                                return Observable.fromIterable(users);
                            }
                        })
                        /**
                         * Fetching Post List on each User emission
                         * */
                        .flatMap(new Function<User, ObservableSource<List<Posts>>>() {
                            @Override
                            public ObservableSource<List<Posts>> apply(User user) throws Exception {
                                Log.d(TAG, "User: " + user.getName());
                                userList.add(user);
                                return projectRepository.getPosts(user.getId()).toObservable();
                            }
                        })
                        .subscribeWith(new DisposableObserver<List<Posts>>() {
                            @Override
                            public void onNext(List<Posts> posts) {

                                Log.d(TAG, "User post count:" + posts.size());
                                Log.d(TAG, "userList size:" + userList.size());

                                for (int i=0; i<userList.size(); i++) {
                                    User u =  userList.get(i);
                                    if (posts.get(i).userId == u.getId()) {
                                        u.setCountPost(posts.size());
                                    }
                                }
                                /**
                                 * Set the final value to response
                                 */
                                response.postValue(userList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "Throwable:" + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Complete");
                            }
                        })
        );

        return response;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
