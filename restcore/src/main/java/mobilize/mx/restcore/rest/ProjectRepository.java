package mobilize.mx.restcore.rest;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mobilize.mx.restcore.Constants;
import mobilize.mx.restcore.model.Posts;
import mobilize.mx.restcore.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public class ProjectRepository {
    private static String TAG = ProjectRepository.class.getSimpleName();
    private static ProjectRepository projectRepository;
    private ApiInterface apiInterface;
    private Context context;

    public synchronized static ProjectRepository getInstance(Context mContext) {

        if (projectRepository == null) {
            if (projectRepository == null) {
                projectRepository = new ProjectRepository(mContext);
            }
        }
        return projectRepository;
    }

    @Inject
    public ProjectRepository(Context mContext) {
        this.context = mContext;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new ChuckInterceptor(context));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public Observable<List<User>> getUsers() {
        return apiInterface.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

    }

    public Single<List<Posts>> getPosts(int userId) {
        return apiInterface.getPosts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);

    }
}
